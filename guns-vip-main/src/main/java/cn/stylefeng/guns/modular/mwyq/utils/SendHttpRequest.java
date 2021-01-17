package cn.stylefeng.guns.modular.mwyq.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SendHttpRequest {
    public static String sendJsonPost(String url, JSONObject params) throws UnsupportedEncodingException {
        CloseableHttpClient client = HttpClients.createDefault();

        StringEntity entity = new StringEntity(params.toString(), "UTF-8");
        entity.setContentType("application/json");
        entity.setContentEncoding("utf-8");

        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(entity);

        CloseableHttpResponse response = null;
        String res = null;
        try {
            response = client.execute(httpPost);
            HttpEntity responseEntity = response.getEntity();

            res = EntityUtils.toString(responseEntity);
        }catch (IOException e){
            e.printStackTrace();
        }
        return res;

    }
    public static void sendGet(String url,Map<String, String> paramsMap){
        CloseableHttpClient client = HttpClients.createDefault();

        HttpGet httpGet = new HttpGet(url);
        List<NameValuePair> params = new ArrayList<>();
        for (Map.Entry<String, String> entry:paramsMap.entrySet()
             ) {
            params.add(new BasicNameValuePair(entry.getKey(),entry.getValue()));
        }
        try {
            String str = EntityUtils.toString(new UrlEncodedFormEntity(params,
                    "utf-8"));

            httpGet.setURI(new URI(httpGet.getURI().toString() + "?" + str));
            CloseableHttpResponse response = null;
            response = client.execute(httpGet);

            HttpEntity responseEntity = response.getEntity();

            String res = EntityUtils.toString(responseEntity);
            System.out.println(res);
        } catch (IOException e) {
            e.printStackTrace();
        }catch (URISyntaxException e) {
            e.printStackTrace();
        }

    }
}
