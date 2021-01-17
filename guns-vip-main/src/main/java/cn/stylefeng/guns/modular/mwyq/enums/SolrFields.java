package cn.stylefeng.guns.modular.mwyq.enums;

import lombok.Getter;

@Getter
public enum SolrFields {
        NEWS_TITLE("news_title"),
        NEWS_ID("news_id"),
        NEWS_CONTENT("news_content"),
        NEWS_TIME("news_time"),
        NEWS_SOURCE("crawl_source"),
        NEWS_URL("news_url"),

        WEIBO_ID("id"),
        WEIBO_CONTENT("weibo_content"),
        WEIBO_TIME("weibo_time"),
        WEIBO_EMOTION("weibo_emotion"),
        WEIBO_AUTHOR("weibo_author"),
        WEIBO_LANG("weibo_lang"),
        WEIBO_LIKES("weibo_likes"),
        WEIBO_COMMENTS("weibo_comments"),
        WEIBO_FORWARDS("weibo_forwards"),
        HEAT("heat");
        private String name;
        SolrFields(String name){
            this.name = name;
        }
        public String getName() {
            return this.name;
        }
}
