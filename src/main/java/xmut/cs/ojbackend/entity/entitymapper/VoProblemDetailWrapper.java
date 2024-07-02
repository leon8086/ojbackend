package xmut.cs.ojbackend.entity.entitymapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import xmut.cs.ojbackend.entity.Problem;
import xmut.cs.ojbackend.entity.VO.VOProblemDetail;

@Mapper(componentModel = "spring",unmappedTargetPolicy= ReportingPolicy.IGNORE)
public interface VoProblemDetailWrapper extends BaseWrapper<VOProblemDetail, Problem> {
}