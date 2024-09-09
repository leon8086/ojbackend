package xmut.cs.ojbackend.entity.VO.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

// Auto generate by mybatis-flex, do not modify it.
public class VOUserRankTableDef extends TableDef {

    /**
     * 实体类。

 @author leon
 @since 2024-07-15
     */
    public static final VOUserRankTableDef V_O_USER_RANK = new VOUserRankTableDef();

    public final QueryColumn ID = new QueryColumn(this, "id");

    public final QueryColumn GRADE = new QueryColumn(this, "grade");

    public final QueryColumn REAL_NAME = new QueryColumn(this, "real_name");

    public final QueryColumn USERNAME = new QueryColumn(this, "username");

    public final QueryColumn GRADE_NAME = new QueryColumn(this, "grade_name");

    public final QueryColumn LAST_ACCEPT = new QueryColumn(this, "last_accept");

    public final QueryColumn ACCEPTED_NUMBER = new QueryColumn(this, "accepted_number");

    public final QueryColumn SUBMISSION_NUMBER = new QueryColumn(this, "submission_number");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, GRADE, REAL_NAME, USERNAME, GRADE_NAME, LAST_ACCEPT, ACCEPTED_NUMBER, SUBMISSION_NUMBER};

    public VOUserRankTableDef() {
        super("", "user");
    }

    private VOUserRankTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public VOUserRankTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new VOUserRankTableDef("", "user", alias));
    }

}
