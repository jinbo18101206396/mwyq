package cn.stylefeng.guns.modular.mwyq.model.params;

import lombok.Data;
import cn.stylefeng.roses.kernel.model.validator.BaseValidatingParam;
import java.util.Date;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author jinbo
 * @since 2020-07-01
 */
@Data
public class TopicParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    private String topicId;

    private String topwords;

    private String topicLabelCandidate;

    private String topicName;

    private String clusterTopicName;

    private String topicLabel;

    private Date producedtime;

    private String langType;

    private String keywords;

    private String topicDir;

    private Double topicIndex;

    private Double newsCount;

    private Date newsTime;

    private String newsContent;

    private String timeLimit;

    private String beginTime;

    private String endTime;

    private Integer isSensitive;

    private Integer sensitiveCategory;

    private String websitename;

    private String summarize;

    @Override
    public String checkParam() {
        return null;
    }

}
