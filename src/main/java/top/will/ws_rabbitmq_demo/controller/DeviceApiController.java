package top.will.ws_rabbitmq_demo.controller;

import com.power.common.constants.BaseErrorCode;
import com.power.common.model.CommonResult;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.will.ws_rabbitmq_demo.config.RabbitmqConfig;
import top.will.ws_rabbitmq_demo.pojo.AlertDust;

/**
 * @author panhao
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/apiPush")
public class DeviceApiController {

    private final RabbitTemplate rabbitTemplate;


    @PostMapping("/device/dustAlert")
    @ApiOperation("扬尘报警")
    public CommonResult<Integer> dustAlert(@RequestBody @Validated AlertDust alertDust) {
        Integer integer = (Integer) rabbitTemplate.convertSendAndReceive(RabbitmqConfig.EXCHANGE_TOPICS_INFORM,RabbitmqConfig.ROUTING_KEY_DUST_ALERT, alertDust);
        CommonResult<Integer> ok = CommonResult.ok(BaseErrorCode.Common.SUCCESS);
        return ok.setResult(integer);
    }
}
