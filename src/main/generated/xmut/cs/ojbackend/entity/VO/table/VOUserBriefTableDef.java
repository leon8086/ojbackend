package xmut.cs.ojbackend.entity.VO.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

// Auto generate by mybatis-flex, do not modify it.
public class VOUserBriefTableDef extends TableDef {

    /**
     * 实体类。

 @author leon
 @since 2024-06-03
     */
    public static final VOUserBriefTableDef V_O_USER_BRIEF = new VOUserBriefTableDef();

    public final QueryColumn ID = new QueryColumn(this, "id");

    public final QueryColumn USERNAME = new QueryColumn(this, "username");

    public final QueryColumn ADMIN_TYPE = new QueryColumn(this, "admin_type");

    public final QueryColumn IS_DISABLED = new QueryColumn(this, "is_disabled");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, USERNAME, ADMIN_TYPE, IS_DISABLED};

    public VOUserBriefTableDef() {
        super("", "user");
    }

    private VOUserBriefTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public VOUserBriefTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new VOUserBriefTableDef("", "user", alias));
    }

}
