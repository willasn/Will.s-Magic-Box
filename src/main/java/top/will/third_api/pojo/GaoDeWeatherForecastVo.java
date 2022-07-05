package top.will.third_api.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author panhao
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ApiModel("天气预报")
public class GaoDeWeatherForecastVo {
    @ApiModelProperty("预测天数编号")
    private Integer index;

    @ApiModelProperty("周几")
    private String week;

    @ApiModelProperty("天气情况 情-阴等")
    private String weather;

    @ApiModelProperty("温度区间")
    private String temperatureRange;

    @ApiModelProperty("风向")
    private String windDirection;

    @ApiModelProperty("风力")
    private String windPower;
}
