package cn.stylefeng.guns.base.db.dao.sqls;

import lombok.Getter;

/**
 * 获取某个表的所有字段的sql
 *
 * @author jinbo
 * @date 2019-07-16-13:06
 */
@Getter
public class TableFieldListSql extends AbstractSql {

    @Override
    protected String mysql() {
        return "select COLUMN_NAME as columnName,COLUMN_COMMENT as columnComment from information_schema.COLUMNS where table_name = ? and table_schema = ?";
    }

    @Override
    protected String sqlServer() {
        return "";
    }

    @Override
    protected String pgSql() {
        return "";
    }

    @Override
    protected String oracle() {
        return "";
    }
}
