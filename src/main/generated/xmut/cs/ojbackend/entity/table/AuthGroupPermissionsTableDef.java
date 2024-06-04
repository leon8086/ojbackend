package xmut.cs.ojbackend.entity.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

// Auto generate by mybatis-flex, do not modify it.
public class AuthGroupPermissionsTableDef extends TableDef {

    /**
     * 实体类。

 @author leon
 @since 2024-06-03
     */
    public static final AuthGroupPermissionsTableDef AUTH_GROUP_PERMISSIONS = new AuthGroupPermissionsTableDef();

    public final QueryColumn ID = new QueryColumn(this, "id");

    public final QueryColumn GROUP_ID = new QueryColumn(this, "group_id");

    public final QueryColumn PERMISSION_ID = new QueryColumn(this, "permission_id");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, GROUP_ID, PERMISSION_ID};

    public AuthGroupPermissionsTableDef() {
        super("", "auth_group_permissions");
    }

    private AuthGroupPermissionsTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public AuthGroupPermissionsTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new AuthGroupPermissionsTableDef("", "auth_group_permissions", alias));
    }

}
