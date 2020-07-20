package cn.stylefeng.guns.modular.mwyq.model.params;

import lombok.Data;
import cn.stylefeng.roses.kernel.model.validator.BaseValidatingParam;
import java.util.Date;
import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author jinbo
 * @since 2020-06-14
 */
@Data
public class NewsParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    private Integer newsId;

    private List<Integer> ids;

    private String newsToken;

    private Integer wordsNum;

    private String generatedKeywords;

    private String newsCategory;

    private String newsSource;

    private String newsTitle;

    private String titleToken;

    private String titleLading;

    private String inbornKeywords;

    private Date newsTime;

    private String newsUrl;

    private String newsAuthor;

    private String newsContent;

    private String keyWords;

    private String contentLading;

    private String langType;

    private String crawlSource;

    private Date crawlTime;

    private String newsPictures;

    private String newsVideo;

    private String newsEncode;

    private String newsTendency;

    private String isSeged;

    private Date updateTime;

    private String hasVideo;

    private Integer isSensitive;

    private String sensitiveWords;

    private Integer sensitiveCategory;

    private String websitename;

    private String timeLimit;

    private String beginTime;

    private String endTime;

    @Override
    public String checkParam() {
        return null;
    }

}
