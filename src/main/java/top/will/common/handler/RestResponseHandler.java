package top.will.common.handler;

import com.alibaba.fastjson.JSON;
import com.power.common.constants.BaseErrorCode;
import com.power.common.model.CommonResult;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * Desc: 全局返回数据CommonResult包装
 * @author panhao
 */
@RestControllerAdvice({"top.will"})
public class RestResponseHandler implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(@Nullable MethodParameter methodParameter,
                            @Nullable Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body,
                                  @Nullable MethodParameter methodParameter,
                                  @Nullable MediaType mediaType,
                                  @Nullable Class<? extends HttpMessageConverter<?>> aClass,
                                  @Nullable ServerHttpRequest serverHttpRequest,
                                  @Nullable ServerHttpResponse serverHttpResponse) {
        // 如果返回值已经是 CommonResult，则不做处理直接返回
        if (body instanceof CommonResult){
            return body;
        }
        // 否则的话封装成 CommonResult 再返回
        CommonResult<Object> commonResult = CommonResult.ok(BaseErrorCode.Common.SUCCESS);
        commonResult.setResult(body);
        // 如果controller层中返回的类型是String，我们还需要特殊处理下（将CommonResult对象转回String）

        if (body instanceof String) {
            // 这里我使用 FastJSON 进行转换
            return JSON.toJSONString(commonResult);
        }
        return commonResult;
    }
}
