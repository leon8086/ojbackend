package xmut.cs.ojbackend.entity.VO.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

// Auto generate by mybatis-flex, do not modify it.
public class VOProblemStatisticTableDef extends TableDef {

    public static final VOProblemStatisticTableDef V_O_PROBLEM_STATISTIC = new VOProblemStatisticTableDef();

    public final QueryColumn ID = new QueryColumn(this, "id");

    public final QueryColumn TITLE = new QueryColumn(this, "title");

    public final QueryColumn SUB_TAG = new QueryColumn(this, "sub_tag");

    public final QueryColumn MAJOR_TAG = new QueryColumn(this, "major_tag");

    public final QueryColumn SUB_TAG_ID = new QueryColumn(this, "sub_tag_id");

    public final QueryColumn DISPLAY_ID = new QueryColumn(this, "display_id");

    public final QueryColumn DIFFICULTY = new QueryColumn(this, "difficulty");

    public final QueryColumn MAJOR_TAG_ID = new QueryColumn(this, "major_tag_id");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, TITLE, SUB_TAG, MAJOR_TAG, SUB_TAG_ID, DISPLAY_ID, DIFFICULTY, MAJOR_TAG_ID};

    public VOProblemStatisticTableDef() {
        super("", "problem");
    }

    private VOProblemStatisticTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public VOProblemStatisticTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new VOProblemStatisticTableDef("", "problem", alias));
    }

}
