package xmut.cs.ojbackend.entity.VO.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

// Auto generate by mybatis-flex, do not modify it.
public class VOCourseListTableDef extends TableDef {

    /**
     * 实体类。

 @author leon
 @since 2024-06-03
     */
    public static final VOCourseListTableDef V_O_COURSE_LIST = new VOCourseListTableDef();

    public final QueryColumn ID = new QueryColumn(this, "id");

    public final QueryColumn OWNER = new QueryColumn(this, "owner");

    public final QueryColumn OWNER_ID = new QueryColumn(this, "owner_id");

    public final QueryColumn IS_CLOSED = new QueryColumn(this, "is_closed");

    public final QueryColumn COURSE_NAME = new QueryColumn(this, "course_name");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, OWNER, OWNER_ID, IS_CLOSED, COURSE_NAME};

    public VOCourseListTableDef() {
        super("", "course");
    }

    private VOCourseListTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public VOCourseListTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new VOCourseListTableDef("", "course", alias));
    }

}
