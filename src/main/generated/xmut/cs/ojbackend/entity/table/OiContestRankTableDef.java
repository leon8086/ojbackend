package xmut.cs.ojbackend.entity.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

// Auto generate by mybatis-flex, do not modify it.
public class OiContestRankTableDef extends TableDef {

    /**
     * 实体类。

 @author leon
 @since 2024-06-03
     */
    public static final OiContestRankTableDef OI_CONTEST_RANK = new OiContestRankTableDef();

    public final QueryColumn ID = new QueryColumn(this, "id");

    public final QueryColumn USER_ID = new QueryColumn(this, "user_id");

    public final QueryColumn CONTEST_ID = new QueryColumn(this, "contest_id");

    public final QueryColumn TOTAL_SCORE = new QueryColumn(this, "total_score");

    public final QueryColumn SUBMISSION_INFO = new QueryColumn(this, "submission_info");

    public final QueryColumn SUBMISSION_NUMBER = new QueryColumn(this, "submission_number");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, USER_ID, CONTEST_ID, TOTAL_SCORE, SUBMISSION_INFO, SUBMISSION_NUMBER};

    public OiContestRankTableDef() {
        super("", "oi_contest_rank");
    }

    private OiContestRankTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public OiContestRankTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new OiContestRankTableDef("", "oi_contest_rank", alias));
    }

}
