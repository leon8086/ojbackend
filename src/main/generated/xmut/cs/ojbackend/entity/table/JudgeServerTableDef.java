package xmut.cs.ojbackend.entity.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

// Auto generate by mybatis-flex, do not modify it.
public class JudgeServerTableDef extends TableDef {

    /**
     * 实体类。

 @author leon
 @since 2024-06-03
     */
    public static final JudgeServerTableDef JUDGE_SERVER = new JudgeServerTableDef();

    public final QueryColumn ID = new QueryColumn(this, "id");

    public final QueryColumn IP = new QueryColumn(this, "ip");

    public final QueryColumn CPU_CORE = new QueryColumn(this, "cpu_core");

    public final QueryColumn CPU_USAGE = new QueryColumn(this, "cpu_usage");

    public final QueryColumn HOSTNAME = new QueryColumn(this, "hostname");

    public final QueryColumn CREATE_TIME = new QueryColumn(this, "create_time");

    public final QueryColumn IS_DISABLED = new QueryColumn(this, "is_disabled");

    public final QueryColumn SERVICE_URL = new QueryColumn(this, "service_url");

    public final QueryColumn TASK_NUMBER = new QueryColumn(this, "task_number");

    public final QueryColumn MEMORY_USAGE = new QueryColumn(this, "memory_usage");

    public final QueryColumn JUDGER_VERSION = new QueryColumn(this, "judger_version");

    public final QueryColumn LAST_HEARTBEAT = new QueryColumn(this, "last_heartbeat");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, IP, CPU_CORE, CPU_USAGE, HOSTNAME, CREATE_TIME, IS_DISABLED, SERVICE_URL, TASK_NUMBER, MEMORY_USAGE, JUDGER_VERSION, LAST_HEARTBEAT};

    public JudgeServerTableDef() {
        super("", "judge_server");
    }

    private JudgeServerTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public JudgeServerTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new JudgeServerTableDef("", "judge_server", alias));
    }

}
