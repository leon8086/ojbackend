package xmut.cs.ojbackend.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileReader;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONWriter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.update.UpdateChain;
import com.mybatisflex.core.util.UpdateEntity;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.io.outputstream.ZipOutputStream;
import net.lingala.zip4j.model.FileHeader;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.model.enums.CompressionLevel;
import net.lingala.zip4j.model.enums.CompressionMethod;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import xmut.cs.ojbackend.entity.DTO.DTOProblemExport;
import xmut.cs.ojbackend.entity.LoginUser;
import xmut.cs.ojbackend.entity.Problem;
import xmut.cs.ojbackend.entity.ProblemTag;
import xmut.cs.ojbackend.entity.User;
import xmut.cs.ojbackend.entity.VO.*;
import xmut.cs.ojbackend.entity.entitymapper.VoProblemTitleWrapper;
import xmut.cs.ojbackend.mapper.ProblemMapper;
import xmut.cs.ojbackend.mapper.SubmissionMapper;
import xmut.cs.ojbackend.service.ProblemService;
import xmut.cs.ojbackend.service.ProblemTagService;
import xmut.cs.ojbackend.utils.CommonUtil;
import xmut.cs.ojbackend.utils.LocalFileUtil;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

import static xmut.cs.ojbackend.entity.table.ProblemTableDef.PROBLEM;
import static xmut.cs.ojbackend.entity.table.SubmissionTableDef.SUBMISSION;

/**
 *  服务层实现。
 *
 * @author leon
 * @since 2024-06-03
 */
@Service
public class ProblemServiceImpl extends ServiceImpl<ProblemMapper, Problem> implements ProblemService {
    @Autowired
    ProblemMapper problemMapper;

    @Autowired
    SubmissionMapper submissionMapper;

    @Autowired
    ProblemTagService problemTagService;

    @Autowired
    VoProblemTitleWrapper voProblemTitleWrapper;

    @Autowired
    CommonUtil commonUtil;

    @Autowired
    LocalFileUtil localFileUtil;

    @Autowired
    RedisTemplate<Object, Object> redisTemplate;

    @Value("${files.upload.tmp.path}")
    private String UPLOAD_TMP_DIRECTORY;

    @Value("${files.testCase.path}")
    private String TESTCASE_DIRECTORY;

    public QueryWrapper applyKeyword(QueryWrapper wrapper, String keyword){
        if (keyword != null && !keyword.isEmpty()) {
            wrapper.where(PROBLEM.TITLE.like(keyword)).or(PROBLEM.DISPLAY_ID.like(keyword));
        }
        return wrapper;
    }

    public QueryWrapper applyDifficulty(QueryWrapper wrapper, String difficulty){
        if (difficulty != null && !difficulty.isEmpty()) {
            wrapper.where(PROBLEM.DIFFICULTY.eq(difficulty));
        }
        return wrapper;
    }

    @Override
    public Object listPage( Integer page, Integer limit, String keyword, String difficulty, Integer[] tags ){
        if (page == null) {
            page = 1;
        }
        if (limit == null) {
            limit = 1000;
        }
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.where(PROBLEM.VISIBLE.eq(true));
        applyKeyword( wrapper, keyword );
        applyDifficulty( wrapper, difficulty );
        if( tags != null && tags.length != 0 ){
            wrapper.and( q ->{
                for( Integer tag : tags ) {
                    q.or( PROBLEM.SUB_TAG_ID.eq(tag));
                }
            });
        }
        wrapper.orderBy(PROBLEM.DISPLAY_ID.asc());
        Page<VOProblemTitle> ret = problemMapper.paginateWithRelationsAs(page, limit, wrapper, VOProblemTitle.class);
        User user = commonUtil.getCurrentUser();
        JSONObject status = user.getProblemsStatus();
        for( VOProblemTitle p: ret.getRecords() ){
            if( !status.containsKey( p.getId().toString() )){
                p.setStatus(null);
            }
            else{
                JSONObject s = status.getJSONObject(p.getId().toString());
                Integer ps = s.getInteger("status");
                p.setStatus(ps);
            }
        }
        return ret;
    }

