package xmut.cs.ojbackend.service;

import com.alibaba.fastjson2.JSONObject;
import com.mybatisflex.core.service.IService;
import xmut.cs.ojbackend.entity.JudgeServer;

/**
 *  服务层。
 *
 * @author leon
 * @since 2024-06-03
 */
public interface JudgeServerService extends IService<JudgeServer> {

    void updateServer(JSONObject source, String ip);

    String selectServer();
}
