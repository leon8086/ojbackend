package xmut.cs.ojbackend.entity.VO.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

// Auto generate by mybatis-flex, do not modify it.
public class VOSubmissionDetailTableDef extends TableDef {

    /**
     * 实体类。

 @author leon
 @since 2024-06-03
     */
    public static final VOSubmissionDetailTableDef V_O_SUBMISSION_DETAIL = new VOSubmissionDetailTableDef();

    public final QueryColumn ID = new QueryColumn(this, "id");

    public final QueryColumn IP = new QueryColumn(this, "ip");

    public final QueryColumn CODE = new QueryColumn(this, "code");

    public final QueryColumn INFO = new QueryColumn(this, "info");

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
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, IP, CODE, INFO, RESULT, USER_ID, LANGUAGE, USERNAME, PROBLEM_ID, CREATE_TIME, STATISTIC_INFO};

    public VOSubmissionDetailTableDef() {
        super("", "submission");
    }

    private VOSubmissionDetailTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public VOSubmissionDetailTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new VOSubmissionDetailTableDef("", "submission", alias));
    }

}
