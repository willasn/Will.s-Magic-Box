package top.will.ws_rabbitmq_demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.will.ws_rabbitmq_demo.pojo.AlertDust;

/**
 * @author panhao
 */
@Mapper
public interface DustAlertMapper extends BaseMapper<AlertDust> {
}
