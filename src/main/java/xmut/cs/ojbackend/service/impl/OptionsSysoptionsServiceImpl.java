package xmut.cs.ojbackend.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import xmut.cs.ojbackend.base.Result;
import xmut.cs.ojbackend.entity.OptionsSysoptions;
import xmut.cs.ojbackend.mapper.OptionsSysoptionsMapper;
import xmut.cs.ojbackend.service.OptionsSysoptionsService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static xmut.cs.ojbackend.entity.table.OptionsSysoptionsTableDef.OPTIONS_SYSOPTIONS;

/**
 *  服务层实现。
 *
 * @author leon
 * @since 2024-06-03
 */
@Service
public class OptionsSysoptionsServiceImpl extends ServiceImpl<OptionsSysoptionsMapper, OptionsSysoptions> implements OptionsSysoptionsService {
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

    @Override
    public Result getInfo() {
        List<OptionsSysoptions> optionsSysoptions = list();
        Map<String, Object> ret = new HashMap<String, Object>();
        for( OptionsSysoptions opt : optionsSysoptions ){
            ret.put( opt.getKey(), opt.getValue() );
        }
        return Result.success(ret);
    }
}
