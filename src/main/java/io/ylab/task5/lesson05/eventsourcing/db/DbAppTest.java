package io.ylab.task5.lesson05.eventsourcing.db;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.io.IOException;

@Component
public class DbAppTest {

    private final Channel channel;
    private final ConnectionFactory connectionFactory;
    private final DataSource dataSource;
    private final EventConsumer eventConsumer;

    public DbAppTest(Channel channel, ConnectionFactory connectionFactory, DataSource dataSource, EventConsumer eventConsumer) {
        this.channel = channel;
        this.connectionFactory = connectionFactory;
        this.dataSource = dataSource;
        this.eventConsumer = eventConsumer;
    }

    @PostConstruct
    void onStart() throws IOException {
        System.out.println("On start");

        channel.queueDeclare("person", false, false, false, null);
        channel.basicConsume("person", true, eventConsumer);
    }
}
