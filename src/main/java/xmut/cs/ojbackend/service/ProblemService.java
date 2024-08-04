package xmut.cs.ojbackend.service;

import com.mybatisflex.core.service.IService;
import org.springframework.web.multipart.MultipartFile;
import xmut.cs.ojbackend.entity.Problem;

import java.io.IOException;
import java.io.OutputStream;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 *  服务层。
 *
 * @author leon
 * @since 2024-06-03
 */
public interface ProblemService extends IService<Problem> {
    public Object listPage(Integer page, Integer limit, String keyword, String difficulty, String tag);

    public Object info( Integer id );

    //Object listBriefPage(Integer page, Integer limit, String keyword, String difficulty);

    Object adminListPage(Integer page, Integer limit, String keyword);

    Object adminSetVisibility(Integer id, Boolean visible);

    Object importProblem(MultipartFile file) throws IOException, NoSuchAlgorithmException;

    Object adminListBriefPage(Integer page, Integer limit, String keyword, String difficulty);

    void exportProblem(List<Integer> idList, OutputStream httpout) throws IOException;

    void exportAllProblem(OutputStream outputStream) throws IOException;

    Object getAdminDetail(Integer id);

    Object adminNewProblem(Problem problem, MultipartFile multipartFile) throws IOException, NoSuchAlgorithmException;

    Object adminUpdateProblem(Problem problem) throws IOException;

    Object adminUpdateProblemWithCases(Problem problem, MultipartFile multipartFile) throws IOException, NoSuchAlgorithmException;
}