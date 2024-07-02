package xmut.cs.ojbackend.entity.VO.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

// Auto generate by mybatis-flex, do not modify it.
public class VOExamDetailTableDef extends TableDef {

    /**
     * 实体类。

 @author leon
 @since 2024-06-26
     */
    public static final VOExamDetailTableDef V_O_EXAM_DETAIL = new VOExamDetailTableDef();

    public final QueryColumn ID = new QueryColumn(this, "id");

    public final QueryColumn TITLE = new QueryColumn(this, "title");

    public final QueryColumn END_TIME = new QueryColumn(this, "end_time");

    public final QueryColumn START_TIME = new QueryColumn(this, "start_time");

    public final QueryColumn CREATED_BY_ID = new QueryColumn(this, "created_by_id");

    public final QueryColumn DESCRIPTION = new QueryColumn(this, "description");

    public final QueryColumn PROBLEM_CONFIG = new QueryColumn(this, "problem_config");

    public final QueryColumn ALLOWED_IP_RANGES = new QueryColumn(this, "allowed_ip_ranges");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, TITLE, END_TIME, START_TIME, CREATED_BY_ID, DESCRIPTION, PROBLEM_CONFIG, ALLOWED_IP_RANGES};

    public VOExamDetailTableDef() {
        super("", "exam");
    }

    private VOExamDetailTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public VOExamDetailTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new VOExamDetailTableDef("", "exam", alias));
    }

}
