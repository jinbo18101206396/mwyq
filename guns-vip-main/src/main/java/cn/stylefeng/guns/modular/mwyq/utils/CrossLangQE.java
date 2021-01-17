package cn.stylefeng.guns.modular.mwyq.utils;

import java.io.UnsupportedEncodingException;
import com.alibaba.fastjson.JSONObject;

public class CrossLangQE {
    private static String url = "http://10.119.130.185:4999/";

    // 鍗曡瑷�璇嶅悜閲忔墿灞昒RL
    private static String urlLatinTopWords = url.concat("latin_top_words");
    private static String urlMnTopWords = url.concat("mn_top_words");
    private static String urlGetZhTopWords = url.concat("zh_top_words");

    // 璺ㄨ瑷�璇嶅悜閲廢RL
    private static String urlLatinZh = url.concat("get_latin_top_words_from_zh");
    private static String latinZhScoreByWholeScore = url.concat("get_latin_top_words_from_zh_by_whole_score");

    private static String urlGetMnFromZh = url.concat("get_mn_top_words_from_zh");
    private static String urlGetMnFormZhByWholeScore = url.concat("get_mn_top_words_from_zh_by_whole_score");

    // 涓嬮潰鍥涗釜鏄渶鏂扮殑
    private static String urlGetMnFromZhInSeries = url.concat("get_mn_top_words_from_zh_in_series");
    private static String urlGetMnFromZhInSeriesOpt = url.concat("get_mn_top_words_from_zh_in_series_opt");

    private static String urlGetMnFromZhInCrossVali = url.concat("get_mn_top_words_from_zh_in_cv");
    private static String urlGetMnFromZhInCrossValiOpt = url.concat("get_mn_top_words_from_zh_in_cv_opt");

    // 鍗曡瑷�璇嶅悜閲忔墿灞�
    public static String getMnTopWords(String mnWords, int num) {
        try {
            return getExtedResultByUrl(mnWords, urlMnTopWords, num);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getLatinTopWords(String latinWords, int num) throws UnsupportedEncodingException {
        try {
            return getExtedResultByUrl(latinWords, urlLatinTopWords, num);
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

    // 璺ㄨ瑷�璇嶅悜閲�
    public static String getLatinFromZh(String zhWords, int num) throws UnsupportedEncodingException {
        try {
            return getExtedResultByUrl(zhWords, urlLatinZh, num);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getLatinFromZhScoreByWholeScore(String zhWords, int num) throws UnsupportedEncodingException {
        try {
            return getExtedResultByUrl(zhWords, latinZhScoreByWholeScore, num);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getMnWordsFromZh(String zhWords, int num) {
        try {
            return getExtedResultByUrl(zhWords, urlGetMnFromZh, num);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getMnWordsFromZhByWholeScore(String zhWords, int num) {
        try {
            return getExtedResultByUrl(zhWords, urlGetMnFormZhByWholeScore, num);
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

    public static String getMnFromZhInSeries(String zhWords, int num) {
        try {
            return getExtedResultByUrl(zhWords, urlGetMnFromZhInSeries, num);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getMnFromZhInSeriesOpt(String zhWords, int num) {
        try {
            return getExtedResultByUrlOpt(zhWords, urlGetMnFromZhInSeriesOpt, num);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getMnFromZhInCrossVali(String zhWords, int num) {
        try {
            return getExtedResultByUrl(zhWords, urlGetMnFromZhInCrossVali, num);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getMnFromZhInCrossValiOpt(String zhWords, int num) {
        try {
            return getExtedResultByUrl(zhWords, urlGetMnFromZhInCrossValiOpt, num);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
    }

}