    public Object info( Integer id ){
        User user = commonUtil.getCurrentUser();
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.select().where(PROBLEM.ID.eq(id));
        VOProblemDetail problem = problemMapper.selectOneWithRelationsByQueryAs(wrapper, VOProblemDetail.class);
        wrapper.clear();
        wrapper.where( SUBMISSION.USER_ID.eq(user.getId()));
        wrapper.and( SUBMISSION.PROBLEM_ID.eq(problem.getId()));
        wrapper.orderBy(SUBMISSION.CREATE_TIME.desc());
        List<VOSubmissionDetail> submissions = submissionMapper.selectListByQueryAs(wrapper, VOSubmissionDetail.class);
        if(commonUtil.isUserInExam()) {
            for (VOSubmissionDetail vs : submissions) {
                vs.setCode("处于考试状态，请结束考试后查看");
            }
        }
        problem.setSubmissionList(submissions);

        JSONObject status = user.getProblemsStatus();
        JSONObject s = status.getJSONObject(problem.getId().toString());
        Integer ps = null;
        if( s != null ) {
            ps = s.getInteger("status");
        }
        problem.setStatus(ps);
        commonUtil.toAnswerTemplate(problem);
        return problem;
    }

    @Override
    public Object adminListPage(Integer page, Integer limit, String keyword) {
        if (page == null) {
            page = 1;
        }
        if (limit == null) {
            limit = 1000;
        }
        QueryWrapper wrapper = new QueryWrapper();
        applyKeyword( wrapper, keyword );
        wrapper.orderBy(PROBLEM.DISPLAY_ID.asc());
        return problemMapper.paginateWithRelationsAs(page,limit, wrapper, VOProblemAdminList.class);
    }

    @Override
    public Object adminListBriefPage(Integer page, Integer limit, String keyword, String difficulty) {
        if (page == null) {
            page = 1;
        }
        if (limit == null) {
            limit = 1000;
        }
        QueryWrapper wrapper = new QueryWrapper();
        applyKeyword( wrapper, keyword );
        applyDifficulty( wrapper, difficulty );
        wrapper.orderBy(PROBLEM.DISPLAY_ID.asc());
        return problemMapper.paginateAs(page, limit, wrapper, VOProblemBrief.class);
    }

    @Override
    // do update
    public Object adminSetVisibility(List<Integer> ids, Boolean visible) {
        List<Problem> listRet = new ArrayList<Problem>();
        for( Integer id : ids ){
            Problem problem = UpdateEntity.of(Problem.class, id);
            problem.setVisible(visible);
            problemMapper.update(problem);
            listRet.add( problem );
        }
        redisTemplate.delete("problems-title");
        return listRet;
    }

    public List<String> getImportFileDesc( ZipFile zipFile ) throws IOException{
        List<FileHeader> fileHeaders = zipFile.getFileHeaders();
        List<String> ret = new ArrayList<>();
        for( FileHeader header : fileHeaders){
            if(header.getFileName().contains("problem.json")){
                String s = header.getFileName().replace("problem.json", "");
                s = s.replace("/","");
                ret.add(s);
            }
        }
        ret.sort(new Comparator<String>() {
            @Override
            public int compare(String t1, String t2) {
                Integer d1 = Integer.parseInt(t1);
                Integer d2 = Integer.parseInt(t2);
                return d1.compareTo(d2);
            }
        });
        return ret;
    }

    public String getContent( String filename ){
        String s = new FileReader(filename).readString();
        s = s.replace("\r\n", "\n");
        return s;
    }

    public void setProblemTag( Problem problem, String major, String sub){
        Map<String, ProblemTag> tags = problemTagService.getTagsCreateWhenNotExist(major, sub);
        problem.setMajorTagId(tags.get("major").getId());
        problem.setSubTagId(tags.get("sub").getId());
    }

