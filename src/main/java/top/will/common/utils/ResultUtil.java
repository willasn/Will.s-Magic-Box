package top.will.common.utils;

import com.power.common.constants.BaseErrorCode;
import com.power.common.interfaces.IMessage;
import com.power.common.model.CommonResult;

/**
 * 封装 CommonResult,解决泛型报警
 * @author panhao
 */
public class ResultUtil {

    private ResultUtil() {
        throw new IllegalStateException("Illegal build class");
    }

    public static CommonResult<String> fail(IMessage msg) {
        CommonResult<String> res = new CommonResult<>(false, msg.getMessage(), null);
        res.setCode(msg.getCode());
        return res;
    }

    public static <T> CommonResult<T> fail(IMessage msg, T t) {
        CommonResult<T> res = new CommonResult<>(false, msg.getMessage(), t);
        res.setCode(msg.getCode());
        return res;
    }

    public static CommonResult<String> fail(String code, String msg) {
        CommonResult<String> res = new CommonResult<>(false, msg, null);
        res.setCode(code);
        return res;
    }

    public static <T> CommonResult<T> fail(String code, String msg, T t){
        CommonResult<T> res = new CommonResult<>(false, msg,t);
        res.setCode(code);
        return res;
    }

    public static <T> CommonResult<T> ok(T t) {
        CommonResult<T> res = CommonResult.ok(BaseErrorCode.Common.SUCCESS);
        return res.setResult(t);
    }
}
