package cn.stylefeng.guns.modular.mwyq.service.impl;

import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.mwyq.entity.Topic;
import cn.stylefeng.guns.modular.mwyq.mapper.TopicMapper;
import cn.stylefeng.guns.modular.mwyq.model.params.TopicParam;
import cn.stylefeng.guns.modular.mwyq.model.result.TopicResult;
import  cn.stylefeng.guns.modular.mwyq.service.TopicService;
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
public class TopicServiceImpl extends ServiceImpl<TopicMapper, Topic> implements TopicService {

    @Override
    public void add(TopicParam param){
        Topic entity = getEntity(param);
        this.save(entity);
    }

    @Override
    public void delete(TopicParam param){
        this.removeById(getKey(param));
    }

    @Override
    public void update(TopicParam param){
        Topic oldEntity = getOldEntity(param);
        Topic newEntity = getEntity(param);
        ToolUtil.copyProperties(newEntity, oldEntity);
        this.updateById(newEntity);
    }

    @Override
    public TopicResult findBySpec(TopicParam param){
        return null;
    }

    @Override
    public List<TopicResult> findListBySpec(TopicParam param){
        return null;
    }

    @Override
    public List<TopicResult> topicCountList(TopicParam param) {
        String timeLimit = param.getTimeLimit();
        if (ToolUtil.isNotEmpty(timeLimit)) {
            String[] split = timeLimit.split(" - ");
            param.setBeginTime(split[0]);
            param.setEndTime(split[1]);
        }
        return this.baseMapper.topicCountList(param);
    }

    @Override
    public LayuiPageInfo findPageBySpec(TopicParam param){
        String timeLimit = param.getTimeLimit();
        if (ToolUtil.isNotEmpty(timeLimit)) {
            String[] split = timeLimit.split(" - ");
            param.setBeginTime(split[0]);
            param.setEndTime(split[1]);
        }
        Page pageContext = getPageContext();
        IPage page = this.baseMapper.customPageList(pageContext, param);
        return LayuiPageFactory.createPageInfo(page);
    }

    private Serializable getKey(TopicParam param){
        return null;
    }

    private Page getPageContext() {
        return LayuiPageFactory.defaultPage();
    }

    private Topic getOldEntity(TopicParam param) {
        return this.getById(getKey(param));
    }

    private Topic getEntity(TopicParam param) {
        Topic entity = new Topic();
        ToolUtil.copyProperties(param, entity);
        return entity;
    }

}
