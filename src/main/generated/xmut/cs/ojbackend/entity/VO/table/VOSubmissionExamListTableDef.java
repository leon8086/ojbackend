package xmut.cs.ojbackend.entity.VO.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

// Auto generate by mybatis-flex, do not modify it.
public class VOSubmissionExamListTableDef extends TableDef {

    /**
     * 实体类。

 @author leon
 @since 2024-06-03
     */
    public static final VOSubmissionExamListTableDef V_O_SUBMISSION_EXAM_LIST = new VOSubmissionExamListTableDef();

    public final QueryColumn ID = new QueryColumn(this, "id");

    public final QueryColumn CODE = new QueryColumn(this, "code");

    public final QueryColumn INFO = new QueryColumn(this, "info");

    public final QueryColumn RESULT = new QueryColumn(this, "result");

    public final QueryColumn SHARED = new QueryColumn(this, "shared");

    public final QueryColumn USER_ID = new QueryColumn(this, "user_id");

    public final QueryColumn LANGUAGE = new QueryColumn(this, "language");

    public final QueryColumn USERNAME = new QueryColumn(this, "username");

    public final QueryColumn CONTEST_ID = new QueryColumn(this, "contest_id");

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
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, CODE, INFO, RESULT, SHARED, USER_ID, LANGUAGE, USERNAME, CONTEST_ID, PROBLEM_ID, CREATE_TIME, STATISTIC_INFO};

    public VOSubmissionExamListTableDef() {
        super("", "submission");
    }

    private VOSubmissionExamListTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public VOSubmissionExamListTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new VOSubmissionExamListTableDef("", "submission", alias));
    }

}
