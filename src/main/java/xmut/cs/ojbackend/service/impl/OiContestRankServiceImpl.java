package xmut.cs.ojbackend.service.impl;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import xmut.cs.ojbackend.entity.OiContestRank;
import xmut.cs.ojbackend.entity.table.OiContestRankTableDef;
import xmut.cs.ojbackend.mapper.OiContestRankMapper;
import xmut.cs.ojbackend.service.OiContestRankService;
import org.springframework.stereotype.Service;

/**
 *  服务层实现。
 *
 * @author leon
 * @since 2024-06-03
 */
@Service
public class OiContestRankServiceImpl extends ServiceImpl<OiContestRankMapper, OiContestRank> implements OiContestRankService {
    @Override
    public OiContestRank getByUserIdAndContestId(Integer userId, Integer contestId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.where(OiContestRankTableDef.OI_CONTEST_RANK.USER_ID.eq(userId)).and(OiContestRankTableDef.OI_CONTEST_RANK.CONTEST_ID.eq(contestId));
        return this.getOne(queryWrapper);
    }

    @Override
    public Page<OiContestRank> page(Integer pageNum, Integer pageSize, Integer contestId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.where(OiContestRankTableDef.OI_CONTEST_RANK.CONTEST_ID.eq(contestId));
        queryWrapper.orderBy(OiContestRankTableDef.OI_CONTEST_RANK.TOTAL_SCORE, false);
        return this.mapper.paginateWithRelations(pageNum, pageSize, queryWrapper);
    }
}
