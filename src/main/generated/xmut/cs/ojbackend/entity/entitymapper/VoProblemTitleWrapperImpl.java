package xmut.cs.ojbackend.entity.entitymapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import xmut.cs.ojbackend.entity.Problem;
import xmut.cs.ojbackend.entity.VO.VOProblemTitle;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-09-09T05:30:33+0000",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.12 (Ubuntu)"
)
@Component
public class VoProblemTitleWrapperImpl implements VoProblemTitleWrapper {

    @Override
    public Problem toEntity(VOProblemTitle vo) {
        if ( vo == null ) {
            return null;
        }

        Problem.ProblemBuilder problem = Problem.builder();

        problem.id( vo.getId() );
        problem.title( vo.getTitle() );
        List<String> list = vo.getLanguages();
        if ( list != null ) {
            problem.languages( new ArrayList<String>( list ) );
        }
        problem.difficulty( vo.getDifficulty() );
        problem.submissionNumber( vo.getSubmissionNumber() );
        problem.acceptedNumber( vo.getAcceptedNumber() );
        problem.displayId( vo.getDisplayId() );
        problem.majorTagId( vo.getMajorTagId() );
        problem.subTagId( vo.getSubTagId() );

        return problem.build();
    }

    @Override
    public VOProblemTitle toO(Problem entity) {
        if ( entity == null ) {
            return null;
        }

        VOProblemTitle.VOProblemTitleBuilder vOProblemTitle = VOProblemTitle.builder();

        vOProblemTitle.id( entity.getId() );
        vOProblemTitle.title( entity.getTitle() );
        List<String> list = entity.getLanguages();
        if ( list != null ) {
            vOProblemTitle.languages( new ArrayList<String>( list ) );
        }
        vOProblemTitle.difficulty( entity.getDifficulty() );
        vOProblemTitle.submissionNumber( entity.getSubmissionNumber() );
        vOProblemTitle.acceptedNumber( entity.getAcceptedNumber() );
        vOProblemTitle.displayId( entity.getDisplayId() );
        vOProblemTitle.majorTagId( entity.getMajorTagId() );
        vOProblemTitle.subTagId( entity.getSubTagId() );

        return vOProblemTitle.build();
    }

    @Override
    public List<Problem> toEntity(List<VOProblemTitle> voList) {
        if ( voList == null ) {
            return null;
        }

        List<Problem> list = new ArrayList<Problem>( voList.size() );
        for ( VOProblemTitle vOProblemTitle : voList ) {
            list.add( toEntity( vOProblemTitle ) );
        }

        return list;
    }

    @Override
    public List<VOProblemTitle> toVo(List<Problem> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<VOProblemTitle> list = new ArrayList<VOProblemTitle>( entityList.size() );
        for ( Problem problem : entityList ) {
            list.add( toO( problem ) );
        }

        return list;
    }
}
