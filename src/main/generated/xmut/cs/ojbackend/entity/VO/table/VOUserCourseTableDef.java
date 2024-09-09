package xmut.cs.ojbackend.entity.VO.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

// Auto generate by mybatis-flex, do not modify it.
public class VOUserCourseTableDef extends TableDef {

    /**
     * 实体类。

 @author leon
 @since 2024-06-03
     */
    public static final VOUserCourseTableDef V_O_USER_COURSE = new VOUserCourseTableDef();

    public final QueryColumn ID = new QueryColumn(this, "id");

    public final QueryColumn EMAIL = new QueryColumn(this, "email");

    public final QueryColumn GRADE = new QueryColumn(this, "grade");

    public final QueryColumn REAL_NAME = new QueryColumn(this, "real_name");

    public final QueryColumn USERNAME = new QueryColumn(this, "username");

    public final QueryColumn ADMIN_TYPE = new QueryColumn(this, "admin_type");

    public final QueryColumn GRADE_NAME = new QueryColumn(this, "grade_name");

    public final QueryColumn IS_DISABLED = new QueryColumn(this, "is_disabled");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, EMAIL, GRADE, REAL_NAME, USERNAME, ADMIN_TYPE, GRADE_NAME, IS_DISABLED};

    public VOUserCourseTableDef() {
        super("", "user");
    }

    private VOUserCourseTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public VOUserCourseTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new VOUserCourseTableDef("", "user", alias));
    }

}
