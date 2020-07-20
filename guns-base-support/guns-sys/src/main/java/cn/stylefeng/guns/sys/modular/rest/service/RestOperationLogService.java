package cn.stylefeng.guns.sys.modular.rest.service;

import cn.stylefeng.guns.sys.modular.rest.entity.RestOperationLog;
import cn.stylefeng.guns.sys.modular.rest.mapper.RestOperationLogMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 操作日志 服务实现类
 * </p>
 *
 * @author jinbo
 * @since 2018-12-07
 */
@Service
public class RestOperationLogService extends ServiceImpl<RestOperationLogMapper, RestOperationLog> {

    /**
     * 获取操作日志列表
     *
     * @author jinbo
     * @Date 2018/12/23 5:41 PM
     */
    public List<Map<String, Object>> getOperationLogs(Page page, String beginTime, String endTime, String logName, String s) {
        return this.baseMapper.getOperationLogs(page, beginTime, endTime, logName, s);
    }

}
