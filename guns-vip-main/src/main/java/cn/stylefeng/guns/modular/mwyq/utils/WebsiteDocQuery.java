package cn.stylefeng.guns.modular.mwyq.utils;

import cn.stylefeng.guns.modular.mwyq.entity.News;
import cn.stylefeng.guns.modular.mwyq.entity.SolrDocRes;
import cn.stylefeng.guns.modular.mwyq.enums.SolrFields;
import com.alibaba.fastjson.JSONObject;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 网页信息检索
 *
 * @author jinbo
 * @Date 2021/1/17
 */

public class WebsiteDocQuery {
//    private final String MO_SOLR_URL = "http://10.119.130.185:8983/solr/clir";

    private final String MO_SOLR_URL = "http://10.119.130.183:8980/solr/clir";
    private final String BI_SOLR_URL = "http://10.119.130.183:8966/solr/mn-zh";
    private final Logger logger = LoggerFactory.getLogger(WeiboDocQuery.class);
    private final SolrQuery query = new SolrQuery();
    public SolrServer moServer = new HttpSolrServer(MO_SOLR_URL);
    public SolrServer biServer = new HttpSolrServer(BI_SOLR_URL);
    public SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private QueryResponse response = null;

    public WebsiteDocQuery() {
    }

    /**
     * 双语检索
     *
     * @author jinbo
     * @Date 2021/1/17
     */
    public void biQuery(String keyWords, String lang, String sensitive, String cycle, JSONObject websiteJson) {
        String queryString = "";
        if (lang.equals("cn-meng")) {
            queryString = CrossLangQE.getMnFromZhInCrossVali(keyWords, 2);
        } else if (lang.equals("cn-zang")) {
            queryString = CrossLangQE_Ti.getTiFromZhInCrossVali(keyWords, 2);
        } else if (lang.equals("cn-wei")) {
            queryString = CrossLangQE_Wei.getWeiFromZhInCrossVali(keyWords, 2);
        }
        String query1 = "news_title:".concat(queryString).concat(" AND news_content:").concat(queryString);
        query.setQuery(query1);
//        query.setQuery("\"" + queryString + "\" or " + queryString);
        query.setStart(0);
        query.setRows(500);
        query.setSort("news_time", SolrQuery.ORDER.desc);
        List<SolrDocument> docs = new ArrayList<SolrDocument>();
        try {
            response = biServer.query(query);
            docs = response.getResults();
        } catch (SolrServerException e) {
            e.printStackTrace();
        }
        List<News> newsList = new ArrayList<>();
        List<News> hotNewsList = new ArrayList<>();
        String newsId = "";
        String newsContent = "";
        int hotCount = 0;

        int positiveNum = 0;
        int negativeNum = 0;
        int neutralNum = 0;

        for (SolrDocument doc : docs) {
            News news = new News();
            String newsTitle = doc.getFieldValue("news_title").toString();
            newsTitle = newsTitle.replace(queryString, "<span style=\"color:red\">" + queryString + "</span>");
            news.setNewsTitle(newsTitle);
            try {
                news.setNewsTime(sdf.parse(doc.getFieldValue("news_time").toString()));
            } catch (Exception e) {
                e.printStackTrace();
            }
            newsContent = doc.getFieldValue("news_content").toString();
            newsId = doc.getFieldValue("news_id").toString();
            if (newsContent.length() > 500) {
                newsContent = newsContent.substring(0, 500);
            }
            if (!StringUtils.isEmpty(newsContent) && !StringUtils.isEmpty(queryString)) {
                newsContent = newsContent.replace("\u1800", "\u202f").replace(queryString, "<span style=\"color:red\">" + queryString + "</span>") + "...";
                newsContent = newsContent.replace(queryString, "<span style=\"color:red\">" + queryString + "</span>");
                news.setNewsContent(newsContent);
            }
            if (!StringUtils.isEmpty(newsId) && !StringUtils.isEmpty(queryString)) {
                newsId = newsId.replace(queryString, "<span style=\"color:red\">" + queryString + "</span>");
                news.setNewsId(Integer.valueOf(newsId));
            }
            String isSensitive = doc.getFieldValue("is_sensitive").toString();
            if ("1".equals(isSensitive)) {
                neutralNum++;
            } else if ("2".equals(isSensitive)) {
                negativeNum++;
            } else if ("3".equals(isSensitive)) {
                positiveNum++;
            }
            newsList.add(news);
            if (hotCount++ < 10) {
                hotNewsList.add(news);
            }
        }
        websiteJson.put("newsNum", response.getResults().getNumFound());
        websiteJson.put("newsList", newsList);
        websiteJson.put("hotNewsList", hotNewsList);
        websiteJson.put("keyword", keyWords);
        websiteJson.put("queryString", queryString);

        websiteJson.put("positiveNum", positiveNum);
        websiteJson.put("negativeNum", negativeNum);
        websiteJson.put("neutralNum", neutralNum);
        query.clear();
    }

