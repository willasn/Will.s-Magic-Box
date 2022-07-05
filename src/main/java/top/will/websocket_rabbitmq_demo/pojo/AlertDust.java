package top.will.websocket_rabbitmq_demo.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author panhao
 */
@Data
@TableName("alert_dust")
public class AlertDust {

  private long id;
  private String deviceId;
  private String projectInfoUuid;
  private long alertDeviceType;
  private long alertLevel;
  private String alertInfo;
  private long alertType;
  private java.sql.Timestamp alertTime;
  private long state;
  private String releasePersonUuid;
  private java.sql.Timestamp releaseTime;
  private java.sql.Timestamp createTime;
  private long deleteMark;
  private String remark;
  private String alertUuid;
  private String releaseResult;
  private long isAlarm;

}
