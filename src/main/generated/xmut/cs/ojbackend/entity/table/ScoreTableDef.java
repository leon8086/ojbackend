package xmut.cs.ojbackend.entity.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

// Auto generate by mybatis-flex, do not modify it.
public class ScoreTableDef extends TableDef {

    /**
     * 实体类。

 @author leon
 @since 2024-08-13
     */
    public static final ScoreTableDef SCORE = new ScoreTableDef();

    public final QueryColumn ID = new QueryColumn(this, "id");

    public final QueryColumn SCORE_ = new QueryColumn(this, "score");

    public final QueryColumn USER_ID = new QueryColumn(this, "user_id");

    public final QueryColumn MAJOR_TAG = new QueryColumn(this, "major_tag");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, SCORE_, USER_ID, MAJOR_TAG};

    public ScoreTableDef() {
        super("", "score");
    }

    private ScoreTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public ScoreTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new ScoreTableDef("", "score", alias));
    }

}
