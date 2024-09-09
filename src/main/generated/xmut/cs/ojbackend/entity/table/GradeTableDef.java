package xmut.cs.ojbackend.entity.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

// Auto generate by mybatis-flex, do not modify it.
public class GradeTableDef extends TableDef {

    /**
     * 实体类。

 @author leon
 @since 2024-08-14
     */
    public static final GradeTableDef GRADE = new GradeTableDef();

    public final QueryColumn ID = new QueryColumn(this, "id");

    public final QueryColumn NAME = new QueryColumn(this, "name");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, NAME};

    public GradeTableDef() {
        super("", "grade");
    }

    private GradeTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public GradeTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new GradeTableDef("", "grade", alias));
    }

}
