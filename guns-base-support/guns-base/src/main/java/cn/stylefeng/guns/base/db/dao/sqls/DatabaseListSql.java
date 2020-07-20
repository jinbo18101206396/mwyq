package cn.stylefeng.guns.base.db.dao.sqls;

import lombok.Getter;

/**
 * 数据源列表sql
 *
 * @author jinbo
 * @date 2019-07-16-13:06
 */
@Getter
public class DatabaseListSql extends AbstractSql {

    @Override
    protected String mysql() {
        return "select db_name,jdbc_driver,jdbc_url,user_name,password from database_info";
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
