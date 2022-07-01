package top.will.ws_rabbitmq_demo.controller;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import top.will.ws_rabbitmq_demo.config.RabbitmqConfig;
import top.will.ws_rabbitmq_demo.pojo.AlertDust;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author panhao
 */
@Component
@Slf4j
@Api(tags = "websocket实时接口",value="websocket实时接口")
@ServerEndpoint("/websocket/alertPush/{sid}")
public class WebSocketDemo {
    //用来记录当前在线连接数,应该把它设计成线程安全的。
    private static int onlineCount = 0;

    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    private static final CopyOnWriteArraySet<WebSocketDemo> webSocketSet = new CopyOnWriteArraySet<>();

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    //接收sid,区分项目
    private String sid;

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("sid") String sid) {
        this.session = session;
        webSocketSet.add(this);     //加入set中
        if (StringUtils.isEmpty(sid)) {
            Throwable throwable = new Throwable("sid 参数错误");
            onError(session,throwable);
        }
        this.sid = sid;
        addOnlineCount(); //在线数加1
        log.info("新加入App实时推送："+session);
        //判断人数,大于1则已经开启队列监听,不做处理哦
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        webSocketSet.remove(this);  //从set中删除
        subOnlineCount();           //在线数减1
        //断开连接情况下，更新主板占用情况为释放
        log.info("释放的session为："+session);
    }

    /**
     * 收到客户端消息后调用的方法
     * @ Param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        try {
            sendMessage("来自服务器的反弹: "+message);
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info("收到来自窗口" + session + "的信息:" + message);
    }

    @OnError
    public void onError(Session session, Throwable error) {
        log.error("{} 发生错误",session);
        error.printStackTrace();
    }

    /**
     * 实现服务器主动推送
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    /**
     * 群发自定义消息,项目作为隔离
     */
    public static void sendInfo(String message, String projectInfoUuid) throws IOException {
        for (WebSocketDemo item : webSocketSet) {
                if (item.sid.equals(projectInfoUuid)) {
                    item.sendMessage(message);
                }
        }
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocketDemo.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketDemo.onlineCount--;
    }


    /**
     * 扬尘报警
     */
    @RabbitListener(id = "dustAlert2Web", queues = {RabbitmqConfig.DUST_ALERT_QUEUE2WEB})
    public void towerAlertListener2Web(AlertDust msg) throws IOException {
        String projectInfoUuid = msg.getProjectInfoUuid();
        if (getOnlineCount() > 0) {
            sendInfo(JSON.toJSONString(msg),projectInfoUuid);
        }
    }

}
