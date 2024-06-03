package xmut.cs.ojbackend.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import xmut.cs.ojbackend.entity.ContestAnnouncement;
import xmut.cs.ojbackend.mapper.ContestAnnouncementMapper;
import xmut.cs.ojbackend.service.ContestAnnouncementService;
import org.springframework.stereotype.Service;

/**
 *  服务层实现。
 *
 * @author leon
 * @since 2024-06-03
 */
@Service
public class ContestAnnouncementServiceImpl extends ServiceImpl<ContestAnnouncementMapper, ContestAnnouncement> implements ContestAnnouncementService {

}
