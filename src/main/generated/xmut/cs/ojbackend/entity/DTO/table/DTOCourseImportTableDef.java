package xmut.cs.ojbackend.entity.DTO.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

// Auto generate by mybatis-flex, do not modify it.
public class DTOCourseImportTableDef extends TableDef {

    /**
     * 实体类。

 @author leon
 @since 2024-06-03
     */
    public static final DTOCourseImportTableDef D_T_O_COURSE_IMPORT = new DTOCourseImportTableDef();

    public final QueryColumn ID = new QueryColumn(this, "id");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID};

    public DTOCourseImportTableDef() {
        super("", "course");
    }

    private DTOCourseImportTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public DTOCourseImportTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new DTOCourseImportTableDef("", "course", alias));
    }

}
