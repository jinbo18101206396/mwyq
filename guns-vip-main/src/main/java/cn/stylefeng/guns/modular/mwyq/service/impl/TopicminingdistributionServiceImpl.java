package cn.stylefeng.guns.modular.mwyq.service.impl;

import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.mwyq.entity.Topicminingdistribution;
import cn.stylefeng.guns.modular.mwyq.mapper.TopicminingdistributionMapper;
import cn.stylefeng.guns.modular.mwyq.model.params.TopicminingdistributionParam;
import cn.stylefeng.guns.modular.mwyq.model.result.TopicminingdistributionResult;
import  cn.stylefeng.guns.modular.mwyq.service.TopicminingdistributionService;
import cn.stylefeng.roses.core.util.ToolUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jinbo
 * @since 2020-07-01
 */
@Service
public class TopicminingdistributionServiceImpl extends ServiceImpl<TopicminingdistributionMapper, Topicminingdistribution> implements TopicminingdistributionService {

    @Override
    public void add(TopicminingdistributionParam param){
        Topicminingdistribution entity = getEntity(param);
        this.save(entity);
    }

    @Override
    public void delete(TopicminingdistributionParam param){
        this.removeById(getKey(param));
    }

    @Override
    public void update(TopicminingdistributionParam param){
        Topicminingdistribution oldEntity = getOldEntity(param);
        Topicminingdistribution newEntity = getEntity(param);
        ToolUtil.copyProperties(newEntity, oldEntity);
        this.updateById(newEntity);
    }

    @Override
    public TopicminingdistributionResult findBySpec(TopicminingdistributionParam param){
        return null;
    }

    @Override
    public List<TopicminingdistributionResult> findListBySpec(TopicminingdistributionParam param){
        return null;
    }

    @Override
    public LayuiPageInfo findPageBySpec(TopicminingdistributionParam param){
        Page pageContext = getPageContext();
        IPage page = this.baseMapper.customPageList(pageContext, param);
        return LayuiPageFactory.createPageInfo(page);
    }

    private Serializable getKey(TopicminingdistributionParam param){
        return null;
    }

    private Page getPageContext() {
        return LayuiPageFactory.defaultPage();
    }

    private Topicminingdistribution getOldEntity(TopicminingdistributionParam param) {
        return this.getById(getKey(param));
    }

    private Topicminingdistribution getEntity(TopicminingdistributionParam param) {
        Topicminingdistribution entity = new Topicminingdistribution();
        ToolUtil.copyProperties(param, entity);
        return entity;
    }

}
