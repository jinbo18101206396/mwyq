package cn.stylefeng.guns.modular.mwyq.service.impl;

import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.mwyq.entity.Twitter;
import cn.stylefeng.guns.modular.mwyq.mapper.TwitterMapper;
import cn.stylefeng.guns.modular.mwyq.model.params.TwitterParam;
import cn.stylefeng.guns.modular.mwyq.model.result.TwitterResult;
import  cn.stylefeng.guns.modular.mwyq.service.TwitterService;
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
 * @since 2021-06-22
 */
@Service
public class TwitterServiceImpl extends ServiceImpl<TwitterMapper, Twitter> implements TwitterService {

    @Override
    public void add(TwitterParam param){
        Twitter entity = getEntity(param);
        this.save(entity);
    }

    @Override
    public void delete(TwitterParam param){
        this.removeById(getKey(param));
    }

    @Override
    public void update(TwitterParam param){
        Twitter oldEntity = getOldEntity(param);
        Twitter newEntity = getEntity(param);
        ToolUtil.copyProperties(newEntity, oldEntity);
        this.updateById(newEntity);
    }

    @Override
    public TwitterResult findBySpec(TwitterParam param){
        return null;
    }

    @Override
    public List<TwitterResult> findListBySpec(TwitterParam param){
        return null;
    }

    @Override
    public LayuiPageInfo findPageBySpec(TwitterParam twitterParam){
        String timeLimit = twitterParam.getTimeLimit();
        if (ToolUtil.isNotEmpty(timeLimit)) {
            String[] split = timeLimit.split("至");
            twitterParam.setBeginTime(split[0]);
            twitterParam.setEndTime(split[1]);
        }
        Page pageContext = getPageContext();
        IPage page = this.baseMapper.customPageList(pageContext, twitterParam);
        return LayuiPageFactory.createPageInfo(page);
    }

    @Override
    public List<TwitterResult> authorRankList(TwitterParam twitterParam) {
        String timeLimit = twitterParam.getTimeLimit();
        if (ToolUtil.isNotEmpty(timeLimit)) {
            String[] split = timeLimit.split("至");
            twitterParam.setBeginTime(split[0]);
            twitterParam.setEndTime(split[1]);
        }
        return  this.baseMapper.authorRankList(twitterParam);
    }

    @Override
    public List<TwitterResult> sentimentList(TwitterParam param) {
        return  this.baseMapper.sentimentList(param);
    }

    private Serializable getKey(TwitterParam param){
        return param.getId();
    }

    private Page getPageContext() {
        return LayuiPageFactory.defaultPage();
    }

    private Twitter getOldEntity(TwitterParam param) {
        return this.getById(getKey(param));
    }

    private Twitter getEntity(TwitterParam param) {
        Twitter entity = new Twitter();
        ToolUtil.copyProperties(param, entity);
        return entity;
    }

}
