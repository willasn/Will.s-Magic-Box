package top.will.ws_rabbitmq_demo.service.impl;

import com.power.common.util.UUIDUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import top.will.ws_rabbitmq_demo.mapper.DustAlertMapper;
import top.will.ws_rabbitmq_demo.pojo.AlertDust;
import top.will.ws_rabbitmq_demo.service.DeviceApiService;

/**
 * @author panhao
 */
@Service
@RequiredArgsConstructor
public class DeviceApiServiceImpl implements DeviceApiService {

    private final DustAlertMapper dustAlertMapper;


    /**
     * 扬尘报警
     */
    @Override
    public Integer dustAlert(AlertDust alertDust) {
        alertDust.setAlertUuid(UUIDUtil.getUuid32());
        return dustAlertMapper.insert(alertDust);
    }

}
