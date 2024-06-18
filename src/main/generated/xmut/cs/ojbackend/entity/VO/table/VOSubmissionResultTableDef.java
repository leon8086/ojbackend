package xmut.cs.ojbackend.entity.VO.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

// Auto generate by mybatis-flex, do not modify it.
public class VOSubmissionResultTableDef extends TableDef {

    /**
     * 实体类。

 @author leon
 @since 2024-06-03
     */
    public static final VOSubmissionResultTableDef V_O_SUBMISSION_RESULT = new VOSubmissionResultTableDef();

    public final QueryColumn ID = new QueryColumn(this, "id");

    public final QueryColumn INFO = new QueryColumn(this, "info");

    public final QueryColumn RESULT = new QueryColumn(this, "result");

    public final QueryColumn USER_ID = new QueryColumn(this, "user_id");

    public final QueryColumn PROBLEM_ID = new QueryColumn(this, "problem_id");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, INFO, RESULT, USER_ID, PROBLEM_ID};

    public VOSubmissionResultTableDef() {
        super("", "submission");
    }

    private VOSubmissionResultTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public VOSubmissionResultTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new VOSubmissionResultTableDef("", "submission", alias));
    }

}
