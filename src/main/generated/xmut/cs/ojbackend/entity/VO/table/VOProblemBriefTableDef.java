package xmut.cs.ojbackend.entity.VO.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

// Auto generate by mybatis-flex, do not modify it.
public class VOProblemBriefTableDef extends TableDef {

    public static final VOProblemBriefTableDef V_O_PROBLEM_BRIEF = new VOProblemBriefTableDef();

    public final QueryColumn ID = new QueryColumn(this, "id");

    public final QueryColumn TITLE = new QueryColumn(this, "title");

    public final QueryColumn DISPLAY_ID = new QueryColumn(this, "display_id");

    public final QueryColumn DIFFICULTY = new QueryColumn(this, "difficulty");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, TITLE, DISPLAY_ID, DIFFICULTY};

    public VOProblemBriefTableDef() {
        super("", "problem");
    }

    private VOProblemBriefTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public VOProblemBriefTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new VOProblemBriefTableDef("", "problem", alias));
    }

}
