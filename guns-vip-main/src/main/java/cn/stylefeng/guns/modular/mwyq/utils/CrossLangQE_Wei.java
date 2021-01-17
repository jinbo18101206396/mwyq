package cn.stylefeng.guns.modular.mwyq.utils;

import com.alibaba.fastjson.JSONObject;
import java.io.UnsupportedEncodingException;

/**
* 对应   跨语言词向量  和  单语言词向量扩展方法
*/

public class CrossLangQE_Wei {
    private static String url = "http://10.119.130.185:6000/";

    //单语言词向量扩展URL
    private static String urlWeiTopWords = url.concat("wei_top_words");
    private static String urlGetZhTopWords = url.concat("zh_top_words");
    private static String urlGetWeiFromZh = url.concat("get_wei_top_words_from_zh");
    private static String urlGetWeiFormZhByWholeScore = url.concat("get_wei_top_words_from_zh_by_whole_score");

    //下面四个是最新的
    private static String urlGetWeiFromZhInSeries = url.concat("get_wei_top_words_from_zh_in_series");
    private static String urlGetWeiFromZhInSeriesOpt = url.concat("get_wei_top_words_from_zh_in_series_opt");

    private static String urlGetWeiFromZhInCrossVali = url.concat("get_wei_top_words_from_zh_in_cv");
    private static String urlGetWeiFromZhInCrossValiOpt = url.concat("get_wei_top_words_from_zh_in_cv_opt");

    //单语言词向量扩展
    public static String getWeiTopWords(String weiWords, int num) {
        try {
            return getExtedResultByUrl(weiWords, urlWeiTopWords, num);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getZhTopWords(String zhWords, int num) throws UnsupportedEncodingException {
        try {
            return getExtedResultByUrl(zhWords, urlGetZhTopWords, num);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getWeiWordsFromZh(String zhWords, int num) {
        try {
            return getExtedResultByUrl(zhWords, urlGetWeiFromZh, num);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getWeiWordsFromZhByWholeScore(String zhWords, int num) {
        try {
            return getExtedResultByUrl(zhWords, urlGetWeiFormZhByWholeScore, num);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getExtedResultByUrl(String words, String ulr, int num) throws UnsupportedEncodingException {
        JSONObject params = new JSONObject();
        params.put("key_word", words);
        params.put("top_num", num);
        return SendHttpRequest.sendJsonPost(ulr, params);
    }

    public static String getExtedResultByUrlOpt(String words, String ulr, int num) throws UnsupportedEncodingException {
        JSONObject params = new JSONObject();
        params.put("key_word", words);
        params.put("top_num", num);
        params.put("is_opt", true);
        params.put("threshold_up", 0.51);
        params.put("threshold_down", 0.3);

        return SendHttpRequest.sendJsonPost(ulr, params);
    }

    public static String getWeiFromZhInSeries(String zhWords, int num) {
        try {
            return getExtedResultByUrl(zhWords, urlGetWeiFromZhInSeries, num);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getWeiFromZhInSeriesOpt(String zhWords, int num) {
        try {
            return getExtedResultByUrlOpt(zhWords, urlGetWeiFromZhInSeriesOpt, num);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getWeiFromZhInCrossVali(String zhWords, int num) {
        try {
            return getExtedResultByUrl(zhWords, urlGetWeiFromZhInCrossVali, num);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getWeiFromZhInCrossValiOpt(String zhWords, int num) {
        try {
            return getExtedResultByUrl(zhWords, urlGetWeiFromZhInCrossValiOpt, num);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
    }

}
