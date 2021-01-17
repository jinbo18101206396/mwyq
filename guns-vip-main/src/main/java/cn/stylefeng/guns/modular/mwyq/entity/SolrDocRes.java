package cn.stylefeng.guns.modular.mwyq.entity;

public class SolrDocRes {
	private String titleLading;
	private String updateTime;
	private String newsContent;
	private String newsTitle;
	private String newsTime;
	private String newsId;
	private String crawlSource;
	private String newsUrl;
	private String langTypeString;

	public String getTitleLading() {
		return titleLading;
	}

	public void setTitleLading(String titleLading) {
		this.titleLading = titleLading;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getNewsContent() {
		return newsContent;
	}

	public void setNewsContent(String newsContent) {
		this.newsContent = newsContent;
	}

	public String getNewsTitle() {
		return newsTitle;
	}

	public void setNewsTitle(String newsTitle) {
		this.newsTitle = newsTitle;
	}

	public String getNewsTime() {
		return newsTime;
	}

	public void setNewsTime(String newsTime) {
		this.newsTime = newsTime;
	}

	public String getNewsId() {
		return newsId;
	}

	public void setNewsId(String newsId) {
		this.newsId = newsId;
	}

	public String getCrawlSource() {
		return crawlSource;
	}

	public void setCrawlSource(String crawlSource) {
		this.crawlSource = crawlSource;
	}

//    url
	public String getNewsUrl() {
		return newsUrl;
	}

	public void setNewsUrl(String newsUrl) {
		this.newsUrl = newsUrl;
	}

//  语言类型
	public String getLangType() {
		return langTypeString;
	}

	public void setLangType(String langTypeString) {
		this.langTypeString = langTypeString;
	}

}
