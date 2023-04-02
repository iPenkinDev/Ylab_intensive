package io.ylab.task5.lesson05.eventsourcing.db;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@SuppressWarnings({"SqlResolve", "DuplicatedCode"})
@Component
public class EventConsumer extends DefaultConsumer {

    private final DataSource dataSource;

    public EventConsumer(Channel channel, DataSource dataSource) {
        super(channel);
        this.dataSource = dataSource;
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
        String message = new String(body, StandardCharsets.UTF_8);
        System.out.println("Received message: " + message);

        String[] parts = message.split(":");
        String action = parts[0];
        String[] data = parts[1].split(",");

        if ("save".equals(action)) {
            Long id = Long.parseLong(data[0]);
            String name = data[1];
            String lastName = data[2];
            String middleName = data[3];
            savePerson(id, name, lastName, middleName, dataSource);
        } else if ("delete".equals(action)) {
            Long personId = Long.parseLong(data[0]);
            deletePerson(personId, dataSource);
        }
    }

    private static void savePerson(Long id, String name, String lastName, String middleName, DataSource dataSource) {
        try (java.sql.Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("INSERT INTO person (person_id, first_name, last_name, middle_name) VALUES (?, ?, ?, ?)")) {
            statement.setLong(1, id);
            statement.setString(2, name);
            statement.setString(3, lastName);
            statement.setString(4, middleName);
            statement.executeUpdate();
            System.out.println("saved person_id = " + id + " name = " + name + " last_name = " + lastName + " middle_name = " + middleName );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // delete from db
    private static void deletePerson(Long personId, DataSource dataSource) {
        try (java.sql.Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("DELETE FROM person WHERE person_id = ?")) {
            statement.setLong(1, personId);
            int rows = statement.executeUpdate();
            System.out.println("removed " + rows + " rows for person_id = " + personId + " from db");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}