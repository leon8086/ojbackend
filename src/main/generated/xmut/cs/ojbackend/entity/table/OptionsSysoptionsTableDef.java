package xmut.cs.ojbackend.entity.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

// Auto generate by mybatis-flex, do not modify it.
public class OptionsSysoptionsTableDef extends TableDef {

    /**
     * 实体类。

 @author leon
 @since 2024-06-03
     */
    public static final OptionsSysoptionsTableDef OPTIONS_SYSOPTIONS = new OptionsSysoptionsTableDef();

    public final QueryColumn ID = new QueryColumn(this, "id");

    public final QueryColumn KEY = new QueryColumn(this, "key");

    public final QueryColumn VALUE = new QueryColumn(this, "value");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, KEY, VALUE};

    public OptionsSysoptionsTableDef() {
        super("", "options_sysoptions");
    }

    private OptionsSysoptionsTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public OptionsSysoptionsTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new OptionsSysoptionsTableDef("", "options_sysoptions", alias));
    }

}
