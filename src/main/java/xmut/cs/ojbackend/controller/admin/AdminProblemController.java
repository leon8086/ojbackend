package xmut.cs.ojbackend.controller.admin;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import xmut.cs.ojbackend.base.Result;
import xmut.cs.ojbackend.entity.DTO.DTOProblemVisible;
import xmut.cs.ojbackend.entity.Problem;
import xmut.cs.ojbackend.service.ProblemService;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 *  控制层。
 *
 * @author leon
 * @since 2024-06-03
 */
@RestController
@RequestMapping("/admin/problem")
public class AdminProblemController {

    @Autowired
    private ProblemService problemService;

    @GetMapping("list")
    public Object list( Integer page, Integer limit, String keyword ){
        Object ret = problemService.adminListPage(page, limit, keyword );
        return Result.success(ret);
    }

    @GetMapping("list_brief")
    public Result listBrief( Integer page, Integer limit, String keyword, String difficulty ) {
        Object ret = problemService.adminListBriefPage( page, limit, keyword, difficulty );
        return Result.success(ret);
    }

    @PostMapping("visible")
    public Object setVisibility(@RequestBody DTOProblemVisible problemVisible){
        System.out.println(problemVisible);
        return Result.success(problemService.adminSetVisibility(problemVisible.getId(), problemVisible.getVisible()));
    }

    @PostMapping("import")
    public Object importProblem(@RequestParam("file") MultipartFile file) throws IOException, NoSuchAlgorithmException {
        return Result.success( problemService.importProblem(file));
    }

    @PostMapping("export")
    public void exportProblem(@RequestBody List<Integer> idList, HttpServletResponse resp ) throws IOException {
        resp.setContentType("application/zip");
        resp.setHeader("Content-Disposition", "attachment; filename=\"problems.zip\"");
        problemService.exportProblem(idList, resp.getOutputStream());
    }

    @PostMapping("export-all")
    public void exportAllProblem(HttpServletResponse resp ) throws IOException {
        resp.setContentType("application/zip");
        resp.setHeader("Content-Disposition", "attachment; filename=\"problems.zip\"");
        problemService.exportAllProblem(resp.getOutputStream());
    }

    @GetMapping("detail")
    public Object getInfo(Integer id) {
        Object ret = problemService.getAdminDetail(id);
        return Result.success(ret);
    }

    @PostMapping("new")
    public Result newProblem( @RequestPart("form") Problem problem, @RequestPart("file") MultipartFile multipartFile) throws IOException, NoSuchAlgorithmException {
        return Result.success(problemService.adminNewProblem( problem, multipartFile));
    }

    @PostMapping( "update" )
    public Result updateProblem( @RequestPart("form") Problem problem) throws IOException, NoSuchAlgorithmException {
        return Result.success(problemService.adminUpdateProblem( problem ));
    }
    @PostMapping( "update-with-cases" )
    public Result updateProblemWithCases( @RequestPart("form") Problem problem, @RequestPart("file") MultipartFile multipartFile) throws IOException, NoSuchAlgorithmException {
        return Result.success(problemService.adminUpdateProblemWithCases( problem, multipartFile));
    }
}
