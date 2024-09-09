package xmut.cs.ojbackend.service;

import com.mybatisflex.core.service.IService;
import xmut.cs.ojbackend.entity.Grade;

/**
 *  服务层。
 *
 * @author leon
 * @since 2024-08-14
 */
public interface GradeService extends IService<Grade> {

    Object all();
}
