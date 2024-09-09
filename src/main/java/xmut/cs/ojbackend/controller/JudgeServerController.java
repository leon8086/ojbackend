package xmut.cs.ojbackend.controller;

import com.alibaba.fastjson2.JSONObject;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import xmut.cs.ojbackend.base.Result;
import xmut.cs.ojbackend.service.JudgeServerService;

/**
 *  控制层。
 *
 * @author leon
 * @since 2024-06-03
 */
@RestController
public class JudgeServerController {

    @Autowired
    private JudgeServerService judgeServerService;

    @GetMapping("/admin/judger/list")
    public Result list() {
            return Result.success(judgeServerService.list());
    }

    @PostMapping("/judger/heartbeat")
    public Object heartBeat(@RequestBody JSONObject source, HttpServletRequest request){
        judgeServerService.updateServer( source, request.getRemoteAddr());
        JSONObject ret = new JSONObject();
        ret.put("error", null );
        return ret;
    }
}