package xmut.cs.ojbackend.entity.entitymapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.repository.support.Repositories;
import xmut.cs.ojbackend.entity.VO.VOProblemTitle;
import xmut.cs.ojbackend.entity.Problem;

@Mapper(componentModel = "spring",unmappedTargetPolicy= ReportingPolicy.IGNORE)
public interface VoProblemTitleWrapper extends BaseWrapper<VOProblemTitle, Problem> {
}