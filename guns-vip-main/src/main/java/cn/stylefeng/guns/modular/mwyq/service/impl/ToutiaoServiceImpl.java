package cn.stylefeng.guns.modular.mwyq.service.impl;

import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.mwyq.entity.Toutiao;
import cn.stylefeng.guns.modular.mwyq.mapper.ToutiaoMapper;
import cn.stylefeng.guns.modular.mwyq.model.params.ToutiaoParam;
import cn.stylefeng.guns.modular.mwyq.model.result.ToutiaoResult;
import  cn.stylefeng.guns.modular.mwyq.service.ToutiaoService;
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
 * @author 金波
 * @since 2023-05-18
 */
@Service
public class ToutiaoServiceImpl extends ServiceImpl<ToutiaoMapper, Toutiao> implements ToutiaoService {

    @Override
    public void add(ToutiaoParam param){
        Toutiao entity = getEntity(param);
        this.save(entity);
    }

    @Override
    public void delete(ToutiaoParam param){
        this.removeById(getKey(param));
    }

    @Override
    public void update(ToutiaoParam param){
        Toutiao oldEntity = getOldEntity(param);
        Toutiao newEntity = getEntity(param);
        ToolUtil.copyProperties(newEntity, oldEntity);
        this.updateById(newEntity);
    }

    @Override
    public ToutiaoResult findBySpec(ToutiaoParam param){
        return null;
    }

    @Override
    public List<ToutiaoResult> findListBySpec(ToutiaoParam param){
        return null;
    }

    @Override
    public LayuiPageInfo findPageBySpec(ToutiaoParam param){
        Page pageContext = getPageContext();
        IPage page = this.baseMapper.customPageList(pageContext, param);
        return LayuiPageFactory.createPageInfo(page);
    }

    private Serializable getKey(ToutiaoParam param){
        return param.getId();
    }

    private Page getPageContext() {
        return LayuiPageFactory.defaultPage();
    }

    private Toutiao getOldEntity(ToutiaoParam param) {
        return this.getById(getKey(param));
    }

    private Toutiao getEntity(ToutiaoParam param) {
        Toutiao entity = new Toutiao();
        ToolUtil.copyProperties(param, entity);
        return entity;
    }

}
