package top.will.common.handler;

import com.power.common.model.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.NoHandlerFoundException;
import top.will.common.constant.StatusCode;
import top.will.common.utils.ResultUtil;

/**
 * @author panhao
 */
@RestControllerAdvice
@Slf4j
public class RestExceptionHandler {

    /**
     * 处理参数验证异常
     */
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CommonResult<String> illegalParamsExceptionHandler(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        FieldError fieldError = bindingResult.getFieldError();
        if(fieldError == null){
            return ResultUtil.fail(StatusCode.UNKNOWN_ERROR);
        }
        log.error("request params invalid: {}", fieldError.getField());

        return processBindingError(fieldError);
    }


    /**
     * 处理参数验证异常
     */
    @ExceptionHandler(value = {BindException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CommonResult<String> bindExceptionHandler(BindException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        FieldError fieldError = bindingResult.getFieldError();
        if(fieldError == null){
            return ResultUtil.fail(StatusCode.UNKNOWN_ERROR);
        }
        log.error("request params invalid: {}", fieldError.getDefaultMessage());

        return processBindingError(fieldError);
    }

    /**
     * 处理参数转换失败异常
     */
    @ExceptionHandler(value = {MethodArgumentTypeMismatchException.class,HttpMessageNotReadableException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CommonResult<String> methodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        Class<?> requiredType = ex.getRequiredType();
        if(requiredType == null){
            return ResultUtil.fail(StatusCode.UNKNOWN_ERROR);
        }
        String error = String.format("The parameter '%s' should be of type '%s'", ex.getName(),
                requiredType.getSimpleName());
        return ResultUtil.fail("400", error);
    }


    /**
     * 处理资源找不到异常（404）
     */
    @ExceptionHandler(value = {NoHandlerFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public CommonResult<String> noHandlerFoundException(NoHandlerFoundException ex) {
        log.error("Error code 404: {}", ex.getRequestURL());
        return ResultUtil.fail("404", "Resource Not Found");
    }

    /**
     * 处理不支持当前媒体类型异常（415）
     */
    @ExceptionHandler(value = {HttpMediaTypeNotSupportedException.class})
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    public CommonResult<String> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex) {
        StringBuilder builder = new StringBuilder();
        builder.append(ex.getContentType());
        builder.append(" media type is not supported. Supported media types are ");
        ex.getSupportedMediaTypes().forEach(t -> builder.append(t).append(","));
        return ResultUtil.fail("415", builder.toString());
    }

    /**
     * 处理方法不被允许异常（405）
     */
    @ExceptionHandler(value = {HttpRequestMethodNotSupportedException.class})
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public CommonResult<String> methodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        log.error("Error code 405: {}", ex.getMessage());
        return ResultUtil.fail("405", ex.getMessage());
    }

    /**
     * 文件过大（413）
     */
    @ExceptionHandler(value = {MaxUploadSizeExceededException.class})
    @ResponseStatus(HttpStatus.PAYLOAD_TOO_LARGE)
    public CommonResult<String> maxUploadSizeExceededException(MaxUploadSizeExceededException ex) {
        log.error("Error code 413: {}", ex.getMessage());
        return ResultUtil.fail("413", ex.getMessage());
    }

    /**
     * 处理其他异常（错误码统一为500）
     */
    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(HttpStatus.OK)
    public CommonResult<String> unknownException(Exception ex) {
        log.error("Error code 500：{}", ex.getMessage());
        return ResultUtil.fail("500", ex.getMessage());
    }

    /**
     * 处理参数验证异常（转换成对应的CommonResult）
     */
    private CommonResult<String> processBindingError(FieldError fieldError) {
        String code = fieldError.getCode();
        if(code == null){
            return ResultUtil.fail(StatusCode.UNKNOWN_ERROR);
        }
        log.debug("validator error code: {}", code);
        switch (code) {
            case "NotEmpty":
            case "NotBlank":
            case "NotNull":
            case "Pattern":
            case "Future":
            case "Length":
            case "Range":
            case "Past":
            case "Digits":
            case "Size":
            case "DecimalMin":
            case "Max":
            case "Min":
            case "Email":
            case "DecimalMax":
                return ResultUtil.fail(StatusCode.PARAM_EMPTY.getCode(),
                        fieldError.getDefaultMessage());
            default:
                return ResultUtil.fail(StatusCode.UNKNOWN_ERROR);
        }
    }
}
