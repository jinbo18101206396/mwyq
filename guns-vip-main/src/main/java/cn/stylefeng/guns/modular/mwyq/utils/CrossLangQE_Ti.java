package cn.stylefeng.guns.modular.mwyq.utils;

import com.alibaba.fastjson.JSONObject;
import java.io.UnsupportedEncodingException;

/**
 * 对应   跨语言词向量  和  单语言词向量扩展    方法
 */

public class CrossLangQE_Ti {
    private static String url="http://10.119.130.185:7000/";

    //单语言词向量扩展URL
    private static String urlTiTopWords=url.concat("ti_top_words");
    private static String urlGetZhTopWords=url.concat("zh_top_words");
    private static String urlGetTiFromZh=url.concat("get_ti_top_words_from_zh");
    private static String urlGetTiFormZhByWholeScore = url.concat("get_ti_top_words_from_zh_by_whole_score");

    //下面四个是最新的
    private static String urlGetTiFromZhInSeries=url.concat("get_ti_top_words_from_zh_in_series");
    private static String urlGetTiFromZhInSeriesOpt=url.concat("get_ti_top_words_from_zh_in_series_opt");
    private static String urlGetTiFromZhInCrossVali=url.concat("get_ti_top_words_from_zh_in_cv");
    private static String urlGetTiFromZhInCrossValiOpt=url.concat("get_ti_top_words_from_zh_in_cv_opt");

    //单语言词向量扩展
    public static String getTiTopWords(String tiWords, int num){
        try {
            return getExtedResultByUrl(tiWords,urlTiTopWords, num);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getZhTopWords(String zhWords, int num) throws UnsupportedEncodingException {
        try {
            return getExtedResultByUrl(zhWords,urlGetZhTopWords, num);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getTiWordsFromZh(String zhWords, int num){
        try {
            return getExtedResultByUrl(zhWords, urlGetTiFromZh,num);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getTiWordsFromZhByWholeScore(String zhWords, int num){
        try {
            return getExtedResultByUrl(zhWords, urlGetTiFormZhByWholeScore,num);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getExtedResultByUrl(String words, String ulr,  int num) throws UnsupportedEncodingException {
        JSONObject params = new JSONObject();
        params.put("key_word", words);
        params.put("top_num", num);
        return SendHttpRequest.sendJsonPost(ulr, params);
    }
    public static String getExtedResultByUrlOpt(String words, String ulr,  int num) throws UnsupportedEncodingException {
        JSONObject params = new JSONObject();
        params.put("key_word", words);
        params.put("top_num", num);
        params.put("is_opt", true);
        params.put("threshold_up", 0.51);
        params.put("threshold_down", 0.3);

        return SendHttpRequest.sendJsonPost(ulr, params);
    }
    public static String getTiFromZhInSeries(String zhWords, int num){
        try {
            return getExtedResultByUrl(zhWords, urlGetTiFromZhInSeries, num);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getTiFromZhInSeriesOpt(String zhWords, int num){
        try {
            return getExtedResultByUrlOpt(zhWords, urlGetTiFromZhInSeriesOpt, num);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getTiFromZhInCrossVali(String zhWords, int num){
        try {
            return getExtedResultByUrl(zhWords, urlGetTiFromZhInCrossVali, num);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static String getTiFromZhInCrossValiOpt(String zhWords, int num){
        try {
            return getExtedResultByUrl(zhWords, urlGetTiFromZhInCrossValiOpt, num);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static void main(String[] args) throws UnsupportedEncodingException {
    }

}

