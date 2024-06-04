package xmut.cs.ojbackend.entity.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

// Auto generate by mybatis-flex, do not modify it.
public class UserProfileTableDef extends TableDef {

    /**
     * 实体类。

 @author leon
 @since 2024-06-03
     */
    public static final UserProfileTableDef USER_PROFILE = new UserProfileTableDef();

    public final QueryColumn ID = new QueryColumn(this, "id");

    public final QueryColumn BLOG = new QueryColumn(this, "blog");

    public final QueryColumn MOOD = new QueryColumn(this, "mood");

    public final QueryColumn MAJOR = new QueryColumn(this, "major");

    public final QueryColumn AVATAR = new QueryColumn(this, "avatar");

    public final QueryColumn GITHUB = new QueryColumn(this, "github");

    public final QueryColumn SCHOOL = new QueryColumn(this, "school");

    public final QueryColumn USER_ID = new QueryColumn(this, "user_id");

    public final QueryColumn LANGUAGE = new QueryColumn(this, "language");

    public final QueryColumn REAL_NAME = new QueryColumn(this, "real_name");

    public final QueryColumn TOTAL_SCORE = new QueryColumn(this, "total_score");

    public final QueryColumn ACCEPTED_NUMBER = new QueryColumn(this, "accepted_number");

    public final QueryColumn OI_PROBLEMS_STATUS = new QueryColumn(this, "oi_problems_status");

    public final QueryColumn SUBMISSION_NUMBER = new QueryColumn(this, "submission_number");

    public final QueryColumn ACM_PROBLEMS_STATUS = new QueryColumn(this, "acm_problems_status");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, BLOG, MOOD, MAJOR, AVATAR, GITHUB, SCHOOL, USER_ID, LANGUAGE, REAL_NAME, TOTAL_SCORE, ACCEPTED_NUMBER, OI_PROBLEMS_STATUS, SUBMISSION_NUMBER, ACM_PROBLEMS_STATUS};

    public UserProfileTableDef() {
        super("", "user_profile");
    }

    private UserProfileTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public UserProfileTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new UserProfileTableDef("", "user_profile", alias));
    }

}
