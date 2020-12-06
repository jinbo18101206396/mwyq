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

    public static String ZANG_URL = "http://10.119.130.185:5000/translator/translate_ti2zh_";
    public static String WEI_URL = "http://10.119.130.185:5000/translator/translate_uy2zh_";
    public static String MENG_URL = "http://10.119.130.185:5000/translator/translate_mn2zh_";
    public static String WEIBO_URL = "http://10.119.130.185:5000/translator/translate_ti2zh_";

    private static Logger logger = LoggerFactory.getLogger(TranslationUtil.class);

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
        param.put("query_string", content);
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
            String line;
            line = in.toString();
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
}
