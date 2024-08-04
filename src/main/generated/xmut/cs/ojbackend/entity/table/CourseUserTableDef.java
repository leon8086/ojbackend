package xmut.cs.ojbackend.entity.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

// Auto generate by mybatis-flex, do not modify it.
public class CourseUserTableDef extends TableDef {

    /**
     * 实体类。

 @author leon
 @since 2024-07-17
     */
    public static final CourseUserTableDef COURSE_USER = new CourseUserTableDef();

    public final QueryColumn ID = new QueryColumn(this, "id");

    public final QueryColumn USER_ID = new QueryColumn(this, "user_id");

    public final QueryColumn COURSE_ID = new QueryColumn(this, "course_id");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, USER_ID, COURSE_ID};

    public CourseUserTableDef() {
        super("", "course_user");
    }

    private CourseUserTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public CourseUserTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new CourseUserTableDef("", "course_user", alias));
    }

}
