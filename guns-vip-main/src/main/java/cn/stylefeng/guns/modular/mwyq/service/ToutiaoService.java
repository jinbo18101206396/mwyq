package cn.stylefeng.guns.modular.mwyq.service;

import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.mwyq.entity.Toutiao;
import cn.stylefeng.guns.modular.mwyq.model.params.ToutiaoParam;
import cn.stylefeng.guns.modular.mwyq.model.result.ToutiaoResult;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 金波
 * @since 2023-05-18
 */
public interface ToutiaoService extends IService<Toutiao> {

    /**
     * 新增
     *
     * @author 金波
     * @Date 2023-05-18
     */
    void add(ToutiaoParam param);

    /**
     * 删除
     *
     * @author 金波
     * @Date 2023-05-18
     */
    void delete(ToutiaoParam param);

    /**
     * 更新
     *
     * @author 金波
     * @Date 2023-05-18
     */
    void update(ToutiaoParam param);

    /**
     * 查询单条数据，Specification模式
     *
     * @author 金波
     * @Date 2023-05-18
     */
    ToutiaoResult findBySpec(ToutiaoParam param);

    /**
     * 查询列表，Specification模式
     *
     * @author 金波
     * @Date 2023-05-18
     */
    List<ToutiaoResult> findListBySpec(ToutiaoParam param);

    /**
     * 查询分页数据，Specification模式
     *
     * @author 金波
     * @Date 2023-05-18
     */
     LayuiPageInfo findPageBySpec(ToutiaoParam param);

}
