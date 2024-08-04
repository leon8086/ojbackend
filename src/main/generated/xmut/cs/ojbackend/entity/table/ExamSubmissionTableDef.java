package xmut.cs.ojbackend.entity.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

// Auto generate by mybatis-flex, do not modify it.
public class ExamSubmissionTableDef extends TableDef {

    /**
     * 实体类。

 @author leon
 @since 2024-06-26
     */
    public static final ExamSubmissionTableDef EXAM_SUBMISSION = new ExamSubmissionTableDef();

    public final QueryColumn ID = new QueryColumn(this, "id");

    public final QueryColumn IP = new QueryColumn(this, "ip");

    public final QueryColumn CODE = new QueryColumn(this, "code");

    public final QueryColumn INFO = new QueryColumn(this, "info");

    public final QueryColumn EXAM_ID = new QueryColumn(this, "exam_id");

    public final QueryColumn RESULT = new QueryColumn(this, "result");

    public final QueryColumn USER_ID = new QueryColumn(this, "user_id");

    public final QueryColumn LANGUAGE = new QueryColumn(this, "language");

    public final QueryColumn USERNAME = new QueryColumn(this, "username");

    public final QueryColumn PROBLEM_ID = new QueryColumn(this, "problem_id");

    public final QueryColumn CREATE_TIME = new QueryColumn(this, "create_time");

    public final QueryColumn STATISTIC_INFO = new QueryColumn(this, "statistic_info");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, IP, CODE, INFO, EXAM_ID, RESULT, USER_ID, LANGUAGE, USERNAME, PROBLEM_ID, CREATE_TIME, STATISTIC_INFO};

    public ExamSubmissionTableDef() {
        super("", "exam_submission");
    }

    private ExamSubmissionTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public ExamSubmissionTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new ExamSubmissionTableDef("", "exam_submission", alias));
    }

}
