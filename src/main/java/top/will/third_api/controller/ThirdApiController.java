package top.will.third_api.controller;

import com.power.common.model.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.will.third_api.pojo.GaoDeGeocode;
import top.will.third_api.pojo.GaoDeWeatherForecastVo;
import top.will.third_api.service.IThirdApiService;

import java.util.List;

/**
 * @author panhao
 */
@RestController
@RequiredArgsConstructor
@Api(tags = "三方接口服务")
@RequestMapping("/thirdApi")
public class ThirdApiController {

    private final IThirdApiService iThirdApiService;


    @ApiOperation(value = "高德获取逆地址编码")
    @GetMapping("/getGaoDeGeocode")
    public CommonResult<GaoDeGeocode> getGaoDeGeocode(@RequestParam(value = "projectInfoUuid") String projectInfoUuid) {
        return iThirdApiService.getGaoDeGeocode(projectInfoUuid);
    }

    @ApiOperation(value = "高德获取天气预报")
    @GetMapping("/getGaoDeWeatherForecast")
    public CommonResult<List<GaoDeWeatherForecastVo>> getGaoDeWeatherForecast(@RequestParam(value = "projectInfoUuid") String projectInfoUuid) {
        return iThirdApiService.getGaoDeWeatherForecast(projectInfoUuid);
    }
}

