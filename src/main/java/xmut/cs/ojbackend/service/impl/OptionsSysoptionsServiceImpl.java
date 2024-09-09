package xmut.cs.ojbackend.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import xmut.cs.ojbackend.entity.OptionsSysoptions;
import xmut.cs.ojbackend.mapper.OptionsSysoptionsMapper;
import xmut.cs.ojbackend.service.OptionsSysoptionsService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import static xmut.cs.ojbackend.entity.table.OptionsSysoptionsTableDef.OPTIONS_SYSOPTIONS;

/**
 *  服务层实现。
 *
 * @author leon
 * @since 2024-06-03
 */
@Service
public class OptionsSysoptionsServiceImpl extends ServiceImpl<OptionsSysoptionsMapper, OptionsSysoptions> implements OptionsSysoptionsService {
    @Autowired
    RedisTemplate<Object, Object> redisTemplate;

    @Override
    public Object getValue(String key) {
        OptionsSysoptions optionsSysoptions = this.getOne(OPTIONS_SYSOPTIONS.KEY.eq(key));
        return optionsSysoptions.getValue();
    }

    @Override
    public void updateValue(String key, Object value) {
        OptionsSysoptions option = new OptionsSysoptions();
        option.setValue(value);
        this.update(option, OPTIONS_SYSOPTIONS.KEY.eq(key));
    }

    public Object getInfoFromDB(){
        List<OptionsSysoptions> optionsSysoptions = list();
        Map<String, Object> ret = new HashMap<String, Object>();
        for( OptionsSysoptions opt : optionsSysoptions ){
            ret.put( opt.getKey(), opt.getValue() );
        }
        return ret;
    }

    @Override
    public Object getInfo() {
        if(Boolean.TRUE.equals(redisTemplate.hasKey("config"))){
            return redisTemplate.opsForValue().get("config");
        }
        else {
            Object info = getInfoFromDB();
            redisTemplate.opsForValue().set("config", info );
            return info;
        }
    }

    @Override
    public Object updateParams(Map<String, Object> params) {
        for( Entry<String,Object> entry : params.entrySet() ){
            updateValue( entry.getKey(), entry.getValue());
        }
        redisTemplate.opsForValue().set("config", getInfoFromDB() );
        return "更新成功";
    }
}