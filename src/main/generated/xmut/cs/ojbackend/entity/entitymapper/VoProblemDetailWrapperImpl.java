package xmut.cs.ojbackend.entity.entitymapper;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import xmut.cs.ojbackend.entity.Problem;
import xmut.cs.ojbackend.entity.VO.VOProblemDetail;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-09-09T05:30:33+0000",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.12 (Ubuntu)"
)
@Component
public class VoProblemDetailWrapperImpl implements VoProblemDetailWrapper {

    @Override
    public Problem toEntity(VOProblemDetail vo) {
        if ( vo == null ) {
            return null;
        }

        Problem.ProblemBuilder problem = Problem.builder();

        problem.id( vo.getId() );
        problem.title( vo.getTitle() );
        problem.description( vo.getDescription() );
        problem.inputDescription( vo.getInputDescription() );
        problem.outputDescription( vo.getOutputDescription() );
        JSONArray jSONArray = vo.getSamples();
        if ( jSONArray != null ) {
            problem.samples( new JSONArray( jSONArray ) );
        }
        problem.hint( vo.getHint() );
        List<String> list = vo.getLanguages();
        if ( list != null ) {
            problem.languages( new ArrayList<String>( list ) );
        }
        JSONObject jSONObject = vo.getTemplate();
        if ( jSONObject != null ) {
            problem.template( new JSONObject( jSONObject ) );
        }
        problem.timeLimit( vo.getTimeLimit() );
        problem.memoryLimit( vo.getMemoryLimit() );
        problem.visible( vo.getVisible() );
        problem.difficulty( vo.getDifficulty() );
        problem.submissionNumber( vo.getSubmissionNumber() );
        problem.acceptedNumber( vo.getAcceptedNumber() );
        problem.displayId( vo.getDisplayId() );
        JSONObject jSONObject1 = vo.getStatisticInfo();
        if ( jSONObject1 != null ) {
            problem.statisticInfo( new JSONObject( jSONObject1 ) );
        }
        problem.totalScore( vo.getTotalScore() );
        problem.majorTagId( vo.getMajorTagId() );
        problem.subTagId( vo.getSubTagId() );

        return problem.build();
    }

    @Override
    public VOProblemDetail toO(Problem entity) {
        if ( entity == null ) {
            return null;
        }

        VOProblemDetail.VOProblemDetailBuilder vOProblemDetail = VOProblemDetail.builder();

        vOProblemDetail.id( entity.getId() );
        vOProblemDetail.title( entity.getTitle() );
        vOProblemDetail.description( entity.getDescription() );
        vOProblemDetail.inputDescription( entity.getInputDescription() );
        vOProblemDetail.outputDescription( entity.getOutputDescription() );
        JSONArray jSONArray = entity.getSamples();
        if ( jSONArray != null ) {
            vOProblemDetail.samples( new JSONArray( jSONArray ) );
        }
        vOProblemDetail.hint( entity.getHint() );
        List<String> list = entity.getLanguages();
        if ( list != null ) {
            vOProblemDetail.languages( new ArrayList<String>( list ) );
        }
        JSONObject jSONObject = entity.getTemplate();
        if ( jSONObject != null ) {
            vOProblemDetail.template( new JSONObject( jSONObject ) );
        }
        vOProblemDetail.timeLimit( entity.getTimeLimit() );
        vOProblemDetail.memoryLimit( entity.getMemoryLimit() );
        vOProblemDetail.difficulty( entity.getDifficulty() );
        vOProblemDetail.submissionNumber( entity.getSubmissionNumber() );
        vOProblemDetail.acceptedNumber( entity.getAcceptedNumber() );
        vOProblemDetail.displayId( entity.getDisplayId() );
        vOProblemDetail.visible( entity.getVisible() );
        JSONObject jSONObject1 = entity.getStatisticInfo();
        if ( jSONObject1 != null ) {
            vOProblemDetail.statisticInfo( new JSONObject( jSONObject1 ) );
        }
        vOProblemDetail.totalScore( entity.getTotalScore() );
        vOProblemDetail.majorTagId( entity.getMajorTagId() );
        vOProblemDetail.subTagId( entity.getSubTagId() );

        return vOProblemDetail.build();
    }

    @Override
    public List<Problem> toEntity(List<VOProblemDetail> voList) {
        if ( voList == null ) {
            return null;
        }

        List<Problem> list = new ArrayList<Problem>( voList.size() );
        for ( VOProblemDetail vOProblemDetail : voList ) {
            list.add( toEntity( vOProblemDetail ) );
        }

        return list;
    }

    @Override
    public List<VOProblemDetail> toVo(List<Problem> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<VOProblemDetail> list = new ArrayList<VOProblemDetail>( entityList.size() );
        for ( Problem problem : entityList ) {
            list.add( toO( problem ) );
        }

        return list;
    }
}
