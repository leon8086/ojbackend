package xmut.cs.ojbackend.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import xmut.cs.ojbackend.entity.Contest;
import xmut.cs.ojbackend.mapper.ContestMapper;
import xmut.cs.ojbackend.service.ContestService;
import org.springframework.stereotype.Service;

/**
 *  服务层实现。
 *
 * @author leon
 * @since 2024-06-03
 */
@Service
public class ContestServiceImpl extends ServiceImpl<ContestMapper, Contest> implements ContestService {

}