    @Override
    // do update
    public Object importProblem(MultipartFile file) throws IOException, NoSuchAlgorithmException {
        redisTemplate.delete("problems-title");
        MessageDigest md = MessageDigest.getInstance("MD5");
        String path = Objects.requireNonNullElse(UPLOAD_TMP_DIRECTORY,"");
        String fid = UUID.randomUUID().toString();
        String filename = path + fid + ".zip";
        File zfile = new File(filename);
        file.transferTo(zfile);
        String workDir = path+fid;
        List<String> desc;
        User user = commonUtil.getCurrentUser();
        List<VOProblemBrief> failed = new ArrayList<>();
        try( ZipFile zipFile = new ZipFile(filename) ){
            desc = getImportFileDesc(zipFile);
            zipFile.extractAll(workDir);
        }
        FileUtil.del(zfile);
        Integer count = 0;
        for( String f : desc ){
            Problem problem;
            FileReader reader = new FileReader(workDir+"/"+f + "/problem.json");
            String json = reader.readString();
            JSONObject jsonObject = JSONObject.parseObject(json);
            String majorTagName = jsonObject.getString("majorTag");
            String subTagName = jsonObject.getString("subTag");
            problem = jsonObject.to(Problem.class);
            String caseId = UUID.randomUUID().toString().replace("-", "");
            problem.setCreatedById(user.getId());
            problem.setCreateTime(Calendar.getInstance().getTime());
            problem.setLastUpdateTime(Calendar.getInstance().getTime());
            problem.setTestCaseId(caseId);
            problem.setVisible(false);
            setProblemTag( problem, majorTagName, subTagName);
            FileUtil.move(new File(workDir+"/"+f+"/testcase"), new File(TESTCASE_DIRECTORY+caseId), true);
            JSONArray cases = problem.getTestCaseScore();
            String targetDir = TESTCASE_DIRECTORY+caseId+"/";
            Map<String, Object> info = new HashMap<>();
            Integer totalScore = 0;
            for( int i=0; i<cases.size(); ++i){
                JSONObject tmp = cases.getJSONObject(i);
                String input_name = tmp.getString("input_name");
                Integer input_size = getContent(targetDir+input_name).getBytes().length;
                String output_name = tmp.getString("output_name");
                Integer score = tmp.getInteger("score");
                totalScore += score;
                byte[] content = getContent(targetDir+output_name).strip().getBytes();
                Integer output_size = content.length;
                String md5 = localFileUtil.toHex(md.digest(content));
                String prefix = input_name.replace(".in","");
                Map<String, Object> data = new HashMap<>();
                data.put("stripped_output_md5", md5);
                data.put("input_name", input_name);
                data.put("input_size", input_size);
                data.put("output_name", output_name);
                data.put("output_size", output_size);
                info.put( prefix, data );
            }
            problem.setTotalScore(totalScore);
            Map<String, Object> root = new HashMap<>();
            root.put("spj", false);
            root.put("test_cases", info);
            ObjectMapper objectMapper = new ObjectMapper();
            try(Writer writer = new FileWriter(new File(targetDir, "info"))){
                objectMapper.writerWithDefaultPrettyPrinter().writeValue(writer, root);
            }
            try {
                mapper.insert(problem);
                count ++;
            }
            catch (Exception e){
                if( failed.size() > 10 ){
                    continue;
                }
                VOProblemBrief pb = new VOProblemBrief();
                pb.setDisplayId(problem.getDisplayId());
                pb.setTitle(problem.getTitle());
                pb.setDifficulty(e.toString());
                failed.add(pb);
            }
        }
        FileUtil.del(workDir);
        Map<String, Object> ret = new HashMap<>();
        ret.put("insert", count);
        ret.put("failed", failed);
        return ret;
    }
    @Override
    public void exportProblem(List<Integer> idList, OutputStream httpout) throws IOException {
        ZipOutputStream zo = new ZipOutputStream(httpout);
        int count = 0;
        for( Integer id : idList ){
            count ++;
            QueryWrapper wrapper = new QueryWrapper();
            wrapper.where(PROBLEM.ID.eq(id));
            DTOProblemExport dtoProblemExport = mapper.selectOneWithRelationsByQueryAs(wrapper, DTOProblemExport.class);
            addProblemToZipStream(zo, count, dtoProblemExport);
        }
        zo.close();
        httpout.close();
    }

    @Override
    public void exportAllProblem(OutputStream outputStream) throws IOException{
        ZipOutputStream zo = new ZipOutputStream(outputStream);
        int count = 0;
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.orderBy(PROBLEM.DISPLAY_ID.asc());
        List<DTOProblemExport> problemLists = mapper.selectListWithRelationsByQueryAs(wrapper, DTOProblemExport.class);
        for( DTOProblemExport dtoProblemExport: problemLists){
            count ++;
            addProblemToZipStream(zo, count, dtoProblemExport);
        }
        zo.close();
        outputStream.close();
    }

