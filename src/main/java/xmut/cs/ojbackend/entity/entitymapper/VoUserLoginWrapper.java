package xmut.cs.ojbackend.entity.entitymapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import xmut.cs.ojbackend.entity.User;
import xmut.cs.ojbackend.entity.VO.VOUserLogin;

@Mapper(componentModel = "spring",unmappedTargetPolicy= ReportingPolicy.IGNORE)
public interface VoUserLoginWrapper extends BaseWrapper<VOUserLogin, User> {
}