package xmut.cs.ojbackend.service.impl;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import xmut.cs.ojbackend.entity.Problem;
import xmut.cs.ojbackend.entity.VO.VOProblemTitle;
import xmut.cs.ojbackend.entity.entitymapper.VoProblemTitleWrapper;
import xmut.cs.ojbackend.mapper.ProblemMapper;
import xmut.cs.ojbackend.service.ProblemService;
import org.springframework.stereotype.Service;

import static xmut.cs.ojbackend.entity.table.ProblemTableDef.PROBLEM;
import static xmut.cs.ojbackend.entity.table.ProblemTagTableDef.PROBLEM_TAG;
import static xmut.cs.ojbackend.entity.table.ProblemTagsTableDef.PROBLEM_TAGS;

/**
 *  服务层实现。
 *
 * @author leon
 * @since 2024-06-03
 */
@Service
public class ProblemServiceImpl extends ServiceImpl<ProblemMapper, Problem> implements ProblemService {
    @Autowired
    ProblemMapper problemMapper;

    @Autowired
    VoProblemTitleWrapper voProblemTitleWrapper;

    @Override
    public Object listPage( Integer page, Integer limit, String keyword, String difficulty, String tag ){
        if (page == null) {
            page = 1;
        }
        if (limit == null) {
            limit = 1000;
        }
        QueryWrapper wrapper = new QueryWrapper();
            wrapper.select(
                    PROBLEM.ID,
                    PROBLEM.DISPLAY_ID.as("_id"),
                    PROBLEM.TITLE,
                    PROBLEM.DIFFICULTY,
                    PROBLEM.LANGUAGES,
                    PROBLEM.SUBMISSION_NUMBER,
                    PROBLEM.ACCEPTED_NUMBER);
        if( tag != null && !tag.isEmpty() ) {
            wrapper.from(PROBLEM.as("a"), PROBLEM_TAGS.as("b"), PROBLEM_TAG.as("c") );
            wrapper.where(PROBLEM.ID.eq(PROBLEM_TAGS.PROBLEM_ID));
            wrapper.where(PROBLEM_TAGS.PROBLEMTAG_ID.eq(PROBLEM_TAG.ID));
            wrapper.where(PROBLEM_TAG.NAME.eq(tag));
        }
        if (keyword != null && !keyword.isEmpty()) {
            wrapper.where(PROBLEM.TITLE.like(keyword));
        }
        if (difficulty != null && !difficulty.isEmpty()) {
            wrapper.where(PROBLEM.DIFFICULTY.eq(difficulty));
        }
        wrapper.where(PROBLEM.CONTEST_ID.isNull());
        wrapper.orderBy(PROBLEM.DISPLAY_ID.asc());
        return problemMapper.paginateWithRelationsAs(page, limit, wrapper, VOProblemTitle.class);
    }
}
