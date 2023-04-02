package io.ylab.task5.lesson05.messagefilter;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Component
public class RabbitListener {

    private final Channel channel;
    private CensorService censorService;

    public RabbitListener(Channel channel, CensorService censorService) throws IOException {
        this.channel = channel;
        this.censorService = censorService;
        channel.queueDeclare("input", false, false, false, null);
    }

    @PostConstruct
    public void consumeInput() throws IOException {
        channel.basicConsume("input", true, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body);
                censorService.processInputMessage(message);
            }
        });
    }
}
