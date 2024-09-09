package xmut.cs.ojbackend.config;

import com.mybatisflex.core.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import xmut.cs.ojbackend.entity.Exam;
import xmut.cs.ojbackend.mapper.exam.ExamMapper;
import xmut.cs.ojbackend.mapper.exam.ExamProfilesMapper;
import xmut.cs.ojbackend.service.ExamService;

import java.util.Calendar;
import java.util.Date;

import static xmut.cs.ojbackend.entity.table.ExamTableDef.EXAM;

@Component
public class ExamStatusCheckSchedule {

    @Autowired
    ExamMapper examMapper;

    @Autowired
    ExamService examService;

    @Autowired
    ExamProfilesMapper examProfilesMapper;

    @Scheduled(fixedDelay=1000*60)
    public void checkExamStatus(){
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.where(EXAM.IS_ENDED.eq(false));
        Date current = Calendar.getInstance().getTime();
        wrapper.and(EXAM.END_TIME.le(current));
        for( Exam exam : examMapper.selectListByQuery(wrapper) ){
            examService.adminCloseExam(exam.getId(), true);
        }
    }
}
