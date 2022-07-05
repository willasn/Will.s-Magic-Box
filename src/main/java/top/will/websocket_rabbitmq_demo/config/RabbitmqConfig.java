package top.will.websocket_rabbitmq_demo.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author panhao
 */
@Configuration
public class RabbitmqConfig {

    // EXCHANGE================================>>>>
    public static final String EXCHANGE_TOPICS_INFORM = "exchange_topics_inform";

    // ROUTING_KEY=============================>>>>
    public static final String ROUTING_KEY_DUST_ALERT = "dust_alert";

    // QUEUE========================================>>>>
    public static final String DUST_ALERT_QUEUE = "DustAlertQueue";

    public static final String DUST_ALERT_QUEUE2WEB = "DustAlert2WebQueue";

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory)
    {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);

        //设置Json转换器
        rabbitTemplate.setMessageConverter(jsonMessageConverter());

        return rabbitTemplate;
    }

    /**
     * Json转换器
     */
    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    /**
     * 声明交换机
     */
    @Bean(EXCHANGE_TOPICS_INFORM)
    public Exchange myTopExchange() {
        //durable(true) 持久化，mq重启之后交换机还在
        return ExchangeBuilder.topicExchange(EXCHANGE_TOPICS_INFORM).durable(true).build();
    }

    /**
     * 声明扬尘报警队列
     */
    @Bean
    public Queue buildDustAlertQueue() {
        return new Queue(DUST_ALERT_QUEUE);
    }

    @Bean
    public Queue buildDustAlert2WebQueue() {
        return new Queue(DUST_ALERT_QUEUE2WEB);
    }


    @Bean
    public Binding bindingDustAlertQueue(@Qualifier("buildDustAlertQueue") Queue queue,
                                             @Qualifier(EXCHANGE_TOPICS_INFORM) Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY_DUST_ALERT).noargs();
    }

    @Bean
    public Binding bindingDustAlert2WebQueue(@Qualifier("buildDustAlert2WebQueue") Queue queue,
                                         @Qualifier(EXCHANGE_TOPICS_INFORM) Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY_DUST_ALERT).noargs();
    }


}