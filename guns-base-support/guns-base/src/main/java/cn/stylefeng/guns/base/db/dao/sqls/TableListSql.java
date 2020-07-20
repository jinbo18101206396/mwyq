package cn.stylefeng.guns.base.db.dao.sqls;

import lombok.Getter;

/**
 * 获取所有表的sql
 *
 * @author jinbo
 * @date 2019-07-16-13:06
 */
@Getter
public class TableListSql extends AbstractSql {

    @Override
    protected String mysql() {
        return "select TABLE_NAME as tableName,TABLE_COMMENT as tableComment from information_schema.`TABLES` where TABLE_SCHEMA = ?";
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
