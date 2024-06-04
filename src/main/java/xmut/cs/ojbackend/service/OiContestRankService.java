package xmut.cs.ojbackend.service;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.service.IService;
import xmut.cs.ojbackend.entity.OiContestRank;

/**
 *  服务层。
 *
 * @author leon
 * @since 2024-06-03
 */
public interface OiContestRankService extends IService<OiContestRank> {
    OiContestRank getByUserIdAndContestId(Integer userId, Integer contestId);

    Page<OiContestRank> page(Integer pageNum, Integer pageSize, Integer contestId);
}
