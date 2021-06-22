package cn.stylefeng.guns.modular.mwyq.utils;

import cn.stylefeng.guns.modular.mwyq.entity.SolrWeiboDocResEntity;
import cn.stylefeng.guns.modular.mwyq.enums.SolrFields;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Calendar;
import java.util.Date;

public class WeiboDocQuery {
    private final String SOLRURL = "http://localhost:8983/solr/mn-zh/";

    private SolrQuery query = new SolrQuery();
    private QueryResponse response = null;

    private Logger logger = LoggerFactory.getLogger(WeiboDocQuery.class);

    public SolrServer server = new HttpSolrServer(SOLRURL);

    public WeiboDocQuery(){
    }

	/**
	 * 查询微博列表
	 *
	 * @author zhangliang
	 */
    public List<SolrWeiboDocResEntity> query(String keyWords, String blogger,String langType, String conetent_emotion, String content_author, String content_time, int start, int rows_num,boolean method){
    	List<SolrWeiboDocResEntity> docResList = new ArrayList<SolrWeiboDocResEntity>();
        int rows = rows_num;
        if (rows < 1){
            rows = 10;
        }
        Date curTime = new Date();
		Calendar calWeek = Calendar.getInstance();
		calWeek.add(Calendar.WEEK_OF_MONTH, -1);
		Date pastWeekDate = calWeek.getTime();
		Calendar calMonth = Calendar.getInstance();
		calMonth.add(Calendar.MONTH, -1);
		Date pastMonthDate = calMonth.getTime();
		Calendar  calYear = Calendar.getInstance();
		calYear.add(Calendar.YEAR,-1);
		Date pastYearDate = calYear.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd'T'HH:mm:ss'Z'");
        String[] fileds = {"id", "weibo_content", "weibo_author", "weibo_emotion","weibo_time","weibo_lang","weibo_likes","weibo_comments","weibo_forwards","heat"};
        String queryString = "";
        if(content_author != null && conetent_emotion != null && content_time != null) {
            if(content_author.equals("author-all")) {
            	if(conetent_emotion.equals("emotion-all")){
            		if(content_time.equals("time-all")) {
            			queryString = ("weibo_content:").concat(keyWords).concat(" OR id:").concat(keyWords);
            		}else if(content_time.equals("time-week")) {
            			queryString = ("weibo_content:").concat(keyWords).concat(" AND weibo_time:").concat("["+ sdf.format(pastWeekDate) + " TO " + sdf.format(curTime) + "]").concat(" OR id:").concat(keyWords);
            		} else if(content_time.equals("time-month")){
            			queryString = ("weibo_content:").concat(keyWords).concat(" AND weibo_time:").concat("["+ sdf.format(pastMonthDate) + " TO " + sdf.format(curTime) + "]").concat(" OR id:").concat(keyWords);
            		}else {
            			queryString = ("weibo_content:").concat(keyWords).concat(" AND weibo_time:").concat("["+ sdf.format(pastYearDate) + " TO " + sdf.format(curTime) + "]").concat(" OR id:").concat(keyWords);
            		}
            	} else if(conetent_emotion.equals("emotion-positive")) {
            		if(content_time.equals("time-all")) {
            			queryString = ("weibo_content:").concat(keyWords).concat(" AND weibo_emotion:positive").concat(" OR id:").concat(keyWords);
            		}else if(content_time.equals("time-week")) {
            			queryString = ("weibo_content:").concat(keyWords).concat(" AND weibo_emotion:positive").concat(" AND weibo_time:").concat("["+ sdf.format(pastWeekDate) + " TO " + sdf.format(curTime) + "]").concat(" OR id:").concat(keyWords);
            		} else if(content_time.equals("time-month")){
            			queryString = ("weibo_content:").concat(keyWords).concat(" AND weibo_emotion:positive").concat(" AND weibo_time:").concat("["+ sdf.format(pastMonthDate) + " TO " + sdf.format(curTime) + "]").concat(" OR id:").concat(keyWords);
            		}else {
            			queryString = ("weibo_content:").concat(keyWords).concat(" AND weibo_emotion:positive").concat(" AND weibo_time:").concat("["+ sdf.format(pastYearDate) + " TO " + sdf.format(curTime) + "]").concat(" OR id:").concat(keyWords);
            		}
            		
            	} else {
            		if(content_time.equals("time-all")) {
            			queryString = ("weibo_content:").concat(keyWords).concat(" AND weibo_emotion:negative").concat(" OR id:").concat(keyWords);
            		}else if(content_time.equals("time-week")) {
            			queryString = ("weibo_content:").concat(keyWords).concat(" AND weibo_emotion:negative").concat(" AND weibo_time:").concat("["+ sdf.format(pastWeekDate) + " TO " + sdf.format(curTime) + "]").concat(" OR id:").concat(keyWords);
            		} else if(content_time.equals("time-month")){
            			queryString = ("weibo_content:").concat(keyWords).concat(" AND weibo_emotion:negative").concat(" AND weibo_time:").concat("["+ sdf.format(pastMonthDate) + " TO " + sdf.format(curTime) + "]").concat(" OR id:").concat(keyWords);
            		}else {
            			queryString = ("weibo_content:").concat(keyWords).concat(" AND weibo_emotion:negative").concat(" AND weibo_time:").concat("["+ sdf.format(pastYearDate) + " TO " + sdf.format(curTime) + "]").concat(" OR id:").concat(keyWords);
            		}
            	}
            }
            if(content_author.equals("author-single")) {
            	if(conetent_emotion.equals("emotion-all")){
            		if(content_time.equals("time-all")) {
            			queryString = ("weibo_author:").concat(keyWords).concat(" OR id:").concat(keyWords);
            		}else if(content_time.equals("time-week")) {
            			queryString = ("weibo_author:").concat(keyWords).concat(" AND weibo_time:").concat("["+ sdf.format(pastWeekDate) + " TO " + sdf.format(curTime) + "]").concat(" OR id:").concat(keyWords);
            		} else if(content_time.equals("time-month")){
            			queryString = ("weibo_author:").concat(keyWords).concat(" AND weibo_time:").concat("["+ sdf.format(pastMonthDate) + " TO " + sdf.format(curTime) + "]").concat(" OR id:").concat(keyWords);
            		}else {
            			queryString = ("weibo_author:").concat(keyWords).concat(" AND weibo_time:").concat("["+ sdf.format(pastYearDate) + " TO " + sdf.format(curTime) + "]").concat(" OR id:").concat(keyWords);
            		}
            	} else if(conetent_emotion.equals("emotion-positive")) {
            		if(content_time.equals("time-all")) {
            			queryString = ("weibo_author:").concat(keyWords).concat(" AND weibo_emotion:positive").concat(" OR id:").concat(keyWords);
            		}else if(content_time.equals("time-week")) {
            			queryString = ("weibo_author:").concat(keyWords).concat(" AND weibo_emotion:positive").concat(" AND weibo_time:").concat("["+ sdf.format(pastWeekDate) + " TO " + sdf.format(curTime) + "]").concat(" OR id:").concat(keyWords);
            		} else if(content_time.equals("time-month")){
            			queryString = ("weibo_author:").concat(keyWords).concat(" AND weibo_emotion:positive").concat(" AND weibo_time:").concat("["+ sdf.format(pastMonthDate) + " TO " + sdf.format(curTime) + "]").concat(" OR id:").concat(keyWords);
            		}else {
            			queryString = ("weibo_author:").concat(keyWords).concat(" AND weibo_emotion:positive").concat(" AND weibo_time:").concat("["+ sdf.format(pastYearDate) + " TO " + sdf.format(curTime) + "]").concat(" OR id:").concat(keyWords);
            		}
            	} else {
            		if(content_time.equals("time-all")) {
            			queryString = ("weibo_author:").concat(keyWords).concat(" AND weibo_emotion:negative").concat(" OR id:").concat(keyWords);
            		}else if(content_time.equals("time-week")) {
            			queryString = ("weibo_author:").concat(keyWords).concat(" AND weibo_emotion:negative").concat(" AND weibo_time:").concat("["+ sdf.format(pastWeekDate) + " TO " + sdf.format(curTime) + "]").concat(" OR id:").concat(keyWords);
            		} else if(content_time.equals("time-month")){
            			queryString = ("weibo_author:").concat(keyWords).concat(" AND weibo_emotion:negative").concat(" AND weibo_time:").concat("["+ sdf.format(pastMonthDate) + " TO " + sdf.format(curTime) + "]").concat(" OR id:").concat(keyWords);
            		}else {
            			queryString = ("weibo_author:").concat(keyWords).concat(" AND weibo_emotion:negative").concat(" AND weibo_time:").concat("["+ sdf.format(pastYearDate) + " TO " + sdf.format(curTime) + "]").concat(" OR id:").concat(keyWords);
            		}
            	}
            }
        }else {
        	queryString = ("weibo_content:").concat(keyWords).concat(" OR id:").concat(keyWords);
        }
        if (start < 1){
            start = 1;
        }
        start = (start - 1)*rows;
        query.setQuery(queryString);
        query.addFilterQuery("weibo_lang:".concat(langType));
        query.setStart(start);
        query.setRows(rows);
        query.setFields(fileds);

        try {
            response = server.query(query);
            for (Iterator<SolrDocument> docIter = response.getResults().iterator(); docIter.hasNext();
                    ) {
                SolrDocument solrDoc = docIter.next();
                SolrWeiboDocResEntity docRes = new SolrWeiboDocResEntity();
                docRes.setWeibo_id(solrDoc.getFieldValue(SolrFields.WEIBO_ID.getName()).toString());
                docRes.setWeibo_author(solrDoc.getFieldValue(SolrFields.WEIBO_AUTHOR.getName()).toString());
                if(method){
					docRes.setWeibo_content(solrDoc.getFieldValue(SolrFields.WEIBO_CONTENT.getName()).toString().replace(keyWords, "<span id=\"add\" style=\"color:red\">"+keyWords+"</span>"));
				}else{
					docRes.setWeibo_content(solrDoc.getFieldValue(SolrFields.WEIBO_CONTENT.getName()).toString());
				}
                docRes.setWeibo_time(solrDoc.getFieldValue(SolrFields.WEIBO_TIME.getName()).toString());
                docRes.setWeibo_emotion(solrDoc.getFieldValue(SolrFields.WEIBO_EMOTION.getName()).toString());
                docRes.setWeibo_lang(solrDoc.getFieldValue(SolrFields.WEIBO_LANG.getName()).toString());
                docRes.setWeibo_likes(Integer.parseInt(solrDoc.getFieldValue(SolrFields.WEIBO_LIKES.getName()).toString()));
                docRes.setWeibo_comments(Integer.parseInt(solrDoc.getFieldValue(SolrFields.WEIBO_COMMENTS.getName()).toString()));
                docRes.setWeibo_forwards(Integer.parseInt(solrDoc.getFieldValue(SolrFields.WEIBO_FORWARDS.getName()).toString()));
                docRes.setHeat(Integer.parseInt(solrDoc.getFieldValue(SolrFields.HEAT.getName()).toString()));
                Date time = (Date) solrDoc.getFieldValue(SolrFields.WEIBO_TIME.getName());
                Calendar ca = Calendar.getInstance();
                ca.setTime(time);
                ca.add(Calendar.HOUR_OF_DAY, -8);
                String weibo_time = DateFormatUtils.format(ca.getTime(),"yyyy-MM-dd");
                docRes.setWeibo_time(weibo_time);
                docResList.add(docRes);
            }
        } catch (SolrServerException e) {
            e.printStackTrace();
        }
        return docResList;
    }
    
    
    /**
     * get top ten by time
     * @param langType
     * @param content_time
     * @return 
     */
    public List<SolrWeiboDocResEntity> queryTopTen(String langType, String content_time){
    	
    	List<SolrWeiboDocResEntity> docResList = new ArrayList<SolrWeiboDocResEntity>();
        Date curTime = new Date();
		Calendar calWeek = Calendar.getInstance();
		calWeek.add(Calendar.WEEK_OF_MONTH, -1);
		Date pastWeekDate = calWeek.getTime();
		Calendar calMonth = Calendar.getInstance();
		calMonth.add(Calendar.MONTH, -1);
		Date pastMonthDate = calMonth.getTime();
		Calendar  calYear = Calendar.getInstance();
		calYear.add(Calendar.YEAR,-1);
		Date pastYearDate = calYear.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd'T'HH:mm:ss'Z'");
        String[] fileds = {"id", "weibo_content", "weibo_author", "weibo_emotion","weibo_time","weibo_lang","weibo_likes","weibo_comments","weibo_forwards","heat"};
        String queryString = "";
        if(langType != null &&  content_time != null) {
        	if(content_time.equals("time-all")) {
        		queryString = ("weibo_lang:").concat(langType);
    		}else if(content_time.equals("time-week")) {
    			queryString = ("weibo_lang:").concat(langType).concat(" AND weibo_time:").concat("["+ sdf.format(pastWeekDate) + " TO " + sdf.format(curTime) + "]");
    		} else if(content_time.equals("time-month")){
    			queryString = ("weibo_lang:").concat(langType).concat(" AND weibo_time:").concat("["+ sdf.format(pastMonthDate) + " TO " + sdf.format(curTime) + "]");
    		}else {
    			queryString = ("weibo_lang:").concat(langType).concat(" AND weibo_time:").concat("["+ sdf.format(pastYearDate) + " TO " + sdf.format(curTime) + "]");
    		}
        }
        query.setQuery(queryString);
        query.addFilterQuery("weibo_lang:".concat(langType));
        query.setFields(fileds);
        query.setSort("heat",SolrQuery.ORDER.desc);
        try {
            response = server.query(query);
            int i = 0;
            for (Iterator<SolrDocument> docIter = response.getResults().iterator();i < 10 && docIter.hasNext();i++) {
                SolrDocument solrDoc = docIter.next();
                SolrWeiboDocResEntity docRes = new SolrWeiboDocResEntity();
                docRes.setWeibo_id(solrDoc.getFieldValue(SolrFields.WEIBO_ID.getName()).toString());
                docRes.setWeibo_author(solrDoc.getFieldValue(SolrFields.WEIBO_AUTHOR.getName()).toString());
            	docRes.setWeibo_content(solrDoc.getFieldValue(SolrFields.WEIBO_CONTENT.getName()).toString());
                docRes.setWeibo_time(solrDoc.getFieldValue(SolrFields.WEIBO_TIME.getName()).toString());
                docRes.setWeibo_emotion(solrDoc.getFieldValue(SolrFields.WEIBO_EMOTION.getName()).toString());
                docRes.setWeibo_lang(solrDoc.getFieldValue(SolrFields.WEIBO_LANG.getName()).toString());
                docRes.setWeibo_likes(Integer.parseInt(solrDoc.getFieldValue(SolrFields.WEIBO_LIKES.getName()).toString()));
                docRes.setWeibo_comments(Integer.parseInt(solrDoc.getFieldValue(SolrFields.WEIBO_COMMENTS.getName()).toString()));
                docRes.setWeibo_forwards(Integer.parseInt(solrDoc.getFieldValue(SolrFields.WEIBO_FORWARDS.getName()).toString()));
                docRes.setHeat(Integer.parseInt(solrDoc.getFieldValue(SolrFields.HEAT.getName()).toString()));
                Date time = (Date) solrDoc.getFieldValue(SolrFields.WEIBO_TIME.getName());
                Calendar ca = Calendar.getInstance();
                ca.setTime(time);
                ca.add(Calendar.HOUR_OF_DAY, -8);
                String weibo_time = DateFormatUtils.format(ca.getTime(),"yyyy-MM-dd");
                docRes.setWeibo_time(weibo_time);
                docResList.add(docRes);
            }
        } catch (SolrServerException e) {
            e.printStackTrace();
        }
        return docResList;
    }
    