    private void addProblemToZipStream(ZipOutputStream zo, int count, DTOProblemExport dtoProblemExport) throws IOException {
        JSONObject jo = JSONObject.from(dtoProblemExport);
        String caseSrcPath = TESTCASE_DIRECTORY+dtoProblemExport.getTestCaseId()+"/";
        String path = Integer.toString(count) +"/";
        String case_path = path+"testcase/";
        jo.remove("testCaseId");
        jo.remove("majorTagId");
        jo.remove("subTagId");
        jo.remove("id");
        ZipParameters pd = new ZipParameters();
        pd.setCompressionMethod(CompressionMethod.DEFLATE);
        pd.setCompressionLevel(CompressionLevel.NORMAL);
        pd.setFileNameInZip(path+"problem.json");
        zo.putNextEntry(pd);
        IOUtils.copy( new ByteArrayInputStream(JSON.toJSONString(jo, JSONWriter.Feature.PrettyFormat).getBytes()), zo );
        zo.closeEntry();
        File file = new File(caseSrcPath);
        for( File f : Objects.requireNonNull(file.listFiles())){
            if(f.isDirectory()){
                continue;
            }
            ZipParameters pc = new ZipParameters();
            pc.setFileNameInZip(case_path+f.getName());
            pc.setCompressionMethod(CompressionMethod.DEFLATE);
            pc.setCompressionLevel(CompressionLevel.NORMAL);
            zo.putNextEntry(pc);
            IOUtils.copy( new FileInputStream(f), zo);
            zo.closeEntry();
        }
    }

    @Override
    public Object getAdminDetail(Integer id) {
        VOProblemAdminDetail voProblemAdminDetail =  mapper.selectOneWithRelationsByIdAs(id, VOProblemAdminDetail.class);
        commonUtil.toEditorTemplate(voProblemAdminDetail);
        return voProblemAdminDetail;
    }

    @Override
    // do update
    public Object adminNewProblem(Problem problem, MultipartFile multipartFile) throws IOException, NoSuchAlgorithmException  {
        redisTemplate.delete("problems-title");
        User user = ((LoginUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        problem.setCreatedById(user.getId());
        problem.setCreateTime(Calendar.getInstance().getTime());
        problem.setLastUpdateTime(Calendar.getInstance().getTime());
        commonUtil.fromEditorTemplate(problem);
        String caseDir = TESTCASE_DIRECTORY;
        JSONObject ret = localFileUtil.processZip(multipartFile, caseDir);
        String caseId = (String)ret.get("id");
        problem.setTestCaseId(caseId);
        mapper.insert(problem);
        return problem;
    }

    @Override
    // do update
    public Object adminUpdateProblem(Problem problem) throws IOException{
        redisTemplate.delete("problems-title");
        Problem np = UpdateEntity.of( Problem.class, problem.getId());
        commonUtil.fromEditorTemplate(problem);
        CopyOptions copyOptions = CopyOptions.create(null, true);
        BeanUtil.copyProperties(problem, np, copyOptions);
        np.setLastUpdateTime(Calendar.getInstance().getTime());
        mapper.update(np);
        return np;
    }

    @Override
    public Object adminUpdateProblemWithCases(Problem problem, MultipartFile multipartFile) throws IOException, NoSuchAlgorithmException{
        String oldId = problem.getTestCaseId();
        String caseDir = TESTCASE_DIRECTORY;
        FileUtil.del(TESTCASE_DIRECTORY+oldId); // 删掉旧的
        JSONObject ret = localFileUtil.processZip(multipartFile, caseDir);
        String caseId = (String)ret.get("id");
        problem.setTestCaseId(caseId); // 更新test case id
        return adminUpdateProblem(problem);
    }

    @Override
    // do update
    public Object adminSetAllVisibility(Boolean visible) {
        redisTemplate.delete("problems-title");
        UpdateChain.of(Problem.class).set(Problem::getVisible, visible).where(PROBLEM.VISIBLE.ne(visible)).update();
        return "设置成功";
    }

    @Override
    public Object getAllBrief() {
        JSONArray data;
        List<VOProblemBrief> ret;
        if(Boolean.TRUE.equals(redisTemplate.hasKey("problems-title"))){
            data = (JSONArray) redisTemplate.opsForValue().get("problems-title");
            if (data != null) {
                ret = JSONArray.parseArray(data.toJSONString(), VOProblemBrief.class);
            }
            else{
                ret = new ArrayList<>();
            }
        }
        else {
            QueryWrapper wrapper = new QueryWrapper();
            wrapper.where( PROBLEM.VISIBLE.eq(true));
            wrapper.orderBy(PROBLEM.DISPLAY_ID.asc());
            ret = mapper.selectListByQueryAs( wrapper, VOProblemBrief.class);
            data = JSONArray.from(ret);
            redisTemplate.opsForValue().set("problems-title", data );
        }
        return ret;
    }
}