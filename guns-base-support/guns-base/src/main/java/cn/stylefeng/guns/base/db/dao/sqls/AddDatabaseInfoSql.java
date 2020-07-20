package cn.stylefeng.guns.base.db.dao.sqls;

import lombok.Getter;

/**
 * 添加数据源sql
 *
 * @author jinbo
 * @date 2019-07-16-13:06
 */
@Getter
public class AddDatabaseInfoSql extends AbstractSql {

    @Override
    protected String mysql() {
        return "INSERT INTO `database_info`(`db_id`, `db_name`, `jdbc_driver`, `user_name`, `password`, `jdbc_url`, `remarks`, `create_time`) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
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
