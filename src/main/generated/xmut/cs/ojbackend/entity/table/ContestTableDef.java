package xmut.cs.ojbackend.entity.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

// Auto generate by mybatis-flex, do not modify it.
public class ContestTableDef extends TableDef {

    /**
     * 实体类。

 @author leon
 @since 2024-06-03
     */
    public static final ContestTableDef CONTEST = new ContestTableDef();

    public final QueryColumn ID = new QueryColumn(this, "id");

    public final QueryColumn TITLE = new QueryColumn(this, "title");

    public final QueryColumn END_TIME = new QueryColumn(this, "end_time");

    public final QueryColumn VISIBLE = new QueryColumn(this, "visible");

    public final QueryColumn PASSWORD = new QueryColumn(this, "password");

    public final QueryColumn RULE_TYPE = new QueryColumn(this, "rule_type");

    public final QueryColumn START_TIME = new QueryColumn(this, "start_time");

    public final QueryColumn CREATE_TIME = new QueryColumn(this, "create_time");

    public final QueryColumn CREATED_BY_ID = new QueryColumn(this, "created_by_id");

    public final QueryColumn DESCRIPTION = new QueryColumn(this, "description");

    public final QueryColumn REAL_TIME_RANK = new QueryColumn(this, "real_time_rank");

    public final QueryColumn LAST_UPDATE_TIME = new QueryColumn(this, "last_update_time");

    public final QueryColumn ALLOWED_IP_RANGES = new QueryColumn(this, "allowed_ip_ranges");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, TITLE, END_TIME, VISIBLE, PASSWORD, RULE_TYPE, START_TIME, CREATE_TIME, CREATED_BY_ID, DESCRIPTION, REAL_TIME_RANK, LAST_UPDATE_TIME, ALLOWED_IP_RANGES};

    public ContestTableDef() {
        super("", "contest");
    }

    private ContestTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public ContestTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new ContestTableDef("", "contest", alias));
    }

}
