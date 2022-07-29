package cn.stylefeng.guns.modular.mwyq.model.result;

import lombok.Data;
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
public class TopicResult implements Serializable {

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

    private String summarize;

    private String dataTime;
    private Integer count;

}
