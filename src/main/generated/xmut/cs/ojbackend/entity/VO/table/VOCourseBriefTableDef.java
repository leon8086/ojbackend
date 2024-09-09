package xmut.cs.ojbackend.entity.VO.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

// Auto generate by mybatis-flex, do not modify it.
public class VOCourseBriefTableDef extends TableDef {

    /**
     * 实体类。

 @author leon
 @since 2024-06-03
     */
    public static final VOCourseBriefTableDef V_O_COURSE_BRIEF = new VOCourseBriefTableDef();

    public final QueryColumn ID = new QueryColumn(this, "id");

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
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, OWNER_ID, IS_CLOSED, COURSE_NAME};

    public VOCourseBriefTableDef() {
        super("", "course");
    }

    private VOCourseBriefTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public VOCourseBriefTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new VOCourseBriefTableDef("", "course", alias));
    }

}
