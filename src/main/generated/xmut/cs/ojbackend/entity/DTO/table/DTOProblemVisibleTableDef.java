package xmut.cs.ojbackend.entity.DTO.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

// Auto generate by mybatis-flex, do not modify it.
public class DTOProblemVisibleTableDef extends TableDef {

    public static final DTOProblemVisibleTableDef D_T_O_PROBLEM_VISIBLE = new DTOProblemVisibleTableDef();

    public final QueryColumn ID = new QueryColumn(this, "id");

    public final QueryColumn VISIBLE = new QueryColumn(this, "visible");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, VISIBLE};

    public DTOProblemVisibleTableDef() {
        super("", "problem");
    }

    private DTOProblemVisibleTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public DTOProblemVisibleTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new DTOProblemVisibleTableDef("", "problem", alias));
    }

}
