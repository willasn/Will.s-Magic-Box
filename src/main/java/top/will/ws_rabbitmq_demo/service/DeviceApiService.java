package top.will.ws_rabbitmq_demo.service;

import top.will.ws_rabbitmq_demo.pojo.AlertDust;

/**
 * @author panhao
 */
public interface DeviceApiService {

    /**
     * 扬尘报警
     */
    Integer dustAlert(AlertDust alertDust);

}
