package xmut.cs.ojbackend.entity.entitymapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import xmut.cs.ojbackend.entity.DTO.DTOUserImport;
import xmut.cs.ojbackend.entity.User;

@Mapper(componentModel = "spring",unmappedTargetPolicy= ReportingPolicy.IGNORE)
public interface DTOUserImportWrapper extends BaseWrapper<DTOUserImport,User> {
}