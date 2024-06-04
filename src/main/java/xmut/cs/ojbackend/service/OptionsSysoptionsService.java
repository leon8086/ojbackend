package xmut.cs.ojbackend.service;

import com.mybatisflex.core.service.IService;
import xmut.cs.ojbackend.entity.OptionsSysoptions;

/**
 *  服务层。
 *
 * @author leon
 * @since 2024-06-03
 */
public interface OptionsSysoptionsService extends IService<OptionsSysoptions> {
    Object getValue(String key);

    void updateValue(String key, Object value);
}
