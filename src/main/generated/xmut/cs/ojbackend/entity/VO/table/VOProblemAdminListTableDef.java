package xmut.cs.ojbackend.entity.VO.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

// Auto generate by mybatis-flex, do not modify it.
public class VOProblemAdminListTableDef extends TableDef {

    public static final VOProblemAdminListTableDef V_O_PROBLEM_ADMIN_LIST = new VOProblemAdminListTableDef();

    public final QueryColumn ID = new QueryColumn(this, "id");

    public final QueryColumn TITLE = new QueryColumn(this, "title");

    public final QueryColumn SUB_TAG = new QueryColumn(this, "sub_tag");

    public final QueryColumn VISIBLE = new QueryColumn(this, "visible");

    public final QueryColumn MAJOR_TAG = new QueryColumn(this, "major_tag");

    public final QueryColumn SUB_TAG_ID = new QueryColumn(this, "sub_tag_id");

    public final QueryColumn TEMPLATE = new QueryColumn(this, "template");

    public final QueryColumn DISPLAY_ID = new QueryColumn(this, "display_id");

    public final QueryColumn TIME_LIMIT = new QueryColumn(this, "time_limit");

    public final QueryColumn CREATE_TIME = new QueryColumn(this, "create_time");

    public final QueryColumn DIFFICULTY = new QueryColumn(this, "difficulty");

    public final QueryColumn MAJOR_TAG_ID = new QueryColumn(this, "major_tag_id");

    public final QueryColumn TOTAL_SCORE = new QueryColumn(this, "total_score");

    public final QueryColumn CREATED_BY_ID = new QueryColumn(this, "created_by_id");

    public final QueryColumn DESCRIPTION = new QueryColumn(this, "description");

    public final QueryColumn MEMORY_LIMIT = new QueryColumn(this, "memory_limit");

    public final QueryColumn ACCEPTED_NUMBER = new QueryColumn(this, "accepted_number");

    public final QueryColumn SUBMISSION_NUMBER = new QueryColumn(this, "submission_number");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, TITLE, SUB_TAG, VISIBLE, MAJOR_TAG, SUB_TAG_ID, TEMPLATE, DISPLAY_ID, TIME_LIMIT, CREATE_TIME, DIFFICULTY, MAJOR_TAG_ID, TOTAL_SCORE, CREATED_BY_ID, DESCRIPTION, MEMORY_LIMIT, ACCEPTED_NUMBER, SUBMISSION_NUMBER};

    public VOProblemAdminListTableDef() {
        super("", "problem");
    }

    private VOProblemAdminListTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public VOProblemAdminListTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new VOProblemAdminListTableDef("", "problem", alias));
    }

}
