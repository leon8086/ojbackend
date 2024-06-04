package xmut.cs.ojbackend.entity.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

// Auto generate by mybatis-flex, do not modify it.
public class ContestAnnouncementTableDef extends TableDef {

    /**
     * 实体类。

 @author leon
 @since 2024-06-03
     */
    public static final ContestAnnouncementTableDef CONTEST_ANNOUNCEMENT = new ContestAnnouncementTableDef();

    public final QueryColumn ID = new QueryColumn(this, "id");

    public final QueryColumn TITLE = new QueryColumn(this, "title");

    public final QueryColumn CONTENT = new QueryColumn(this, "content");

    public final QueryColumn VISIBLE = new QueryColumn(this, "visible");

    public final QueryColumn CONTEST_ID = new QueryColumn(this, "contest_id");

    public final QueryColumn CREATE_TIME = new QueryColumn(this, "create_time");

    public final QueryColumn CREATED_BY_ID = new QueryColumn(this, "created_by_id");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, TITLE, CONTENT, VISIBLE, CONTEST_ID, CREATE_TIME, CREATED_BY_ID};

    public ContestAnnouncementTableDef() {
        super("", "contest_announcement");
    }

    private ContestAnnouncementTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public ContestAnnouncementTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new ContestAnnouncementTableDef("", "contest_announcement", alias));
    }

}
