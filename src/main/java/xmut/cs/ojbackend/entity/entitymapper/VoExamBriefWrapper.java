package xmut.cs.ojbackend.entity.entitymapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import xmut.cs.ojbackend.entity.Exam;
import xmut.cs.ojbackend.entity.VO.VOExamBrief;

@Mapper(componentModel = "spring",unmappedTargetPolicy= ReportingPolicy.IGNORE)
public interface VoExamBriefWrapper extends BaseWrapper<VOExamBrief, Exam> {
}