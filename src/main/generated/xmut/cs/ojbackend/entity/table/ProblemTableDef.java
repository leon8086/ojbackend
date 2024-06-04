package xmut.cs.ojbackend.entity.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

// Auto generate by mybatis-flex, do not modify it.
public class ProblemTableDef extends TableDef {

    /**
     * 实体类。

 @author leon
 @since 2024-06-03
     */
    public static final ProblemTableDef PROBLEM = new ProblemTableDef();

    public final QueryColumn ID = new QueryColumn(this, "id");

    public final QueryColumn SPJ = new QueryColumn(this, "spj");

    public final QueryColumn HINT = new QueryColumn(this, "hint");

    public final QueryColumn TITLE = new QueryColumn(this, "title");

    public final QueryColumn IO_MODE = new QueryColumn(this, "io_mode");

    public final QueryColumn SOURCE = new QueryColumn(this, "source");

    public final QueryColumn SAMPLES = new QueryColumn(this, "samples");

    public final QueryColumn SPJ_CODE = new QueryColumn(this, "spj_code");

    public final QueryColumn VISIBLE = new QueryColumn(this, "visible");

    public final QueryColumn IS_PUBLIC = new QueryColumn(this, "is_public");

    public final QueryColumn RULE_TYPE = new QueryColumn(this, "rule_type");

    public final QueryColumn TEMPLATE = new QueryColumn(this, "template");

    public final QueryColumn CONTEST_ID = new QueryColumn(this, "contest_id");

    public final QueryColumn DISPLAY_ID = new QueryColumn(this, "_id");

    public final QueryColumn LANGUAGES = new QueryColumn(this, "languages");

    public final QueryColumn TIME_LIMIT = new QueryColumn(this, "time_limit");

    public final QueryColumn CREATE_TIME = new QueryColumn(this, "create_time");

    public final QueryColumn DIFFICULTY = new QueryColumn(this, "difficulty");

    public final QueryColumn SPJ_VERSION = new QueryColumn(this, "spj_version");

    public final QueryColumn TEST_CASE_ID = new QueryColumn(this, "test_case_id");

    public final QueryColumn TOTAL_SCORE = new QueryColumn(this, "total_score");

    public final QueryColumn CREATED_BY_ID = new QueryColumn(this, "created_by_id");

    public final QueryColumn DESCRIPTION = new QueryColumn(this, "description");

    public final QueryColumn MEMORY_LIMIT = new QueryColumn(this, "memory_limit");

    public final QueryColumn SPJ_LANGUAGE = new QueryColumn(this, "spj_language");

    public final QueryColumn SPJ_COMPILE_OK = new QueryColumn(this, "spj_compile_ok");

    public final QueryColumn STATISTIC_INFO = new QueryColumn(this, "statistic_info");

    public final QueryColumn TEST_CASE_SCORE = new QueryColumn(this, "test_case_score");

    public final QueryColumn ACCEPTED_NUMBER = new QueryColumn(this, "accepted_number");

    public final QueryColumn LAST_UPDATE_TIME = new QueryColumn(this, "last_update_time");

    public final QueryColumn SHARE_SUBMISSION = new QueryColumn(this, "share_submission");

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
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, SPJ, HINT, TITLE, IO_MODE, SOURCE, SAMPLES, SPJ_CODE, VISIBLE, IS_PUBLIC, RULE_TYPE, TEMPLATE, CONTEST_ID, DISPLAY_ID, LANGUAGES, TIME_LIMIT, CREATE_TIME, DIFFICULTY, SPJ_VERSION, TEST_CASE_ID, TOTAL_SCORE, CREATED_BY_ID, DESCRIPTION, MEMORY_LIMIT, SPJ_LANGUAGE, SPJ_COMPILE_OK, STATISTIC_INFO, TEST_CASE_SCORE, ACCEPTED_NUMBER, LAST_UPDATE_TIME, SHARE_SUBMISSION, INPUT_DESCRIPTION, SUBMISSION_NUMBER, OUTPUT_DESCRIPTION};

    public ProblemTableDef() {
        super("", "problem");
    }

    private ProblemTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public ProblemTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new ProblemTableDef("", "problem", alias));
    }

}
