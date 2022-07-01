package top.will.ws_rabbitmq_demo.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import top.will.ws_rabbitmq_demo.config.RabbitmqConfig;
import top.will.ws_rabbitmq_demo.pojo.AlertDust;
import top.will.ws_rabbitmq_demo.service.DeviceApiService;

/**
 * @author panhao
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class DeviceMqListener {

    private final DeviceApiService deviceApiService;

    /**
     * 扬尘报警
     */
    @RabbitListener(id = RabbitmqConfig.ROUTING_KEY_DUST_ALERT, queues = {RabbitmqConfig.DUST_ALERT_QUEUE}, autoStartup = "true")
    public Integer dustAlertListener(AlertDust msg) {
        return deviceApiService.dustAlert(msg);
    }

}
