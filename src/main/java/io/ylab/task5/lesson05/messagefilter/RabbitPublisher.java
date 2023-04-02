package io.ylab.task5.lesson05.messagefilter;

import com.rabbitmq.client.Channel;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RabbitPublisher {

    private final Channel channel;

    public RabbitPublisher(Channel channel) throws IOException {
        this.channel = channel;
        channel.queueDeclare("output", false, false, false, null);
    }

    public void publishCensored(String processedMessage) throws IOException {
        channel.basicPublish("", "output", null, processedMessage.getBytes());
    }
}
