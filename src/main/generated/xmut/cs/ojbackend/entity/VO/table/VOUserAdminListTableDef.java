package xmut.cs.ojbackend.entity.VO.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

// Auto generate by mybatis-flex, do not modify it.
public class VOUserAdminListTableDef extends TableDef {

    /**
     * 实体类。

 @author leon
 @since 2024-07-15
     */
    public static final VOUserAdminListTableDef V_O_USER_ADMIN_LIST = new VOUserAdminListTableDef();

    public final QueryColumn ID = new QueryColumn(this, "id");

    public final QueryColumn EMAIL = new QueryColumn(this, "email");

    public final QueryColumn GRADE = new QueryColumn(this, "grade");

    public final QueryColumn AVATAR = new QueryColumn(this, "avatar");

    public final QueryColumn PASSWORD = new QueryColumn(this, "password");

    public final QueryColumn REAL_NAME = new QueryColumn(this, "real_name");

    public final QueryColumn USERNAME = new QueryColumn(this, "username");

    public final QueryColumn ADMIN_TYPE = new QueryColumn(this, "admin_type");

    public final QueryColumn GRADE_NAME = new QueryColumn(this, "grade_name");

    public final QueryColumn LAST_LOGIN = new QueryColumn(this, "last_login");

    public final QueryColumn CREATE_TIME = new QueryColumn(this, "create_time");

    public final QueryColumn FIRST_LOGIN = new QueryColumn(this, "first_login");

    public final QueryColumn IS_DISABLED = new QueryColumn(this, "is_disabled");

    public final QueryColumn LAST_ACCEPT = new QueryColumn(this, "last_accept");

    public final QueryColumn ACCEPTED_NUMBER = new QueryColumn(this, "accepted_number");

    public final QueryColumn PROBLEMS_STATUS = new QueryColumn(this, "problems_status");

    public final QueryColumn SUBMISSION_NUMBER = new QueryColumn(this, "submission_number");

    public final QueryColumn RESET_PASSWORD_TOKEN = new QueryColumn(this, "reset_password_token");

    public final QueryColumn RESET_PASSWORD_TOKEN_EXPIRE_TIME = new QueryColumn(this, "reset_password_token_expire_time");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, EMAIL, GRADE, AVATAR, PASSWORD, REAL_NAME, USERNAME, ADMIN_TYPE, GRADE_NAME, LAST_LOGIN, CREATE_TIME, FIRST_LOGIN, IS_DISABLED, LAST_ACCEPT, ACCEPTED_NUMBER, PROBLEMS_STATUS, SUBMISSION_NUMBER, RESET_PASSWORD_TOKEN, RESET_PASSWORD_TOKEN_EXPIRE_TIME};

    public VOUserAdminListTableDef() {
        super("", "user");
    }

    private VOUserAdminListTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public VOUserAdminListTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new VOUserAdminListTableDef("", "user", alias));
    }

}
