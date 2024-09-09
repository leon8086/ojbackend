package xmut.cs.ojbackend.entity.entitymapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import xmut.cs.ojbackend.entity.Exam;
import xmut.cs.ojbackend.entity.VO.VOExamBrief;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-09-09T05:30:33+0000",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.12 (Ubuntu)"
)
@Component
public class VoExamBriefWrapperImpl implements VoExamBriefWrapper {

    @Override
    public Exam toEntity(VOExamBrief vo) {
        if ( vo == null ) {
            return null;
        }

        Exam.ExamBuilder exam = Exam.builder();

        exam.id( vo.getId() );
        exam.title( vo.getTitle() );
        exam.description( vo.getDescription() );
        exam.startTime( vo.getStartTime() );
        exam.endTime( vo.getEndTime() );
        exam.courseId( vo.getCourseId() );
        exam.isEnded( vo.getIsEnded() );
        exam.problemCount( vo.getProblemCount() );

        return exam.build();
    }

    @Override
    public VOExamBrief toO(Exam entity) {
        if ( entity == null ) {
            return null;
        }

        VOExamBrief.VOExamBriefBuilder vOExamBrief = VOExamBrief.builder();

        vOExamBrief.id( entity.getId() );
        vOExamBrief.title( entity.getTitle() );
        vOExamBrief.description( entity.getDescription() );
        vOExamBrief.startTime( entity.getStartTime() );
        vOExamBrief.endTime( entity.getEndTime() );
        vOExamBrief.courseId( entity.getCourseId() );
        vOExamBrief.isEnded( entity.getIsEnded() );
        vOExamBrief.problemCount( entity.getProblemCount() );

        return vOExamBrief.build();
    }

    @Override
    public List<Exam> toEntity(List<VOExamBrief> voList) {
        if ( voList == null ) {
            return null;
        }

        List<Exam> list = new ArrayList<Exam>( voList.size() );
        for ( VOExamBrief vOExamBrief : voList ) {
            list.add( toEntity( vOExamBrief ) );
        }

        return list;
    }

    @Override
    public List<VOExamBrief> toVo(List<Exam> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<VOExamBrief> list = new ArrayList<VOExamBrief>( entityList.size() );
        for ( Exam exam : entityList ) {
            list.add( toO( exam ) );
        }

        return list;
    }
}
