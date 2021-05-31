package cn.stylefeng.guns.modular.mwyq.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TranslationUtil {

    public static String ZANG_URL = "http://10.119.130.188:5000/translator/translate_ti2zh_";
    public static String WEI_URL = "http://10.119.130.188:5000/translator/translate_uy2zh_";
    public static String MENG_URL = "http://10.119.130.188:5000/translator/translate_mn2zh_";
    public static String WEIBO_URL = "http://10.119.130.188:5000/translator/translate_ti2zh_";

    private static Logger logger = LoggerFactory.getLogger(TranslationUtil.class);

    public static void main(String []args){
        TranslationUtil t = new TranslationUtil();
        String s = t.sendPost("མཚེས་21ཉིན། རྒྱལ་སྲིད་སྤྱི་ཁྱབ་ཁང་གསར་འགྱུར་གཞུང་དོན་ཁང་གིས《བོད་ཞི་བས་བཅིངས་འགྲོལ་དང་དར་རྒྱས་གོང་འཕེལ》གྱི་རྒྱབ་ཤ་དཀར་པོའི་དེབ་བཏོན་ནས་རྒྱལ་ཁབ་ཀྱིས་བྱེད་ཕྱོགས་སྲིད་ཇུས་རབ་དང་རིམ་པ་བཟོས་པ་དང་། ཕྱོགས་ཡོངས་ནས་མི་རིགས་ས་ཁོངས་རང་སྐྱོང་ལམ་ལུགས་དོན་འཁྱོལ་བྱས་ཏེ་རྒྱུན་གཏན་གྱི་ཆོས་ལུགས་བྱེད་སྒོར་ཁྲིམས་ལྟར་སྲུང་སྐྱོང་བྱས་ནས་མི་རིགས་ཁག་ཐུན་མོང་ཐོག་མཐུན་སྒྲིལ་འབད་འཐབ་དང་ཐུན་མོང་ཐོག་དར་རྒྱས་གོང་འཕེལ་ཡོང་བར་སྐུལ་འདེད་བཏང་ཡོད་ཅེས་ངོ་སྤྲོད་བྱས་པ་རེད།","http://10.119.130.188:5000/translator/translate_ti2zh_article");
        //String s = t.sendPost("ى جىنپىڭ يەر شارىۋى ساغلاملىق باشلىقلار يىغىنىغا قاتناشتى ھەم م..","http://10.119.130.188:5000/translator/translate_uy2zh_article");
        //String s = t.sendPost( "\uE2CE\uE26C\uE30B\uE291\uE2B5 \uE2E4\uE289\uE289\uE2F9 \uE289\uE2B5 \uE2CE\uE26C\uE301\uE27E\uE2FA\uE2E9\uE26A \uE30F\uE289\uE30B\uE289\uE325\uE26A \uE267\uE317\uE268 48 \uE2A2\uE2DD\uE2AC\uE2EF\uE301\uE276\uE2B5 ","http://10.119.130.188:5000/translator/translate_mn2zh_article");
        //String s = t.sendPost("བོད་ཡིག","http://10.119.130.188:5000/translator/translate");
        System.out.println(s);
    }
    

    //type表示翻译类型(article/paragraph),
    public String sendPost(String content, String lang, String type) {
        String url = null;

        if(StringUtils.isEmpty(type)){
            type = "paragraph";
        }
        if (lang.equals("zang")) {
            url = ZANG_URL + type;
        } else if (lang.equals("wei")) {
            url = WEI_URL + type;
        } else if (lang.equals("meng")) {
            url = MENG_URL + type;
        } else if (lang.equals("weibo")) {
            url = WEIBO_URL + type;
        }
        return sendPost(content, url);
    }

    public String sendPost(String content, String url) {
        JSONObject param = new JSONObject();
        param.put("src", content);
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            URLConnection conn = realUrl.openConnection();
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept-Charset", "UTF-8");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            out = new PrintWriter(conn.getOutputStream());
            out.print(param);
            out.flush();
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = in.toString();
            while ((line = in.readLine()) != null) {
                result += line;
            }
            JSONObject json = JSONObject.parseObject(result);
            result = json.getString("result");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        if (StringUtils.isNotBlank(result)) {
            result = result.replace(" ", "");
        }
        return result;
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
