package xmut.cs.ojbackend.service.impl;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xmut.cs.ojbackend.entity.Grade;
import xmut.cs.ojbackend.mapper.GradeMapper;
import xmut.cs.ojbackend.service.GradeService;
import xmut.cs.ojbackend.utils.CommonUtil;

import static xmut.cs.ojbackend.entity.table.GradeTableDef.GRADE;

/**
 *  服务层实现。
 *
 * @author leon
 * @since 2024-08-14
 */
@Service
public class GradeServiceImpl extends ServiceImpl<GradeMapper, Grade> implements GradeService {

    @Autowired
    private CommonUtil commonUtil;

    @Override
    public Object all() {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.where( GRADE.ID.ne(1) );
        return mapper.selectListByQuery(queryWrapper);
    }
}
