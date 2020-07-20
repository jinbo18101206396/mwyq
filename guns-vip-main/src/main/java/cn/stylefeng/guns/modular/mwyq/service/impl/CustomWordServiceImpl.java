package cn.stylefeng.guns.modular.mwyq.service.impl;

import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.mwyq.entity.CustomWord;
import cn.stylefeng.guns.modular.mwyq.mapper.CustomWordMapper;
import cn.stylefeng.guns.modular.mwyq.model.params.CustomWordParam;
import cn.stylefeng.guns.modular.mwyq.model.result.CustomWordResult;
import cn.stylefeng.guns.modular.mwyq.service.CustomWordService;
import cn.stylefeng.guns.modular.mwyq.utils.TranslationUtil;
import cn.stylefeng.guns.sys.modular.system.entity.User;
import cn.stylefeng.guns.sys.modular.system.mapper.UserMapper;
import cn.stylefeng.roses.core.util.ToolUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 定制词表 服务实现类
 * </p>
 *
 * @author jinbo
 * @since 2020-06-11
 */
@Service
public class CustomWordServiceImpl extends ServiceImpl<CustomWordMapper, CustomWord> implements CustomWordService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public void add(CustomWordParam param) {
        CustomWord entity = getEntity(param);
        this.save(entity);
    }

    @Override
    public void delete(CustomWordParam param) {
        this.removeById(getKey(param));
    }

    @Override
    public void update(CustomWordParam param) {
        CustomWord oldEntity = getOldEntity(param);
        CustomWord newEntity = getEntity(param);
        ToolUtil.copyProperties(newEntity, oldEntity);
        this.updateById(newEntity);
    }

    @Override
    public CustomWordResult findBySpec(CustomWordParam param) {
        return null;
    }

    @Override
    public List<CustomWordResult> findListBySpec(CustomWordParam param) {
        return this.baseMapper.customList(param);
    }

    @Override
    public LayuiPageInfo findPageBySpec(CustomWordParam customWordParam) {

        //若为少数语言主题词，则翻译成汉语
        String lang = customWordParam.getLang();
        if(ToolUtil.isNotEmpty(lang) && !lang.equals("cn")){
            TranslationUtil translationUtil = new TranslationUtil();
            String name = translationUtil.sendPost(customWordParam.getName(),lang);
            customWordParam.setName(name);
            customWordParam.setLang("cn");
        }
        String timeLimit = customWordParam.getTimeLimit();
        if (ToolUtil.isNotEmpty(timeLimit)) {
            String[] split = timeLimit.split(" - ");
            customWordParam.setBeginTime(split[0]);
            customWordParam.setEndTime(split[1]);
        }
        Page pageContext = getPageContext();
        Page<CustomWordResult> customWordResultPage = this.baseMapper.customPageList(pageContext, customWordParam);
        List<CustomWordResult> records = customWordResultPage.getRecords();
        for (CustomWordResult record : records) {
            User user = userMapper.selectById(record.getCreateUser());
            record.setUserName(user.getName());
        }
        return LayuiPageFactory.createPageInfo(customWordResultPage);
    }

    @Override
    public List<String> translateCnCustomwordName(String name, String lang) {
        return null;
    }

    @Override
    public List<String> translateCnCustomwordNames(List<String> names, String lang) {
        return null;
    }


    private Serializable getKey(CustomWordParam param) {
        return param.getId();
    }

    private Page getPageContext() {
        return LayuiPageFactory.defaultPage();
    }

    private CustomWord getOldEntity(CustomWordParam param) {
        return this.getById(getKey(param));
    }

    private CustomWord getEntity(CustomWordParam param) {
        CustomWord entity = new CustomWord();
        ToolUtil.copyProperties(param, entity);
        return entity;
    }

}
