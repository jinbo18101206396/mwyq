package cn.stylefeng.guns.modular.mwyq.service.impl;

import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;

import cn.stylefeng.guns.modular.mwyq.entity.EntityTopicRelation;
import cn.stylefeng.guns.modular.mwyq.mapper.EntityTopicRelationMapper;
import cn.stylefeng.guns.modular.mwyq.model.params.EntityTopicRelationParam;
import cn.stylefeng.guns.modular.mwyq.model.result.EntityTopicRelationResult;
import cn.stylefeng.guns.modular.mwyq.service.EntityTopicRelationService;
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
 * @since 2020-07-05
 */
@Service
public class EntityTopicRelationServiceImpl extends ServiceImpl<EntityTopicRelationMapper, EntityTopicRelation> implements EntityTopicRelationService {

    @Override
    public void add(EntityTopicRelationParam param){
        EntityTopicRelation entity = getEntity(param);
        this.save(entity);
    }

    @Override
    public void delete(EntityTopicRelationParam param){
        this.removeById(getKey(param));
    }

    @Override
    public void update(EntityTopicRelationParam param){
        EntityTopicRelation oldEntity = getOldEntity(param);
        EntityTopicRelation newEntity = getEntity(param);
        ToolUtil.copyProperties(newEntity, oldEntity);
        this.updateById(newEntity);
    }

    @Override
    public EntityTopicRelationResult findBySpec(EntityTopicRelationParam param){
        return null;
    }

    @Override
    public List<EntityTopicRelationResult> findListBySpec(EntityTopicRelationParam param){
        return null;
    }

    @Override
    public LayuiPageInfo findPageBySpec(EntityTopicRelationParam param){
        Page pageContext = getPageContext();
        IPage page = this.baseMapper.customPageList(pageContext, param);
        return LayuiPageFactory.createPageInfo(page);
    }

    private Serializable getKey(EntityTopicRelationParam param){
        return param.getId();
    }

    private Page getPageContext() {
        return LayuiPageFactory.defaultPage();
    }

    private EntityTopicRelation getOldEntity(EntityTopicRelationParam param) {
        return this.getById(getKey(param));
    }

    private EntityTopicRelation getEntity(EntityTopicRelationParam param) {
        EntityTopicRelation entity = new EntityTopicRelation();
        ToolUtil.copyProperties(param, entity);
        return entity;
    }

}
