package top.will.common.constant;

import com.power.common.interfaces.IMessage;

/**
 * Desc: 全局业务返回状态
 * @author panhao
 */
public enum StatusCode implements IMessage {

    /**
     * 系统全局错误枚举
     */
    SUCCESS("0000", "成功"),
    PARAM_EMPTY("1001", "参数为空"),
    PARAM_ERROR("1002", "参数格式错误"),
    LOGIN_ERROR("1003", "用户名或密码错误"),
    ACCESS_ERROR("1004", "权限不足"),
    REMOTE_ERROR("1005", "远程调用失败"),
    REP_ERROR("1006", "重复操作"),
    VALID_ERROR("1007", "验证失败"),
    REPETITION_REGISTER_ERROR("1008", "账户已存在"),
    SEND_SMS_ERROR("1009", "短信验证码发送失败，请重试"),
    AUTH_CODE_ERROR("1010", "短信验证码错误"),
    UPLOAD_FILE_ERROR("1011", "上传文件出错"),
    FILE_TYPE_ERROR("1012", "文件格式错误"),
    FILE_ERROR("1013", "文件出错"),
    INSERT_ERROR("1014", "新增失败"),
    USER_WARN("0000", "项目绑定成功"),
    UPDATE_ERROR("1016", "修改失败"),
    DELETE_ERROR("1017", "删除失败"),
    ADD_ACCOUNT_ROLE_ERROR("1018", "账户角色出错"),
    ADD_ACCOUNT_PROJECT_ERROR("1019", "账户已绑定此项目"),
    REUSE_ERROR("1020", "请勿重复添加"),
    PROJECT_REGISTER_ERROR("1021", "项目已存在"),
    MANUFACTURER_REGISTER_ERROR("1022", "厂商已存在"),
    UNLOAD_AERIAL_VIEW("1023", "未上传鸟瞰图"),
    DRIVER_ALREADY_BIND("1024", "该特种人员已绑定其他设备"),
    IDCARD_ERROR_MSG("1025", "身份证号码输入有误"),
    UNKNOWN_ERROR("9999", "系统繁忙，请稍后再试...."),


    /**
     * 1xxx 业务全局错误  <BR/>
     * 2xxx-6xxx 个人模块错误 <BR/>
     * 7xxx 系统程序内部错误 <BR/>
     * 8xxx 三方API错误 <BR/>
     * 9xxx 其他错误 <BR/>
     * 0000 成功 <BR/>
     * 9999 默认错误,未知错误 <BR/>
     * <BR/>
     */

    GAODE_API_ADCODE_UNAVAILABLE("8001","高德地址反编码API,服务不可用~~~");

    private final String code;
    private final String message;

    StatusCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
