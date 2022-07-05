package top.will.third_api.service;

import com.power.common.model.CommonResult;
import top.will.third_api.pojo.GaoDeGeocode;
import top.will.third_api.pojo.GaoDeWeatherForecastVo;

import java.util.List;

/**
 * @author panhao
 */
public interface IThirdApiService {

    /**
     * 获取高德天气预报
     */
    CommonResult<List<GaoDeWeatherForecastVo>> getGaoDeWeatherForecast(String projectInfoUuid);

    /**
     * 高德获取逆地址编码
     */
    CommonResult<GaoDeGeocode> getGaoDeGeocode(String projectInfoUuid);
}
