package cn.stylefeng.guns.sys.modular.gen.service;

import cn.stylefeng.guns.base.db.entity.DatabaseInfo;
import cn.stylefeng.guns.base.db.util.DbUtil;
import cn.stylefeng.guns.sys.modular.db.mapper.DatabaseInfoMapper;
import cn.stylefeng.roses.core.util.HttpContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

import static cn.stylefeng.guns.sys.modular.gen.controller.GeneratorController.CONDITION_FIELDS;


/**
 * 数据库表服务
 *
 * @author fengshuonan
 * @date 2019-05-06-19:04
 */
@Service
public class TableService {

    @Autowired
    private DatabaseInfoMapper databaseInfoMapper;

    public List<Map<String, Object>> getTableFields(Long dbId, String tableName) {

        //查找数据库元数据信息
        DatabaseInfo databaseInfo = databaseInfoMapper.selectById(dbId);

        //获取对应表的所有字段
        List<Map<String, Object>> tableFields = DbUtil.getTableFields(databaseInfo, tableName);

        //查询session中有无已经选中的数据，若有以选中的字段，则增加LAY_CHECKED标识
        HttpSession session = HttpContext.getRequest().getSession();
        Map<String, String[]> fieldMap = (Map<String, String[]>) session.getAttribute(CONDITION_FIELDS);
        if (fieldMap != null) {
            String[] strings = fieldMap.get(tableName);
            if (strings != null) {
                for (String fieldName : strings) {
                    for (Map<String, Object> tableField : tableFields) {
                        if (fieldName.equals(tableField.get("columnName"))) {
                            tableField.put("LAY_CHECKED", true);
                        }
                    }
                }
            }
        }

        return tableFields;
    }

}