    /**
     * 单语检索
     *
     * @author jinbo
     * @Date 2021/1/17
     */
    public void moQuery(String keyWords, String langType, String sensitive, String cycle, boolean method, JSONObject websiteJson) {
        List<SolrDocRes> docResList = new ArrayList<SolrDocRes>();
        List<SolrDocRes> moHotNewsList = new ArrayList<>();
        String[] fileds = {"news_title", "news_content", "news_id", "news_time", "crawl_source", "lang_type", "is_sensitive", "news_url"};
        String queryString = "news_title:".concat(keyWords).concat(" AND news_content:").concat(keyWords).concat(" AND lang_type:").concat(langType);
        query.setQuery(queryString);
//        query.addFilterQuery("is_sensitive:".concat(sensitive));
        query.setStart(0);
        query.setRows(200);
        query.setFields(fileds);
        int positiveNum = 0;
        int negativeNum = 0;
        int neutralNum = 0;
        try {
            int hotCount = 0;
            response = moServer.query(query);
            for (Iterator<SolrDocument> docIter = response.getResults().iterator(); docIter.hasNext(); ) {
                hotCount++;
                SolrDocument solrDoc = docIter.next();
                SolrDocRes docRes = new SolrDocRes();
                docRes.setNewsId(solrDoc.getFieldValue(SolrFields.NEWS_ID.getName()).toString());
                docRes.setNewsTitle(solrDoc.getFieldValue(SolrFields.NEWS_TITLE.getName()).toString());
                if (method) {
                    docRes.setNewsContent(solrDoc.getFieldValue(SolrFields.NEWS_CONTENT.getName()).toString().replace(keyWords, "<span id=\"add\" style=\"color:red\">" + keyWords + "</span>"));
                } else {
                    docRes.setNewsContent(solrDoc.getFieldValue(SolrFields.NEWS_CONTENT.getName()).toString());
                }
                docRes.setNewsTime(solrDoc.getFieldValue(SolrFields.NEWS_TIME.getName()).toString());
                docRes.setCrawlSource(solrDoc.getFieldValue(SolrFields.NEWS_SOURCE.getName()).toString());
                docRes.setNewsUrl(solrDoc.getFieldValue(SolrFields.NEWS_URL.getName()).toString());
                docRes.setLangType(solrDoc.getFieldValue(SolrFields.LANG_TYPE.getName()).toString());
                docRes.setIsSensitive(solrDoc.getFieldValue(SolrFields.SENSITIVE.getName()).toString());
                docResList.add(docRes);

                String isSensitive = solrDoc.getFieldValue("is_sensitive").toString();
                if ("1".equals(isSensitive)) {
                    neutralNum++;
                } else if ("2".equals(isSensitive)) {
                    negativeNum++;
                } else if ("3".equals(isSensitive)) {
                    positiveNum++;
                }
                if (hotCount % 5 == 0 && hotCount < 100) {
                    moHotNewsList.add(docRes);
                }
            }
        } catch (SolrServerException e) {
            e.printStackTrace();
        }
        websiteJson.put("newsList", docResList);
        websiteJson.put("hotNewsList", moHotNewsList);
        websiteJson.put("newsNum", response.getResults().getNumFound());
        websiteJson.put("queryString", keyWords);

        websiteJson.put("positiveNum", positiveNum);
        websiteJson.put("negativeNum", negativeNum);
        websiteJson.put("neutralNum", neutralNum);
    }

    public String getContentById(int newsId) {
        String newsContent = null;
        query.setQuery("news_id:" + newsId);
        try {
            newsContent = biServer.query(query).getResults().get(0).getFieldValue("news_content").toString();
        } catch (SolrServerException e) {
            e.printStackTrace();
        }
        return newsContent;
    }

    public String getTitleById(int newsId) {
        String newsTitle = null;
        query.setQuery("news_id:" + newsId);
        try {
            newsTitle = biServer.query(query).getResults().get(0).getFieldValue("news_title").toString();
        } catch (SolrServerException e) {
            e.printStackTrace();
        }
        return newsTitle;
    }

    public String getTimeById(int newsId) {
        String newsTime = null;
        query.setQuery("news_id:" + newsId);
        try {
            newsTime = biServer.query(query).getResults().get(0).getFieldValue("news_time").toString();
        } catch (SolrServerException e) {
            e.printStackTrace();
        }
        return newsTime;
    }

    public String getUrlById(int newsId) {
        String newsUrl = null;
        query.setQuery("news_id:" + newsId);
        try {
            newsUrl = biServer.query(query).getResults().get(0).getFieldValue("news_url").toString();
        } catch (SolrServerException e) {
            e.printStackTrace();
        }
        return newsUrl;
    }
}