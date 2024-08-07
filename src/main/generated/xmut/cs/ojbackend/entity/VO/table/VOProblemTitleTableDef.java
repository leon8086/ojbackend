package xmut.cs.ojbackend.entity.VO.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

// Auto generate by mybatis-flex, do not modify it.
public class VOProblemTitleTableDef extends TableDef {

    public static final VOProblemTitleTableDef V_O_PROBLEM_TITLE = new VOProblemTitleTableDef();

    public final QueryColumn ID = new QueryColumn(this, "id");

    public final QueryColumn TITLE = new QueryColumn(this, "title");

    public final QueryColumn SUB_TAG = new QueryColumn(this, "sub_tag");

    public final QueryColumn MAJOR_TAG = new QueryColumn(this, "major_tag");

    public final QueryColumn SUB_TAG_ID = new QueryColumn(this, "sub_tag_id");

    public final QueryColumn DISPLAY_ID = new QueryColumn(this, "display_id");

    public final QueryColumn LANGUAGES = new QueryColumn(this, "languages");

    public final QueryColumn DIFFICULTY = new QueryColumn(this, "difficulty");

    public final QueryColumn MAJOR_TAG_ID = new QueryColumn(this, "major_tag_id");

    public final QueryColumn ACCEPTED_NUMBER = new QueryColumn(this, "accepted_number");

    public final QueryColumn SUBMISSION_NUMBER = new QueryColumn(this, "submission_number");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, TITLE, SUB_TAG, MAJOR_TAG, SUB_TAG_ID, DISPLAY_ID, LANGUAGES, DIFFICULTY, MAJOR_TAG_ID, ACCEPTED_NUMBER, SUBMISSION_NUMBER};

    public VOProblemTitleTableDef() {
        super("", "problem");
    }

    private VOProblemTitleTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public VOProblemTitleTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new VOProblemTitleTableDef("", "problem", alias));
    }

}
