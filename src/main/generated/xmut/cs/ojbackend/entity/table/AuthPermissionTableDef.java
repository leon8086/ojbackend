package xmut.cs.ojbackend.entity.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

// Auto generate by mybatis-flex, do not modify it.
public class AuthPermissionTableDef extends TableDef {

    /**
     * 实体类。

 @author leon
 @since 2024-06-03
     */
    public static final AuthPermissionTableDef AUTH_PERMISSION = new AuthPermissionTableDef();

    public final QueryColumn ID = new QueryColumn(this, "id");

    public final QueryColumn NAME = new QueryColumn(this, "name");

    public final QueryColumn CODENAME = new QueryColumn(this, "codename");

    public final QueryColumn CONTENT_TYPE_ID = new QueryColumn(this, "content_type_id");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, NAME, CODENAME, CONTENT_TYPE_ID};

    public AuthPermissionTableDef() {
        super("", "auth_permission");
    }

    private AuthPermissionTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public AuthPermissionTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new AuthPermissionTableDef("", "auth_permission", alias));
    }

}