    public long queryNum(String keyWords, String blogger,String langType, String conetent_emotion, String content_author, String content_time, int start, int rows_num,boolean method){
        long num=0;
    	int rows = rows_num;
        if (rows < 1){
            rows = 100000;
        }
        Date curTime = new Date();
		Calendar calWeek = Calendar.getInstance();
		calWeek.add(Calendar.WEEK_OF_MONTH, -1);
		Date pastWeekDate = calWeek.getTime();
		Calendar calMonth = Calendar.getInstance();
		calMonth.add(Calendar.MONTH, -1);
		Date pastMonthDate = calMonth.getTime();
		Calendar  calYear = Calendar.getInstance();
		calYear.add(Calendar.YEAR,-1);
		Date pastYearDate = calYear.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd'T'HH:mm:ss'Z'");
        String[] fileds = {"id", "weibo_content", "weibo_author", "weibo_emotion","weibo_time","weibo_lang","weibo_likes","weibo_comments","weibo_forwards","heat"};
        String queryString = "";
        if(content_author != null && conetent_emotion != null && content_time != null) {
            if(content_author.equals("author-all")) {
            	if(conetent_emotion.equals("emotion-all")){
            		if(content_time.equals("time-all")) {
            			queryString = ("weibo_content:").concat(keyWords).concat(" OR id:").concat(keyWords);
            		}else if(content_time.equals("time-week")) {
            			queryString = ("weibo_content:").concat(keyWords).concat(" AND weibo_time:").concat("["+ sdf.format(pastWeekDate) + " TO " + sdf.format(curTime) + "]").concat(" OR id:").concat(keyWords);
            		} else if(content_time.equals("time-month")){
            			queryString = ("weibo_content:").concat(keyWords).concat(" AND weibo_time:").concat("["+ sdf.format(pastMonthDate) + " TO " + sdf.format(curTime) + "]").concat(" OR id:").concat(keyWords);
            		}else {
            			queryString = ("weibo_content:").concat(keyWords).concat(" AND weibo_time:").concat("["+ sdf.format(pastYearDate) + " TO " + sdf.format(curTime) + "]").concat(" OR id:").concat(keyWords);
            		}
            	} else if(conetent_emotion.equals("emotion-positive")) {
            		if(content_time.equals("time-all")) {
            			queryString = ("weibo_content:").concat(keyWords).concat(" AND weibo_emotion:positive").concat(" OR id:").concat(keyWords);
            		}else if(content_time.equals("time-week")) {
            			queryString = ("weibo_content:").concat(keyWords).concat(" AND weibo_emotion:positive").concat(" AND weibo_time:").concat("["+ sdf.format(pastWeekDate) + " TO " + sdf.format(curTime) + "]").concat(" OR id:").concat(keyWords);
            		} else if(content_time.equals("time-month")){
            			queryString = ("weibo_content:").concat(keyWords).concat(" AND weibo_emotion:positive").concat(" AND weibo_time:").concat("["+ sdf.format(pastMonthDate) + " TO " + sdf.format(curTime) + "]").concat(" OR id:").concat(keyWords);
            		}else {
            			queryString = ("weibo_content:").concat(keyWords).concat(" AND weibo_emotion:positive").concat(" AND weibo_time:").concat("["+ sdf.format(pastYearDate) + " TO " + sdf.format(curTime) + "]").concat(" OR id:").concat(keyWords);
            		}
            	} else {
            		if(content_time.equals("time-all")) {
            			queryString = ("weibo_content:").concat(keyWords).concat(" AND weibo_emotion:negative").concat(" OR id:").concat(keyWords);
            		}else if(content_time.equals("time-week")) {
            			queryString = ("weibo_content:").concat(keyWords).concat(" AND weibo_emotion:negative").concat(" AND weibo_time:").concat("["+ sdf.format(pastWeekDate) + " TO " + sdf.format(curTime) + "]").concat(" OR id:").concat(keyWords);
            		} else if(content_time.equals("time-month")){
            			queryString = ("weibo_content:").concat(keyWords).concat(" AND weibo_emotion:negative").concat(" AND weibo_time:").concat("["+ sdf.format(pastMonthDate) + " TO " + sdf.format(curTime) + "]").concat(" OR id:").concat(keyWords);
            		}else {
            			queryString = ("weibo_content:").concat(keyWords).concat(" AND weibo_emotion:negative").concat(" AND weibo_time:").concat("["+ sdf.format(pastYearDate) + " TO " + sdf.format(curTime) + "]").concat(" OR id:").concat(keyWords);
            		}
            	}
            }
            if(content_author.equals("author-single")) {
            	if(conetent_emotion.equals("emotion-all")){
            		if(content_time.equals("time-all")) {
            			queryString = ("weibo_author:").concat(keyWords).concat(" OR id:").concat(keyWords);
            		}else if(content_time.equals("time-week")) {
            			queryString = ("weibo_author:").concat(keyWords).concat(" AND weibo_time:").concat("["+ sdf.format(pastWeekDate) + " TO " + sdf.format(curTime) + "]").concat(" OR id:").concat(keyWords);
            		} else if(content_time.equals("time-month")){
            			queryString = ("weibo_author:").concat(keyWords).concat(" AND weibo_time:").concat("["+ sdf.format(pastMonthDate) + " TO " + sdf.format(curTime) + "]").concat(" OR id:").concat(keyWords);
            		}else {
            			queryString = ("weibo_author:").concat(keyWords).concat(" AND weibo_time:").concat("["+ sdf.format(pastYearDate) + " TO " + sdf.format(curTime) + "]").concat(" OR id:").concat(keyWords);
            		}
            	} else if(conetent_emotion.equals("emotion-positive")) {
            		if(content_time.equals("time-all")) {
            			queryString = ("weibo_author:").concat(keyWords).concat(" AND weibo_emotion:positive").concat(" OR id:").concat(keyWords);
            		}else if(content_time.equals("time-week")) {
            			queryString = ("weibo_author:").concat(keyWords).concat(" AND weibo_emotion:positive").concat(" AND weibo_time:").concat("["+ sdf.format(pastWeekDate) + " TO " + sdf.format(curTime) + "]").concat(" OR id:").concat(keyWords);
            		} else if(content_time.equals("time-month")){
            			queryString = ("weibo_author:").concat(keyWords).concat(" AND weibo_emotion:positive").concat(" AND weibo_time:").concat("["+ sdf.format(pastMonthDate) + " TO " + sdf.format(curTime) + "]").concat(" OR id:").concat(keyWords);
            		}else {
            			queryString = ("weibo_author:").concat(keyWords).concat(" AND weibo_emotion:positive").concat(" AND weibo_time:").concat("["+ sdf.format(pastYearDate) + " TO " + sdf.format(curTime) + "]").concat(" OR id:").concat(keyWords);
            		}
            	} else {
            		if(content_time.equals("time-all")) {
            			queryString = ("weibo_author:").concat(keyWords).concat(" AND weibo_emotion:negative").concat(" OR id:").concat(keyWords);
            		}else if(content_time.equals("time-week")) {
            			queryString = ("weibo_author:").concat(keyWords).concat(" AND weibo_emotion:negative").concat(" AND weibo_time:").concat("["+ sdf.format(pastWeekDate) + " TO " + sdf.format(curTime) + "]").concat(" OR id:").concat(keyWords);
            		} else if(content_time.equals("time-month")){
            			queryString = ("weibo_author:").concat(keyWords).concat(" AND weibo_emotion:negative").concat(" AND weibo_time:").concat("["+ sdf.format(pastMonthDate) + " TO " + sdf.format(curTime) + "]").concat(" OR id:").concat(keyWords);
            		}else {
            			queryString = ("weibo_author:").concat(keyWords).concat(" AND weibo_emotion:negative").concat(" AND weibo_time:").concat("["+ sdf.format(pastYearDate) + " TO " + sdf.format(curTime) + "]").concat(" OR id:").concat(keyWords);
            		}
            	}
            }
        }else {
        	queryString = ("weibo_content:").concat(keyWords).concat(" OR id:").concat(keyWords);
        }
        if (start < 1){
			start = 1;
		}
        start = (start - 1)*rows;
        query.setQuery(queryString);
        query.addFilterQuery("weibo_lang:".concat(langType));
        query.setStart(start);
        query.setRows(rows);
        query.setFields(fileds);
        try {
            response = server.query(query);
            num = response.getResults().getNumFound();
        } catch (SolrServerException e) {
            e.printStackTrace();
        }
        return num;
    }

