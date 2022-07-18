package cn.stylefeng.guns.modular.mwyq.service;

import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.mwyq.entity.CustomWord;
import cn.stylefeng.guns.modular.mwyq.model.params.CustomWordParam;
import cn.stylefeng.guns.modular.mwyq.model.result.CustomWordResult;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 定制词表 服务类
 * </p>
 *
 * @author jinbo
 * @since 2020-06-11
 */
public interface CustomWordService extends IService<CustomWord> {

    /**
     * 新增
     *
     * @author jinbo
     * @Date 2020-06-11
     */
    void add(CustomWordParam param);

    /**
     * 删除
     *
     * @author jinbo
     * @Date 2020-06-11
     */
    void delete(CustomWordParam param);

    /**
     * 更新
     *
     * @author jinbo
     * @Date 2020-06-11
     */
    void update(CustomWordParam param);

    /**
     * 查询单条数据，Specification模式
     *
     * @author jinbo
     * @Date 2020-06-11
     */
    CustomWordResult findBySpec(CustomWordParam param);

    /**
     * 查询列表，Specification模式
     *
     * @author jinbo
     * @Date 2020-06-11
     */
    List<CustomWordResult> findListBySpec(CustomWordParam param);

    /**
     * 查询分页数据，Specification模式
     *
     * @author jinbo
     * @Date 2020-06-11
     */
     LayuiPageInfo findPageBySpec(CustomWordParam param);


    /**
     * TODO 将中文主题词翻译成少数民族语言
     *
     * @author jinbo
     * @Date 2020-06-11
     */
    List<String> translateCnCustomWordName(String customWordName, String lang);

    List<String> translateCnCustomwordNames(List<String> names, String lang);
}
