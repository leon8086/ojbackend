package xmut.cs.ojbackend.entity.VO.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

// Auto generate by mybatis-flex, do not modify it.
public class VOExamBriefTableDef extends TableDef {

    /**
     * 实体类。

 @author leon
 @since 2024-06-26
     */
    public static final VOExamBriefTableDef V_O_EXAM_BRIEF = new VOExamBriefTableDef();

    public final QueryColumn ID = new QueryColumn(this, "id");

    public final QueryColumn TITLE = new QueryColumn(this, "title");

    public final QueryColumn COURSE = new QueryColumn(this, "course");

    public final QueryColumn END_TIME = new QueryColumn(this, "end_time");

    public final QueryColumn IS_ENDED = new QueryColumn(this, "is_ended");

    public final QueryColumn COURSE_ID = new QueryColumn(this, "course_id");

    public final QueryColumn START_TIME = new QueryColumn(this, "start_time");

    public final QueryColumn DESCRIPTION = new QueryColumn(this, "description");

    public final QueryColumn PROBLEM_COUNT = new QueryColumn(this, "problem_count");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, TITLE, COURSE, END_TIME, IS_ENDED, COURSE_ID, START_TIME, DESCRIPTION, PROBLEM_COUNT};

    public VOExamBriefTableDef() {
        super("", "exam");
    }

    private VOExamBriefTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public VOExamBriefTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new VOExamBriefTableDef("", "exam", alias));
    }

}
