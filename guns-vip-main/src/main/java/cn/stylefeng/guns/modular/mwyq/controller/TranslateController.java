package cn.stylefeng.guns.modular.mwyq.controller;

import cn.stylefeng.guns.modular.mwyq.model.params.TranslateParam;
import cn.stylefeng.guns.modular.mwyq.utils.TranslationUtil;
import cn.stylefeng.roses.core.base.controller.BaseController;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/translate")
@Slf4j
public class TranslateController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(TranslateController.class);

    private String PREFIX = "/translate";

    @RequestMapping("")
    public String index() {
        return PREFIX + "/translate.html";
    }

    @ResponseBody
    @RequestMapping("/content")
    public JSONObject translate(@RequestBody TranslateParam translateParam) {

        String sourceLang = translateParam.getSourceLang();
        String sourceContent = translateParam.getSourceContent();
        String transModel = translateParam.getTransModel();
        String targetContent = "";

        TranslationUtil trans = new TranslationUtil();
        if(!StringUtils.isEmpty(sourceLang) && !StringUtils.isEmpty(sourceContent)){
            sourceContent = processContent(sourceContent,sourceLang);
            if("nmt".equals(transModel)){ //神经机器翻译
                targetContent = trans.sendPost(sourceContent, sourceLang,"paragraph");
            }else if("smt".equals(transModel)){ //统计机器翻译

            }
            targetContent.replace(" ","");
        }
        JSONObject transJson = new JSONObject();
        transJson.put("targetContent",targetContent);
        return transJson;
    }

    public String processContent(String content,String lang){

        if(lang.equals("zang")){
            content = content.replace("åï¿½","åï¿½ ");
        }else if(lang.equals("meng")){

        }else if(lang.equals("wei")){

        }
        return content;
    }

}
