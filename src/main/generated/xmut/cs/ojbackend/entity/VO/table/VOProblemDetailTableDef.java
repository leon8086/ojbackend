package xmut.cs.ojbackend.entity.VO.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

// Auto generate by mybatis-flex, do not modify it.
public class VOProblemDetailTableDef extends TableDef {

    /**
     * 实体类。

 @author leon
 @since 2024-06-03
     */
    public static final VOProblemDetailTableDef V_O_PROBLEM_DETAIL = new VOProblemDetailTableDef();

    public final QueryColumn ID = new QueryColumn(this, "id");

    public final QueryColumn HINT = new QueryColumn(this, "hint");

    public final QueryColumn TITLE = new QueryColumn(this, "title");

    public final QueryColumn SUB_TAG = new QueryColumn(this, "sub_tag");

    public final QueryColumn SAMPLES = new QueryColumn(this, "samples");

    public final QueryColumn VISIBLE = new QueryColumn(this, "visible");

    public final QueryColumn IS_PUBLIC = new QueryColumn(this, "is_public");

    public final QueryColumn MAJOR_TAG = new QueryColumn(this, "major_tag");

    public final QueryColumn SUB_TAG_ID = new QueryColumn(this, "sub_tag_id");

    public final QueryColumn TEMPLATE = new QueryColumn(this, "template");

    public final QueryColumn DISPLAY_ID = new QueryColumn(this, "display_id");

    public final QueryColumn LANGUAGES = new QueryColumn(this, "languages");

    public final QueryColumn TIME_LIMIT = new QueryColumn(this, "time_limit");

    public final QueryColumn DIFFICULTY = new QueryColumn(this, "difficulty");

    public final QueryColumn MAJOR_TAG_ID = new QueryColumn(this, "major_tag_id");

    public final QueryColumn TOTAL_SCORE = new QueryColumn(this, "total_score");

    public final QueryColumn DESCRIPTION = new QueryColumn(this, "description");

    public final QueryColumn MEMORY_LIMIT = new QueryColumn(this, "memory_limit");

    public final QueryColumn STATISTIC_INFO = new QueryColumn(this, "statistic_info");

    public final QueryColumn ACCEPTED_NUMBER = new QueryColumn(this, "accepted_number");

    public final QueryColumn INPUT_DESCRIPTION = new QueryColumn(this, "input_description");

    public final QueryColumn SUBMISSION_NUMBER = new QueryColumn(this, "submission_number");

    public final QueryColumn OUTPUT_DESCRIPTION = new QueryColumn(this, "output_description");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, HINT, TITLE, SUB_TAG, SAMPLES, VISIBLE, IS_PUBLIC, MAJOR_TAG, SUB_TAG_ID, TEMPLATE, DISPLAY_ID, LANGUAGES, TIME_LIMIT, DIFFICULTY, MAJOR_TAG_ID, TOTAL_SCORE, DESCRIPTION, MEMORY_LIMIT, STATISTIC_INFO, ACCEPTED_NUMBER, INPUT_DESCRIPTION, SUBMISSION_NUMBER, OUTPUT_DESCRIPTION};

    public VOProblemDetailTableDef() {
        super("", "problem");
    }

    private VOProblemDetailTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public VOProblemDetailTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new VOProblemDetailTableDef("", "problem", alias));
    }

}
