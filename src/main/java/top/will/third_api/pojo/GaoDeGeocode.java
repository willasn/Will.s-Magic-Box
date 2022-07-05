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
@ApiModel("高德逆地址查询")
public class GaoDeGeocode {

    @ApiModelProperty(":江苏省常州市钟楼区邹新镇")
    private Integer formattedAddress;

    @ApiModelProperty("中国")
    private String country;

    @ApiModelProperty("江苏省")
    private String province;

    @ApiModelProperty("0519")
    private String cityCode;

    @ApiModelProperty("常州市")
    private String city;

    @ApiModelProperty("钟楼区")
    private String district;

    @ApiModelProperty("320404")
    private String adcode;

    @ApiModelProperty("119.843048,31.779547")
    private String location;

    @ApiModelProperty("乡镇")
    private String level;

}
