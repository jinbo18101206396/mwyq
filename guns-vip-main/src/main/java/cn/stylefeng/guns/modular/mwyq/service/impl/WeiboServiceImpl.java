package cn.stylefeng.guns.modular.mwyq.service.impl;

import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;

import cn.stylefeng.guns.modular.mwyq.entity.Weibo;
import cn.stylefeng.guns.modular.mwyq.mapper.WeiboMapper;
import cn.stylefeng.guns.modular.mwyq.model.params.WeiboParam;
import cn.stylefeng.guns.modular.mwyq.model.result.WeiboResult;
import cn.stylefeng.guns.modular.mwyq.service.WeiboService;
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
 * @since 2020-12-16
 */
@Service
public class WeiboServiceImpl extends ServiceImpl<WeiboMapper, Weibo> implements WeiboService {

    @Override
    public void add(WeiboParam param){
        Weibo entity = getEntity(param);
        this.save(entity);
    }

    @Override
    public void delete(WeiboParam param){
        this.removeById(getKey(param));
    }

    @Override
    public void update(WeiboParam param){
        Weibo oldEntity = getOldEntity(param);
        Weibo newEntity = getEntity(param);
        ToolUtil.copyProperties(newEntity, oldEntity);
        this.updateById(newEntity);
    }

    @Override
    public WeiboResult findBySpec(WeiboParam param){
        return null;
    }

    @Override
    public List<WeiboResult> findListBySpec(WeiboParam param){
        return null;
    }

    @Override
    public LayuiPageInfo findPageBySpec(WeiboParam param){
        Page pageContext = getPageContext();
        IPage page = this.baseMapper.customPageList(pageContext, param);
        return LayuiPageFactory.createPageInfo(page);
    }

    private Serializable getKey(WeiboParam param){
        return param.getId();
    }

    private Page getPageContext() {
        return LayuiPageFactory.defaultPage();
    }

    private Weibo getOldEntity(WeiboParam param) {
        return this.getById(getKey(param));
    }

    private Weibo getEntity(WeiboParam param) {
        Weibo entity = new Weibo();
        ToolUtil.copyProperties(param, entity);
        return entity;
    }

}
