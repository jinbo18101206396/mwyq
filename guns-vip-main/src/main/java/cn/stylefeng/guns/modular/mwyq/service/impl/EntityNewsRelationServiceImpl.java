package cn.stylefeng.guns.modular.mwyq.service.impl;

import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.mwyq.entity.EntityNewsRelation;
import cn.stylefeng.guns.modular.mwyq.mapper.EntityNewsRelationMapper;
import cn.stylefeng.guns.modular.mwyq.model.params.EntityNewsRelationParam;
import cn.stylefeng.guns.modular.mwyq.model.result.EntityNewsRelationResult;
import  cn.stylefeng.guns.modular.mwyq.service.EntityNewsRelationService;
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
 * @since 2020-06-14
 */
@Service
public class EntityNewsRelationServiceImpl extends ServiceImpl<EntityNewsRelationMapper, EntityNewsRelation> implements EntityNewsRelationService {

    @Override
    public void add(EntityNewsRelationParam param){
        EntityNewsRelation entity = getEntity(param);
        this.save(entity);
    }

    @Override
    public void delete(EntityNewsRelationParam param){
        this.removeById(getKey(param));
    }

    @Override
    public void update(EntityNewsRelationParam param){
        EntityNewsRelation oldEntity = getOldEntity(param);
        EntityNewsRelation newEntity = getEntity(param);
        ToolUtil.copyProperties(newEntity, oldEntity);
        this.updateById(newEntity);
    }

    @Override
    public EntityNewsRelationResult findBySpec(EntityNewsRelationParam param){
        return null;
    }

    @Override
    public List<EntityNewsRelationResult> findListBySpec(EntityNewsRelationParam param){
        return this.baseMapper.customList(param);
    }

    @Override
    public LayuiPageInfo findPageBySpec(EntityNewsRelationParam param){
        Page pageContext = getPageContext();
        IPage page = this.baseMapper.customPageList(pageContext, param);
        return LayuiPageFactory.createPageInfo(page);
    }

    private Serializable getKey(EntityNewsRelationParam param){
        return param.getId();
    }

    private Page getPageContext() {
        return LayuiPageFactory.defaultPage();
    }

    private EntityNewsRelation getOldEntity(EntityNewsRelationParam param) {
        return this.getById(getKey(param));
    }

    private EntityNewsRelation getEntity(EntityNewsRelationParam param) {
        EntityNewsRelation entity = new EntityNewsRelation();
        ToolUtil.copyProperties(param, entity);
        return entity;
    }

}
