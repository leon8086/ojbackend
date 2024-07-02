package xmut.cs.ojbackend.entity.entitymapper;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import xmut.cs.ojbackend.entity.Problem;
import xmut.cs.ojbackend.entity.ProblemTag;
import xmut.cs.ojbackend.entity.VO.VOProblemDetail;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-06-30T14:47:05+0000",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.11 (Ubuntu)"
)
@Component
public class VoProblemDetailWrapperImpl implements VoProblemDetailWrapper {

    @Override
    public Problem toEntity(VOProblemDetail arg0) {
        if ( arg0 == null ) {
            return null;
        }

        Problem.ProblemBuilder problem = Problem.builder();

        problem.id( arg0.getId() );
        problem.title( arg0.getTitle() );
        problem.description( arg0.getDescription() );
        problem.inputDescription( arg0.getInputDescription() );
        problem.outputDescription( arg0.getOutputDescription() );
        JSONArray jSONArray = arg0.getSamples();
        if ( jSONArray != null ) {
            problem.samples( new JSONArray( jSONArray ) );
        }
        problem.hint( arg0.getHint() );
        List<String> list = arg0.getLanguages();
        if ( list != null ) {
            problem.languages( new ArrayList<String>( list ) );
        }
        JSONObject jSONObject = arg0.getTemplate();
        if ( jSONObject != null ) {
            problem.template( new JSONObject( jSONObject ) );
        }
        problem.timeLimit( arg0.getTimeLimit() );
        problem.memoryLimit( arg0.getMemoryLimit() );
        problem.difficulty( arg0.getDifficulty() );
        problem.submissionNumber( arg0.getSubmissionNumber() );
        problem.acceptedNumber( arg0.getAcceptedNumber() );
        problem.displayId( arg0.getDisplayId() );
        JSONObject jSONObject1 = arg0.getStatisticInfo();
        if ( jSONObject1 != null ) {
            problem.statisticInfo( new JSONObject( jSONObject1 ) );
        }
        problem.totalScore( arg0.getTotalScore() );
        problem.isPublic( arg0.getIsPublic() );
        JSONObject jSONObject2 = arg0.getIoMode();
        if ( jSONObject2 != null ) {
            problem.ioMode( new JSONObject( jSONObject2 ) );
        }
        problem.shareSubmission( arg0.getShareSubmission() );
        List<ProblemTag> list1 = arg0.getTags();
        if ( list1 != null ) {
            problem.tags( new ArrayList<ProblemTag>( list1 ) );
        }

        return problem.build();
    }

    @Override
    public VOProblemDetail toVo(Problem arg0) {
        if ( arg0 == null ) {
            return null;
        }

        VOProblemDetail.VOProblemDetailBuilder vOProblemDetail = VOProblemDetail.builder();

        vOProblemDetail.id( arg0.getId() );
        vOProblemDetail.title( arg0.getTitle() );
        vOProblemDetail.description( arg0.getDescription() );
        vOProblemDetail.inputDescription( arg0.getInputDescription() );
        vOProblemDetail.outputDescription( arg0.getOutputDescription() );
        JSONArray jSONArray = arg0.getSamples();
        if ( jSONArray != null ) {
            vOProblemDetail.samples( new JSONArray( jSONArray ) );
        }
        vOProblemDetail.hint( arg0.getHint() );
        List<String> list = arg0.getLanguages();
        if ( list != null ) {
            vOProblemDetail.languages( new ArrayList<String>( list ) );
        }
        JSONObject jSONObject = arg0.getTemplate();
        if ( jSONObject != null ) {
            vOProblemDetail.template( new JSONObject( jSONObject ) );
        }
        vOProblemDetail.timeLimit( arg0.getTimeLimit() );
        vOProblemDetail.memoryLimit( arg0.getMemoryLimit() );
        vOProblemDetail.difficulty( arg0.getDifficulty() );
        vOProblemDetail.submissionNumber( arg0.getSubmissionNumber() );
        vOProblemDetail.acceptedNumber( arg0.getAcceptedNumber() );
        vOProblemDetail.displayId( arg0.getDisplayId() );
        JSONObject jSONObject1 = arg0.getStatisticInfo();
        if ( jSONObject1 != null ) {
            vOProblemDetail.statisticInfo( new JSONObject( jSONObject1 ) );
        }
        vOProblemDetail.totalScore( arg0.getTotalScore() );
        vOProblemDetail.isPublic( arg0.getIsPublic() );
        JSONObject jSONObject2 = arg0.getIoMode();
        if ( jSONObject2 != null ) {
            vOProblemDetail.ioMode( new JSONObject( jSONObject2 ) );
        }
        vOProblemDetail.shareSubmission( arg0.getShareSubmission() );
        List<ProblemTag> list1 = arg0.getTags();
        if ( list1 != null ) {
            vOProblemDetail.tags( new ArrayList<ProblemTag>( list1 ) );
        }

        return vOProblemDetail.build();
    }

    @Override
    public List<Problem> toEntity(List<VOProblemDetail> arg0) {
        if ( arg0 == null ) {
            return null;
        }

        List<Problem> list = new ArrayList<Problem>( arg0.size() );
        for ( VOProblemDetail vOProblemDetail : arg0 ) {
            list.add( toEntity( vOProblemDetail ) );
        }

        return list;
    }

    @Override
    public List<VOProblemDetail> toVo(List<Problem> arg0) {
        if ( arg0 == null ) {
            return null;
        }

        List<VOProblemDetail> list = new ArrayList<VOProblemDetail>( arg0.size() );
        for ( Problem problem : arg0 ) {
            list.add( toVo( problem ) );
        }

        return list;
    }
}
