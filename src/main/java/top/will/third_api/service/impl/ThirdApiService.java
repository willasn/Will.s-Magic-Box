package top.will.third_api.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.power.common.constants.BaseErrorCode;
import com.power.common.model.CommonResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import top.will.common.utils.ResultUtil;
import top.will.third_api.pojo.GaoDeGeocode;
import top.will.third_api.pojo.GaoDeWeatherForecastVo;
import top.will.third_api.service.IThirdApiService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static top.will.common.constant.StatusCode.GAODE_API_ADCODE_UNAVAILABLE;

/**
 * @author panhao
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class ThirdApiService implements IThirdApiService {

    private final RestTemplate restTemplate;

    public static final String GEOCODES = "geocodes";

    /**
     * 获取高德天气预报
     */
    @Override
    public CommonResult<List<GaoDeWeatherForecastVo>> getGaoDeWeatherForecast(String projectInfoUuid) {
        // 前置验证处理
        // do something

        String key="b0a89191ca42d9888030df9d3ad326f8"; //请求服务权限标识
        String extensions="all"; //base:返回实况天气 all:返回预报天气
        String output="JSON"; // 可选值：JSON,XML
        String address="ur address";
        String batch="false";
        // adcode查询
        String addrBody = restTemplate.getForEntity("https://restapi.amap.com/v3/geocode/geo?"
                + "key="+key
                +"&address="+address
                +"&batch="+batch
                +"&output="+output, String.class).getBody();
        JSONObject addrJsonObject = JSON.parseObject(addrBody);
        if (addrJsonObject == null) {
            log.warn("高德地址反编码API,服务不可用~");
            return ResultUtil.fail(GAODE_API_ADCODE_UNAVAILABLE, Collections.emptyList());
        }
        if (addrJsonObject.get("status").equals("0")){
            log.warn("高德地址反编码API,服务不可用~");
            return ResultUtil.fail(GAODE_API_ADCODE_UNAVAILABLE, Collections.emptyList());
        }
        String adcode = (String) addrJsonObject.getJSONArray(GEOCODES).getJSONObject(0).get("adcode");
        String targetCity = (String) addrJsonObject.getJSONArray(GEOCODES).getJSONObject(0).get("city");
        String targetDistrict = (String)addrJsonObject.getJSONArray(GEOCODES).getJSONObject(0).get("district");
        log.info("开始查询{}-{}",targetCity,targetDistrict);
        // 天气查询
        String weatherBody = restTemplate.getForEntity("https://restapi.amap.com/v3/weather/weatherInfo?"
                + "key="+key
                +"&city="+adcode
                +"&extensions="+extensions
                +"&output="+output, String.class).getBody();
        JSONObject jsonObject = JSON.parseObject(weatherBody);
        if (jsonObject == null) {
            log.warn("高德天气API,服务不可用~");
            CommonResult<List<GaoDeWeatherForecastVo>> commonResult = new CommonResult<>(false, "高德地址反编码API,服务不可用~~~", null);
            commonResult.setCode("-1");
            return commonResult;
        }
        if (jsonObject.get("status").equals("0")){
            log.warn("高德天气API,服务不可用~");
            CommonResult<List<GaoDeWeatherForecastVo>> commonResult = new CommonResult<>(false, "高德地址反编码API,服务不可用~~~", null);
            commonResult.setCode("-1");
            return commonResult;
        }
        JSONArray jsonArray = jsonObject.getJSONArray("forecasts").getJSONObject(0).getJSONArray("casts");
        List<GaoDeWeatherForecastVo> list = new ArrayList<>(4);
        for(int i = 0; i < jsonArray.size(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);

            String week = "周" + obj.get("week");
            String dayweather = (String) obj.get("dayweather");
            String temp = obj.get("daytemp")+"℃~"+ obj.get("nighttemp")+"℃";
            String daywind = (String) obj.get("daywind");
            String windPower = obj.get("daypower")+"级";

            list.add(i,new GaoDeWeatherForecastVo().setIndex(i)
                    .setWeek(week)
                    .setWeather(dayweather)
                    .setTemperatureRange(temp)
                    .setWindDirection(daywind)
                    .setWindPower(windPower));
        }
        list.get(0).setWeek("今天");
        CommonResult<List<GaoDeWeatherForecastVo>> ok = CommonResult.ok(BaseErrorCode.Common.SUCCESS);
        return ok.setResult(list);
    }

    /**
     * 高德获取逆地址编码
     */
    @Override
    public CommonResult<GaoDeGeocode> getGaoDeGeocode(String projectInfoUuid) {
        // 请求参数:
        // 包含地址信息
        String key="b0a89191ca42d9888030df9d3ad326f8"; //请求服务权限标识
        String output="JSON"; // 可选值：JSON,XML
        String address= "南京雨花台";
        String batch="false";
        // adcode查询
        String addrBody = restTemplate.getForEntity("https://restapi.amap.com/v3/geocode/geo?"
                + "key="+key
                +"&address="+address
                +"&batch="+batch
                +"&output="+output, String.class).getBody();
        JSONObject addrJsonObject = JSON.parseObject(addrBody);
        if (addrJsonObject.get("status").equals("0")){
            log.warn("高德地址反编码API,服务不可用~");
            return ResultUtil.fail(GAODE_API_ADCODE_UNAVAILABLE,null);
        }
        JSONObject jsonObject = addrJsonObject.getJSONArray(GEOCODES).getJSONObject(0);
        GaoDeGeocode gaoDeGeocode = JSONObject.toJavaObject(jsonObject, GaoDeGeocode.class);

//        String formattedAddress = ;
//        String country = ;
//        String province = ;
//        String citycode = ;
//        String city = ;
//        String location = ;
//        String level = ;
//        String adcode = (String) addrJsonObject.getJSONArray(GEOCODES).getJSONObject(0).get("adcode");
//        String targetCity = (String) addrJsonObject.getJSONArray(GEOCODES).getJSONObject(0).get("city");
//        String district = (String)addrJsonObject.getJSONArray(GEOCODES).getJSONObject(0).get("district");
//        GaoDeGeocode gaoDeGeocode = new GaoDeGeocode()
//                .setFormattedAddress()
//                .setCountry()
//                .setProvince()
//                .setCityCode()
//                .setCity()
//                .setDistrict()
//                .setAdcode()
//                .setLocation()
//                .setLevel();
        System.out.println(gaoDeGeocode);
        return ResultUtil.ok(gaoDeGeocode);
    }
}
