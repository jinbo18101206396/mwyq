package cn.stylefeng.guns.sys.modular.rest.mapper;

import cn.stylefeng.guns.sys.modular.rest.entity.RestNotice;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * <p>
 * 通知表 Mapper 接口
 * </p>
 *
 * @author jinbo
 * @since 2018-12-07
 */
public interface RestNoticeMapper extends BaseMapper<RestNotice> {

    /**
     * 获取通知列表
     */
    Page<Map<String, Object>> list(@Param("page") Page page, @Param("condition") String condition);

}
