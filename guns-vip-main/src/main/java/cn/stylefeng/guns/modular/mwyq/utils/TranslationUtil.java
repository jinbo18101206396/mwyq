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

	public static String URLZang = "http://10.119.130.185:5000/translator/translate_ti2zh_article";
	public static String URLWei = "http://10.119.130.185:5000/translator/translate_uy2zh_article";
	public static String URL_WEIBO = "http://10.119.130.185:5000/translator/translate_ti2zh_paragraph";
	
	private static Logger logger = LoggerFactory.getLogger(TranslationUtil.class);
	
	public String sendPost(String content,String lang) {
		String url = null;
		if(lang.equals("wei")) {
			url = URLWei;
		}
		else if(lang.equals("zang")) {
			url = URLZang;
		}else if(lang.startsWith("weibo_")){
			url = URL_WEIBO;
		}else {
			return null;
		}
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
		}
		finally {
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
		if(StringUtils.isNotBlank(result)) {
			result = result.replace(" ", "");			
		}
		return result;
	}
}
