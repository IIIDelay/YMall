package org.ymall.commons.handler.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.data.redis.connection.stream.RecordId;
import org.springframework.data.redis.stream.StreamListener;
import org.springframework.stereotype.Component;
import org.ymall.commons.helper.RedisMQHelper;

import javax.annotation.PostConstruct;
import java.util.Map;

/**
 * @description：消费监听，自动ack
 */
@Slf4j
@Component
public class ConsumeListener implements StreamListener<String, MapRecord<String, String, String>> {

    @Autowired
    private RedisMQHelper redisUtil;

    private static ConsumeListener consumeListener;

    @PostConstruct
    public void init() {
        consumeListener = this;
        consumeListener.redisUtil = this.redisUtil;
    }

    @Override
    public void onMessage(MapRecord<String, String, String> message) {
        String stream = message.getStream();
        RecordId id = message.getId();
        Map<String, String> map = message.getValue();
        log.info("[自动] group:[group-b] 接收到一个消息 stream:[{}],id:[{}],value:[{}]", stream, id, map);
        // 消费完毕删除该条消息
        consumeListener.redisUtil.del(stream, id.getValue());
    }
}