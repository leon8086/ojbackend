package xmut.cs.ojbackend.entity.VO.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

// Auto generate by mybatis-flex, do not modify it.
public class VOExamProfileRankTableDef extends TableDef {

    /**
     * 实体类。

 @author leon
 @since 2024-06-26
     */
    public static final VOExamProfileRankTableDef V_O_EXAM_PROFILE_RANK = new VOExamProfileRankTableDef();

    public final QueryColumn ID = new QueryColumn(this, "id");

    public final QueryColumn INFO = new QueryColumn(this, "info");

    public final QueryColumn SCORE = new QueryColumn(this, "score");

    public final QueryColumn EXAM_ID = new QueryColumn(this, "exam_id");

    public final QueryColumn USER_ID = new QueryColumn(this, "user_id");

    public final QueryColumn IS_ENDED = new QueryColumn(this, "is_ended");

    public final QueryColumn LAST_UPDATE = new QueryColumn(this, "last_update");

    public final QueryColumn PROBLEM_CONFIG = new QueryColumn(this, "problem_config");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, INFO, SCORE, EXAM_ID, USER_ID, IS_ENDED, LAST_UPDATE, PROBLEM_CONFIG};

    public VOExamProfileRankTableDef() {
        super("", "exam_profile");
    }

    private VOExamProfileRankTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public VOExamProfileRankTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new VOExamProfileRankTableDef("", "exam_profile", alias));
    }

}
