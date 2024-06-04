package xmut.cs.ojbackend.entity.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

// Auto generate by mybatis-flex, do not modify it.
public class ProblemTagsTableDef extends TableDef {

    /**
     * 实体类。

 @author leon
 @since 2024-06-03
     */
    public static final ProblemTagsTableDef PROBLEM_TAGS = new ProblemTagsTableDef();

    public final QueryColumn ID = new QueryColumn(this, "id");

    public final QueryColumn PROBLEM_ID = new QueryColumn(this, "problem_id");

    public final QueryColumn PROBLEMTAG_ID = new QueryColumn(this, "problemtag_id");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, PROBLEM_ID, PROBLEMTAG_ID};

    public ProblemTagsTableDef() {
        super("", "problem_tags");
    }

    private ProblemTagsTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public ProblemTagsTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new ProblemTagsTableDef("", "problem_tags", alias));
    }

}
