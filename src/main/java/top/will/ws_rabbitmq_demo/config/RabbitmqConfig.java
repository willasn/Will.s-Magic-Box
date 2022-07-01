package top.will.ws_rabbitmq_demo.config;

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
    public static final String ROUTING_KEY_CAR_WASH = "inform.#.carWash.#";

    public static final String ROUTING_KEY_TOWER_HIS = "tower_his";

    public static final String ROUTING_KEY_TOWER_ALERT = "tower_alert";

    public static final String ROUTING_KEY_ELEVATOR_HIS = "elevator_his";

    public static final String ROUTING_KEY_ELEVATOR_ALERT = "elevator_alert";

    public static final String ROUTING_KEY_DEEP_ALERT = "deep_alert";

    public static final String ROUTING_KEY_DEEP_DETAIL = "deep_detail";

    public static final String ROUTING_KEY_DEEP_RELATION = "deep_relation";

    public static final String ROUTING_KEY_UNLOAD_HIS = "unload_his";

    public static final String ROUTING_KEY_UNLOAD_ALERT = "unload_alert";

    public static final String ROUTING_KEY_DUST_ALERT = "dust_alert";

    public static final String ROUTING_KEY_DUST_HIS = "dust_his";

    public static final String ROUTING_KEY_PEOPLE_POSITION = "people_position";

    // QUEUE========================================>>>>
    public static final String QUEUE_INFORM_CAR_WASH = "queue_inform_carWash";

    public static final String QUEUE_INFORM_PEOPLE_POSITION = "queue_inform_people_position";

    public static final String TOWER_HISTORIES_QUEUE = "TowerHistoriesQueue";

    public static final String TOWER_ALERT_QUEUE = "TowerAlertQueue";

    public static final String TOWER_ALERT_QUEUE2WEB = "TowerAlert2WebQueue";

    public static final String ELEVATOR_HISTORIES_QUEUE = "ElevatorHistoriesQueue";

    public static final String ELEVATOR_ALERT_QUEUE = "ElevatorAlertQueue";

    public static final String ELEVATOR_ALERT_QUEUE2WEB = "ElevatorAlert2WebQueue";

    public static final String DEEP_PIT_ALERT_QUEUE = "DeepPitAlertQueue";

    public static final String DEEP_PIT_ALERT_QUEUE2WEB = "DeepPitAlert2WebQueue";

    public static final String DEEP_PIT_DETAILS_QUEUE = "DeepPitDetailsQueue";

    public static final String DEEP_PIT_RELATION_QUEUE = "DeepPitRelationQueue";

    public static final String UNLOAD_ALERT_QUEUE = "UnloadAlertQueue";

    public static final String UNLOAD_ALERT_QUEUE2WEB = "UnloadAlert2WebQueue";

    public static final String UNLOAD_HISTORIES_QUEUE = "UnloadHistoriesQueue";

    public static final String DUST_ALERT_QUEUE = "DustAlertQueue";

    public static final String DUST_ALERT_QUEUE2WEB = "DustAlert2WebQueue";

    public static final String DUST_HISTORIES_QUEUE = "DustHistoriesQueue";

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
     * 声明QUEUE_INFORM_CAR_WASH队列
     */
    @Bean(QUEUE_INFORM_CAR_WASH)
    public Queue myInformCarWash() {
        return new Queue(QUEUE_INFORM_CAR_WASH);
    }

    /**
     * QUEUE_INFORM_PEOPLE_POSITION
     */
    @Bean(QUEUE_INFORM_PEOPLE_POSITION)
    public Queue peoplePosition() {
        return new Queue(QUEUE_INFORM_PEOPLE_POSITION);
    }

    /**
     * 声明塔机运行队列
     */
    @Bean
    public Queue buildTowerHistoriesQueue() {
        return new Queue(TOWER_HISTORIES_QUEUE);
    }

    /**
     * 声明塔机告警队列
     */
    @Bean
    public Queue buildTowerAlertQueue() {
        return new Queue(TOWER_ALERT_QUEUE);
    }

    @Bean
    public Queue buildTowerAlert2WebQueue() {
        return new Queue(TOWER_ALERT_QUEUE2WEB);
    }

    /**
     * 声明升降机运行队列
     */
    @Bean
    public Queue buildElevatorHistoriesQueue() {
        return new Queue(ELEVATOR_HISTORIES_QUEUE);
    }

    /**
     * 声明升降机报警队列
     */
    @Bean
    public Queue buildElevatorAlertQueue() {
        return new Queue(ELEVATOR_ALERT_QUEUE);
    }

    @Bean
    public Queue buildElevatorAlert2WebQueue() {
        return new Queue(ELEVATOR_ALERT_QUEUE2WEB);
    }

    /**
     * 声明深基坑报警队列
     */
    @Bean
    public Queue buildDeepPitAlertQueue() {
        return new Queue(DEEP_PIT_ALERT_QUEUE);
    }

    @Bean
    public Queue buildDeepPitAlert2WebQueue() {
        return new Queue(DEEP_PIT_ALERT_QUEUE2WEB);
    }

    /**
     * 声明深基坑检测项详情队列
     */
    @Bean
    public Queue buildDeepPitDetailsQueue() {
        return new Queue(DEEP_PIT_DETAILS_QUEUE);
    }

    @Bean
    public Queue buildDeepPitRelationQueue() {
        return new Queue(DEEP_PIT_RELATION_QUEUE);
    }

    /**
     * 声明卸料台告警记录队列
     */
    @Bean
    public Queue buildUnloadAlertQueue() {
        return new Queue(UNLOAD_ALERT_QUEUE);
    }

    @Bean
    public Queue buildUnloadAlert2WebQueue() {
        return new Queue(UNLOAD_ALERT_QUEUE2WEB);
    }

    /**
     * 声明卸料台运行记录队列
     */
    @Bean
    public Queue buildUnloadHistoriesQueue() {
        return new Queue(UNLOAD_HISTORIES_QUEUE);
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

    /**
     * 声明扬尘运行队列
     */
    @Bean
    public Queue buildDustHistoriesQueue() {
        return new Queue(DUST_HISTORIES_QUEUE);
    }


    /**
     * ROUTING_KEY_EMAIL队列绑定交换机，指定routingKey
     */
    @Bean
    public Binding bindingQueueInformCarWash(@Qualifier(QUEUE_INFORM_CAR_WASH) Queue queue,
                                             @Qualifier(EXCHANGE_TOPICS_INFORM) Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY_CAR_WASH).noargs();
    }

    @Bean
    public Binding bindingQueueInformPeoplePosition(@Qualifier(QUEUE_INFORM_PEOPLE_POSITION) Queue queue,
                                             @Qualifier(EXCHANGE_TOPICS_INFORM) Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY_PEOPLE_POSITION).noargs();
    }

    @Bean
    public Binding bindingTowerHistoriesQueue(@Qualifier("buildTowerHistoriesQueue") Queue queue,
                                             @Qualifier(EXCHANGE_TOPICS_INFORM) Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY_TOWER_HIS).noargs();
    }


    @Bean
    public Binding bindingTowerAlertQueue(@Qualifier("buildTowerAlertQueue") Queue queue,
                                             @Qualifier(EXCHANGE_TOPICS_INFORM) Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY_TOWER_ALERT).noargs();
    }

    @Bean
    public Binding bindingTowerAlert2WebQueue(@Qualifier("buildTowerAlert2WebQueue") Queue queue,
                                          @Qualifier(EXCHANGE_TOPICS_INFORM) Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY_TOWER_ALERT).noargs();
    }

    @Bean
    public Binding bindingElevatorHistoriesQueue(@Qualifier("buildElevatorHistoriesQueue") Queue queue,
                                             @Qualifier(EXCHANGE_TOPICS_INFORM) Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY_ELEVATOR_HIS).noargs();
    }

    @Bean
    public Binding bindingElevatorAlertQueue(@Qualifier("buildElevatorAlertQueue") Queue queue,
                                             @Qualifier(EXCHANGE_TOPICS_INFORM) Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY_ELEVATOR_ALERT).noargs();
    }

    @Bean
    public Binding bindingElevatorAlert2WebQueue(@Qualifier("buildElevatorAlert2WebQueue") Queue queue,
                                             @Qualifier(EXCHANGE_TOPICS_INFORM) Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY_ELEVATOR_ALERT).noargs();
    }

    @Bean
    public Binding bindingDeepPitAlertQueue(@Qualifier("buildDeepPitAlertQueue") Queue queue,
                                             @Qualifier(EXCHANGE_TOPICS_INFORM) Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY_DEEP_ALERT).noargs();
    }

    @Bean
    public Binding bindingDeepPitAlert2WebQueue(@Qualifier("buildDeepPitAlert2WebQueue") Queue queue,
                                            @Qualifier(EXCHANGE_TOPICS_INFORM) Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY_DEEP_ALERT).noargs();
    }

    @Bean
    public Binding bindingDeepPitDetailsQueue(@Qualifier("buildDeepPitDetailsQueue") Queue queue,
                                             @Qualifier(EXCHANGE_TOPICS_INFORM) Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY_DEEP_DETAIL).noargs();
    }

    @Bean
    public Binding bindingDeepPitRelationQueue(@Qualifier("buildDeepPitRelationQueue") Queue queue,
                                              @Qualifier(EXCHANGE_TOPICS_INFORM) Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY_DEEP_RELATION).noargs();
    }

    @Bean
    public Binding bindingUnloadAlertQueue(@Qualifier("buildUnloadAlertQueue") Queue queue,
                                             @Qualifier(EXCHANGE_TOPICS_INFORM) Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY_UNLOAD_HIS).noargs();
    }

    @Bean
    public Binding bindingUnloadAlert2WebQueue(@Qualifier("buildUnloadAlert2WebQueue") Queue queue,
                                           @Qualifier(EXCHANGE_TOPICS_INFORM) Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY_UNLOAD_HIS).noargs();
    }

    @Bean
    public Binding bindingUnloadHistoriesQueue(@Qualifier("buildUnloadHistoriesQueue") Queue queue,
                                             @Qualifier(EXCHANGE_TOPICS_INFORM) Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY_UNLOAD_ALERT).noargs();
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

    @Bean
    public Binding bindingDustHistoriesQueue(@Qualifier("buildDustHistoriesQueue") Queue queue,
                                             @Qualifier(EXCHANGE_TOPICS_INFORM) Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY_DUST_HIS).noargs();
    }

}