    public String queryDate(String keyWords, String langType, String conetent_emotion, String content_author, String content_time, int start, int rows_num,boolean method){
    	String time_span="";
    	int rows = rows_num;
        if (rows < 1){
            rows = 100000;
        }
        Date curTime = new Date();
		Calendar calWeek = Calendar.getInstance();
		calWeek.add(Calendar.WEEK_OF_MONTH, -1);
		Date pastWeekDate = calWeek.getTime();
		Calendar calMonth = Calendar.getInstance();
		calMonth.add(Calendar.MONTH, -1);
		Date pastMonthDate = calMonth.getTime();
		Calendar  calYear = Calendar.getInstance();
		calYear.add(Calendar.YEAR,-1);
		Date pastYearDate = calYear.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd'T'HH:mm:ss'Z'");
        String[] fileds = {"id", "weibo_content", "weibo_author", "weibo_emotion","weibo_time","weibo_lang","weibo_likes","weibo_comments","weibo_forwards","heat"};
        String queryString = "";
        if(content_author != null && conetent_emotion != null && content_time != null) {
            if(content_author.equals("author-all")) {
            	if(conetent_emotion.equals("emotion-all")){
            		if(content_time.equals("time-all")) {
            			queryString = ("weibo_content:").concat(keyWords).concat(" OR id:").concat(keyWords);
            		}else if(content_time.equals("time-week")) {
            			queryString = ("weibo_content:").concat(keyWords).concat(" AND weibo_time:").concat("["+ sdf.format(pastWeekDate) + " TO " + sdf.format(curTime) + "]").concat(" OR id:").concat(keyWords);
            		} else if(content_time.equals("time-month")){
            			queryString = ("weibo_content:").concat(keyWords).concat(" AND weibo_time:").concat("["+ sdf.format(pastMonthDate) + " TO " + sdf.format(curTime) + "]").concat(" OR id:").concat(keyWords);
            		}else {
            			queryString = ("weibo_content:").concat(keyWords).concat(" AND weibo_time:").concat("["+ sdf.format(pastYearDate) + " TO " + sdf.format(curTime) + "]").concat(" OR id:").concat(keyWords);
            		}
            	} else if(conetent_emotion.equals("emotion-positive")) {
            		if(content_time.equals("time-all")) {
            			queryString = ("weibo_content:").concat(keyWords).concat(" AND weibo_emotion:positive").concat(" OR id:").concat(keyWords);
            		}else if(content_time.equals("time-week")) {
            			queryString = ("weibo_content:").concat(keyWords).concat(" AND weibo_emotion:positive").concat(" AND weibo_time:").concat("["+ sdf.format(pastWeekDate) + " TO " + sdf.format(curTime) + "]").concat(" OR id:").concat(keyWords);
            		} else if(content_time.equals("time-month")){
            			queryString = ("weibo_content:").concat(keyWords).concat(" AND weibo_emotion:positive").concat(" AND weibo_time:").concat("["+ sdf.format(pastMonthDate) + " TO " + sdf.format(curTime) + "]").concat(" OR id:").concat(keyWords);
            		}else {
            			queryString = ("weibo_content:").concat(keyWords).concat(" AND weibo_emotion:positive").concat(" AND weibo_time:").concat("["+ sdf.format(pastYearDate) + " TO " + sdf.format(curTime) + "]").concat(" OR id:").concat(keyWords);
            		}
            	} else {
            		if(content_time.equals("time-all")) {
            			queryString = ("weibo_content:").concat(keyWords).concat(" AND weibo_emotion:negative").concat(" OR id:").concat(keyWords);
            		}else if(content_time.equals("time-week")) {
            			queryString = ("weibo_content:").concat(keyWords).concat(" AND weibo_emotion:negative").concat(" AND weibo_time:").concat("["+ sdf.format(pastWeekDate) + " TO " + sdf.format(curTime) + "]").concat(" OR id:").concat(keyWords);
            		} else if(content_time.equals("time-month")){
            			queryString = ("weibo_content:").concat(keyWords).concat(" AND weibo_emotion:negative").concat(" AND weibo_time:").concat("["+ sdf.format(pastMonthDate) + " TO " + sdf.format(curTime) + "]").concat(" OR id:").concat(keyWords);
            		}else {
            			queryString = ("weibo_content:").concat(keyWords).concat(" AND weibo_emotion:negative").concat(" AND weibo_time:").concat("["+ sdf.format(pastYearDate) + " TO " + sdf.format(curTime) + "]").concat(" OR id:").concat(keyWords);
            		}
            	}
            }
            if(content_author.equals("author-single")) {
            	if(conetent_emotion.equals("emotion-all")){
            		if(content_time.equals("time-all")) {
            			queryString = ("weibo_author:").concat(keyWords).concat(" OR id:").concat(keyWords);
            		}else if(content_time.equals("time-week")) {
            			queryString = ("weibo_author:").concat(keyWords).concat(" AND weibo_time:").concat("["+ sdf.format(pastWeekDate) + " TO " + sdf.format(curTime) + "]").concat(" OR id:").concat(keyWords);
            		} else if(content_time.equals("time-month")){
            			queryString = ("weibo_author:").concat(keyWords).concat(" AND weibo_time:").concat("["+ sdf.format(pastMonthDate) + " TO " + sdf.format(curTime) + "]").concat(" OR id:").concat(keyWords);
            		}else {
            			queryString = ("weibo_author:").concat(keyWords).concat(" AND weibo_time:").concat("["+ sdf.format(pastYearDate) + " TO " + sdf.format(curTime) + "]").concat(" OR id:").concat(keyWords);
            		}
            	} else if(conetent_emotion.equals("emotion-positive")) {
            		if(content_time.equals("time-all")) {
            			queryString = ("weibo_author:").concat(keyWords).concat(" AND weibo_emotion:positive").concat(" OR id:").concat(keyWords);
            		}else if(content_time.equals("time-week")) {
            			queryString = ("weibo_author:").concat(keyWords).concat(" AND weibo_emotion:positive").concat(" AND weibo_time:").concat("["+ sdf.format(pastWeekDate) + " TO " + sdf.format(curTime) + "]").concat(" OR id:").concat(keyWords);
            		} else if(content_time.equals("time-month")){
            			queryString = ("weibo_author:").concat(keyWords).concat(" AND weibo_emotion:positive").concat(" AND weibo_time:").concat("["+ sdf.format(pastMonthDate) + " TO " + sdf.format(curTime) + "]").concat(" OR id:").concat(keyWords);
            		}else {
            			queryString = ("weibo_author:").concat(keyWords).concat(" AND weibo_emotion:positive").concat(" AND weibo_time:").concat("["+ sdf.format(pastYearDate) + " TO " + sdf.format(curTime) + "]").concat(" OR id:").concat(keyWords);
            		}
            	} else {
            		if(content_time.equals("time-all")) {
            			queryString = ("weibo_author:").concat(keyWords).concat(" AND weibo_emotion:negative").concat(" OR id:").concat(keyWords);
            		}else if(content_time.equals("time-week")) {
            			queryString = ("weibo_author:").concat(keyWords).concat(" AND weibo_emotion:negative").concat(" AND weibo_time:").concat("["+ sdf.format(pastWeekDate) + " TO " + sdf.format(curTime) + "]").concat(" OR id:").concat(keyWords);
            		} else if(content_time.equals("time-month")){
            			queryString = ("weibo_author:").concat(keyWords).concat(" AND weibo_emotion:negative").concat(" AND weibo_time:").concat("["+ sdf.format(pastMonthDate) + " TO " + sdf.format(curTime) + "]").concat(" OR id:").concat(keyWords);
            		}else {
            			queryString = ("weibo_author:").concat(keyWords).concat(" AND weibo_emotion:negative").concat(" AND weibo_time:").concat("["+ sdf.format(pastYearDate) + " TO " + sdf.format(curTime) + "]").concat(" OR id:").concat(keyWords);
            		}
            	}
            }
        }else {
        	queryString = ("weibo_content:").concat(keyWords).concat(" OR id:").concat(keyWords);
        }
        if (start < 1){
			start = 1;
		}
        start = (start - 1)*rows;
        query.setQuery(queryString);
        query.addFilterQuery("weibo_lang:".concat(langType));
        query.setStart(start);
        query.setRows(rows);
        query.setSort("weibo_time",SolrQuery.ORDER.asc);
        query.setFields(fileds);
        try {
            response = server.query(query);
            SolrDocument doc1 = response.getResults().get(0);
            Date time1 = (Date) doc1.getFieldValue(SolrFields.WEIBO_TIME.getName());
            SolrDocument doc2 = response.getResults().get((int) response.getResults().getNumFound()-1);
            Date time2 = (Date) doc2.getFieldValue(SolrFields.WEIBO_TIME.getName());
            Calendar ca = Calendar.getInstance();
            ca.setTime(time1);
            ca.add(Calendar.HOUR_OF_DAY, -8);
            String earliest_time = DateFormatUtils.format(ca.getTime(),"yyyy-MM-dd");
            ca.clear();
            ca.setTime(time2);
            ca.add(Calendar.HOUR_OF_DAY, -8);
            String latest_time = DateFormatUtils.format(ca.getTime(),"yyyy-MM-dd");
            time_span = earliest_time+";"+latest_time;
        } catch (SolrServerException e) {
            e.printStackTrace();
        }
        return time_span;
    }
}
