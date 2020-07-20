package cn.stylefeng.guns.modular.mwyq.service.impl;

import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.mwyq.entity.Entity;
import cn.stylefeng.guns.modular.mwyq.mapper.EntityMapper;
import cn.stylefeng.guns.modular.mwyq.model.params.EntityParam;
import cn.stylefeng.guns.modular.mwyq.model.result.EntityResult;
import  cn.stylefeng.guns.modular.mwyq.service.EntityService;
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
public class EntityServiceImpl extends ServiceImpl<EntityMapper, Entity> implements EntityService {

    @Override
    public void add(EntityParam param){
        Entity entity = getEntity(param);
        this.save(entity);
    }

    @Override
    public void delete(EntityParam param){
        this.removeById(getKey(param));
    }

    @Override
    public void update(EntityParam param){
        Entity oldEntity = getOldEntity(param);
        Entity newEntity = getEntity(param);
        ToolUtil.copyProperties(newEntity, oldEntity);
        this.updateById(newEntity);
    }

    @Override
    public EntityResult findBySpec(EntityParam param){
        return null;
    }

    @Override
    public List<EntityResult> findListBySpec(EntityParam param){
        return this.baseMapper.customList(param);
    }

    @Override
    public LayuiPageInfo findPageBySpec(EntityParam param){
        Page pageContext = getPageContext();
        IPage page = this.baseMapper.customPageList(pageContext, param);
        return LayuiPageFactory.createPageInfo(page);
    }

    private Serializable getKey(EntityParam param){
        return param.getEntityId();
    }

    private Page getPageContext() {
        return LayuiPageFactory.defaultPage();
    }

    private Entity getOldEntity(EntityParam param) {
        return this.getById(getKey(param));
    }

    private Entity getEntity(EntityParam param) {
        Entity entity = new Entity();
        ToolUtil.copyProperties(param, entity);
        return entity;
    }

}
