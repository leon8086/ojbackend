package xmut.cs.ojbackend.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import xmut.cs.ojbackend.entity.AuthPermission;
import xmut.cs.ojbackend.mapper.AuthPermissionMapper;
import xmut.cs.ojbackend.service.AuthPermissionService;
import org.springframework.stereotype.Service;

/**
 *  服务层实现。
 *
 * @author leon
 * @since 2024-06-03
 */
@Service
public class AuthPermissionServiceImpl extends ServiceImpl<AuthPermissionMapper, AuthPermission> implements AuthPermissionService {

}
