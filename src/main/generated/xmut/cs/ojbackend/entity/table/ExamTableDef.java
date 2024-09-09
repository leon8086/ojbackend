package xmut.cs.ojbackend.entity.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

// Auto generate by mybatis-flex, do not modify it.
public class ExamTableDef extends TableDef {

    /**
     * 实体类。

 @author leon
 @since 2024-06-26
     */
    public static final ExamTableDef EXAM = new ExamTableDef();

    public final QueryColumn ID = new QueryColumn(this, "id");

    public final QueryColumn TITLE = new QueryColumn(this, "title");

    public final QueryColumn END_TIME = new QueryColumn(this, "end_time");

    public final QueryColumn IS_ENDED = new QueryColumn(this, "is_ended");

    public final QueryColumn VISIBLE = new QueryColumn(this, "visible");

    public final QueryColumn COURSE_ID = new QueryColumn(this, "course_id");

    public final QueryColumn START_TIME = new QueryColumn(this, "start_time");

    public final QueryColumn CREATE_TIME = new QueryColumn(this, "create_time");

    public final QueryColumn DESCRIPTION = new QueryColumn(this, "description");

    public final QueryColumn PROBLEM_COUNT = new QueryColumn(this, "problem_count");

    public final QueryColumn PROBLEM_CONFIG = new QueryColumn(this, "problem_config");

    public final QueryColumn LAST_UPDATE_TIME = new QueryColumn(this, "last_update_time");

    public final QueryColumn ALLOWED_IP_RANGES = new QueryColumn(this, "allowed_ip_ranges");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, TITLE, END_TIME, IS_ENDED, VISIBLE, COURSE_ID, START_TIME, CREATE_TIME, DESCRIPTION, PROBLEM_COUNT, PROBLEM_CONFIG, LAST_UPDATE_TIME, ALLOWED_IP_RANGES};

    public ExamTableDef() {
        super("", "exam");
    }

    private ExamTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public ExamTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new ExamTableDef("", "exam", alias));
    }

}
