package com.bucket.test;

import com.alibaba.fastjson.JSONObject;
import com.bucket.message.process.MessageProcessor;
import com.bucket.message.process.ProcessContext;
import com.bucket.message.process.ProcessResult;
import com.bucket.message.rocketmq.Config;
import com.bucket.message.rocketmq.RocketMQClients;
import com.bucket.message.rocketmq.RocketMQContext;
import com.bucket.message.rocketmq.RocketMQReceiver;
import com.bucket.message.rocketmq.RocketMQSender;
import com.bucket.message.support.Destination;
import com.bucket.message.support.Message;
import com.bucket.message.support.MessageResult;
import com.bucket.message.support.TextMessage;

/**
 * @author yuguantou
 * @version 1.0
 * @since 2018-07-16 15:50
 */
public class Test {

    public static void main(String[] args){

        Config config = new Config();
        config.setAppName("test");
        config.setNameServer("mq101.2dfire-daily.com:9876;mq102.2dfire-daily.com:9876");
        RocketMQContext.init(config);

        Destination destination = Destination.topic("testTopic1");
        Message message =  TextMessage.create("hhhh","dddddd");
        message.setMsgType("gg");
        RocketMQSender.send(destination,message);
        RocketMQSender.send(destination,message);
        MessageResult result = RocketMQSender.send(destination,message);
        System.out.println(result.getMessageId());

        MessageProcessor dd = new MessageProcessor(){
            /**
             * 留给用户进行消息处理的扩展点
             *
             * @param message
             * @param context
             */
            @Override
            public ProcessResult process(Message message, ProcessContext context) {
                System.out.println(JSONObject.toJSONString(message));
                return ProcessResult.OK;
            }

            /**
             * 消息处理前的校验扩展点,例如：消息防重
             *
             * @param message
             * @param context
             */
            @Override
            public boolean isValid(Message message, ProcessContext context) {
                return true;
            }
        };
        dd.setDest(Destination.topic("testTopic1"));
        dd.getMsgTypeSet().add("gg");
        RocketMQReceiver.register(dd);

        RocketMQClients.start();
    }


}
