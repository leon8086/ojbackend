package xmut.cs.ojbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import xmut.cs.ojbackend.base.Result;
import xmut.cs.ojbackend.service.OptionsSysoptionsService;

import java.util.Map;
/**
 *  控制层。
 *
 * @author leon
 * @since 2024-06-03
 */
@RestController
public class OptionsSysoptionsController {

    @Autowired
    private OptionsSysoptionsService optionsSysoptionsService;

    @Value("${spring.data.redis.host}")
    private String redisHost;

    @PostMapping("/admin/opt/update")
    public Result update(@RequestBody Map<String, Object> params) {
        return Result.success(optionsSysoptionsService.updateParams( params ));
    }

    @GetMapping("/opt/list")
    public Object list() {
        Object ret = optionsSysoptionsService.getInfo();
        return Result.success(ret);
    }

    @GetMapping("/opt/redis")
    public Object showOpt(){
        return Result.success(redisHost);
    }
}
