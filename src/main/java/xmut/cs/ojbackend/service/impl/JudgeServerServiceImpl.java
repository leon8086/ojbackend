package xmut.cs.ojbackend.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.util.UpdateEntity;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import xmut.cs.ojbackend.entity.JudgeServer;
import xmut.cs.ojbackend.mapper.JudgeServerMapper;
import xmut.cs.ojbackend.service.JudgeServerService;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import static xmut.cs.ojbackend.entity.table.JudgeServerTableDef.JUDGE_SERVER;

/**
 *  服务层实现。
 *
 * @author leon
 * @since 2024-06-03
 */
@Service
public class JudgeServerServiceImpl extends ServiceImpl<JudgeServerMapper, JudgeServer> implements JudgeServerService {

    @Value("${judge-server.url:default}")
    public String judgeServerUrl;

    @Override
    public void updateServer(JSONObject source, String ip) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.where(JUDGE_SERVER.HOSTNAME.eq(source.getString("hostname")));
        JudgeServer server = getOne(wrapper);
        if( server == null ){
            server = UpdateEntity.of(JudgeServer.class);
            server.setHostname(source.getString("hostname"));
            server.setTaskNumber(0);
        }
        server.setCpuUsage(source.getDouble("cpu"));
        server.setCpuCore(source.getIntValue("cpu_core"));
        server.setMemoryUsage(source.getDouble("memory"));
        server.setJudgerVersion(source.getString("judger_version"));
        server.setServiceUrl(source.getString("service_url"));
        server.setLastHeartbeat(Calendar.getInstance().getTime());
        server.setIp(ip);
        server.setIsDisabled(false);
        saveOrUpdate(server);
        //pper.update(server);
    }

    @Override
    public String selectServer() {
        if(Objects.equals(judgeServerUrl, "default")) {
            QueryWrapper wrapper = new QueryWrapper();
            wrapper.where(JUDGE_SERVER.IS_DISABLED.eq(false));
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.SECOND, -15);
            Date timeLimit = calendar.getTime();
            wrapper.and(JUDGE_SERVER.LAST_HEARTBEAT.ge(timeLimit));
            //wrapper.orderBy(JUDGE_SERVER.TASK_NUMBER.asc());
            JudgeServer server = mapper.selectOneByQuery(wrapper);
            if (server == null) {
                return "";
            }
            String ret = server.getServiceUrl();
            return ret;
        }
        else{
            return judgeServerUrl;
        }
    }
}
