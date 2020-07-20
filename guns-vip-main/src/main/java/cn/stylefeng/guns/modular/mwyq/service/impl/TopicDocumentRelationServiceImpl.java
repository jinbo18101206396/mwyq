package cn.stylefeng.guns.modular.mwyq.service.impl;

import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.mwyq.entity.TopicDocumentRelation;
import cn.stylefeng.guns.modular.mwyq.mapper.TopicDocumentRelationMapper;
import cn.stylefeng.guns.modular.mwyq.model.params.TopicDocumentRelationParam;
import cn.stylefeng.guns.modular.mwyq.model.result.TopicDocumentRelationResult;
import  cn.stylefeng.guns.modular.mwyq.service.TopicDocumentRelationService;
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
public class TopicDocumentRelationServiceImpl extends ServiceImpl<TopicDocumentRelationMapper, TopicDocumentRelation> implements TopicDocumentRelationService {

    @Override
    public void add(TopicDocumentRelationParam param){
        TopicDocumentRelation entity = getEntity(param);
        this.save(entity);
    }

    @Override
    public void delete(TopicDocumentRelationParam param){
        this.removeById(getKey(param));
    }

    @Override
    public void update(TopicDocumentRelationParam param){
        TopicDocumentRelation oldEntity = getOldEntity(param);
        TopicDocumentRelation newEntity = getEntity(param);
        ToolUtil.copyProperties(newEntity, oldEntity);
        this.updateById(newEntity);
    }

    @Override
    public TopicDocumentRelationResult findBySpec(TopicDocumentRelationParam param){
        return null;
    }

    @Override
    public List<TopicDocumentRelationResult> findListBySpec(TopicDocumentRelationParam param){
        return null;
    }

    @Override
    public LayuiPageInfo findPageBySpec(TopicDocumentRelationParam param){
        Page pageContext = getPageContext();
        IPage page = this.baseMapper.customPageList(pageContext, param);
        return LayuiPageFactory.createPageInfo(page);
    }

    private Serializable getKey(TopicDocumentRelationParam param){
        return null;
    }

    private Page getPageContext() {
        return LayuiPageFactory.defaultPage();
    }

    private TopicDocumentRelation getOldEntity(TopicDocumentRelationParam param) {
        return this.getById(getKey(param));
    }

    private TopicDocumentRelation getEntity(TopicDocumentRelationParam param) {
        TopicDocumentRelation entity = new TopicDocumentRelation();
        ToolUtil.copyProperties(param, entity);
        return entity;
    }

}
