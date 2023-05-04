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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author jinbo
 */
public class SendHttpRequest {

    public static String sendJsonPost(String url, JSONObject params) {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        StringEntity entity = new StringEntity(params.toString(), StandardCharsets.UTF_8);
        entity.setContentEncoding("UTF-8");
        entity.setContentType("application/json");
        httpPost.setEntity(entity);
        String responseBody = null;
        try {
            CloseableHttpResponse response = client.execute(httpPost);
            HttpEntity responseEntity = response.getEntity();
            responseBody = EntityUtils.toString(responseEntity, StandardCharsets.UTF_8);
            response.close();
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseBody;
    }


    // POST 请求多语言翻译接口
    public static String sendTranslatePost(String url, JSONObject params) {
        return SendHttpRequest.sendEsPost(url,params);
    }


    // POST 请求Es接口
    public static String sendEsPost(String url, JSONObject params) {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        StringEntity entity = new StringEntity(params.toString(), StandardCharsets.UTF_8);
        entity.setContentEncoding("UTF-8");
        entity.setContentType("application/json");
        httpPost.setEntity(entity);

        StringBuilder result = new StringBuilder();
        try {
            CloseableHttpResponse response = client.execute(httpPost);
            HttpEntity responseEntity = response.getEntity();
            BufferedReader reader = new BufferedReader(new InputStreamReader(responseEntity.getContent(), StandardCharsets.UTF_8));
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    public static void sendGet(String url, Map<String, String> paramsMap) {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        List<NameValuePair> params = new ArrayList<>();
        for (Map.Entry<String, String> entry : paramsMap.entrySet()
        ) {
            params.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
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
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
