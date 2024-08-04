package xmut.cs.ojbackend.entity.VO.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

// Auto generate by mybatis-flex, do not modify it.
public class VOProblemAdminDetailTableDef extends TableDef {

    public static final VOProblemAdminDetailTableDef V_O_PROBLEM_ADMIN_DETAIL = new VOProblemAdminDetailTableDef();

    public final QueryColumn ID = new QueryColumn(this, "id");

    public final QueryColumn HINT = new QueryColumn(this, "hint");

    public final QueryColumn TITLE = new QueryColumn(this, "title");

    public final QueryColumn SAMPLES = new QueryColumn(this, "samples");

    public final QueryColumn VISIBLE = new QueryColumn(this, "visible");

    public final QueryColumn SUB_TAG_ID = new QueryColumn(this, "sub_tag_id");

    public final QueryColumn TEMPLATE = new QueryColumn(this, "template");

    public final QueryColumn DISPLAY_ID = new QueryColumn(this, "display_id");

    public final QueryColumn LANGUAGES = new QueryColumn(this, "languages");

    public final QueryColumn TIME_LIMIT = new QueryColumn(this, "time_limit");

    public final QueryColumn DIFFICULTY = new QueryColumn(this, "difficulty");

    public final QueryColumn MAJOR_TAG_ID = new QueryColumn(this, "major_tag_id");

    public final QueryColumn TEST_CASE_ID = new QueryColumn(this, "test_case_id");

    public final QueryColumn TOTAL_SCORE = new QueryColumn(this, "total_score");

    public final QueryColumn CREATED_BY_ID = new QueryColumn(this, "created_by_id");

    public final QueryColumn DESCRIPTION = new QueryColumn(this, "description");

    public final QueryColumn MEMORY_LIMIT = new QueryColumn(this, "memory_limit");

    public final QueryColumn TEST_CASE_SCORE = new QueryColumn(this, "test_case_score");

    public final QueryColumn INPUT_DESCRIPTION = new QueryColumn(this, "input_description");

    public final QueryColumn OUTPUT_DESCRIPTION = new QueryColumn(this, "output_description");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, HINT, TITLE, SAMPLES, VISIBLE, SUB_TAG_ID, TEMPLATE, DISPLAY_ID, LANGUAGES, TIME_LIMIT, DIFFICULTY, MAJOR_TAG_ID, TEST_CASE_ID, TOTAL_SCORE, CREATED_BY_ID, DESCRIPTION, MEMORY_LIMIT, TEST_CASE_SCORE, INPUT_DESCRIPTION, OUTPUT_DESCRIPTION};

    public VOProblemAdminDetailTableDef() {
        super("", "problem");
    }

    private VOProblemAdminDetailTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public VOProblemAdminDetailTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new VOProblemAdminDetailTableDef("", "problem", alias));
    }

}
