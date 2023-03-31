package io.ylab.task4.io.ylab.intensive.lesson04.eventsourcing.db;

import com.rabbitmq.client.*;
import io.ylab.task4.io.ylab.intensive.lesson04.DbUtil;
import io.ylab.task4.io.ylab.intensive.lesson04.RabbitMQUtil;
import io.ylab.task4.io.ylab.intensive.lesson04.eventsourcing.api.PersonApi;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@SuppressWarnings("SqlResolve")
public class DbApp {
    private static final String QUEUE_NAME = "queue_name";

    public static void main(String[] args) throws Exception {
        DataSource dataSource = initDb();
        ConnectionFactory connectionFactory = initMQ();
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        Consumer consumer = new Consumer(channel, dataSource);
        // run consumer
        channel.basicConsume(QUEUE_NAME, true, consumer);

    }

    private static ConnectionFactory initMQ() throws Exception {
        return RabbitMQUtil.buildConnectionFactory();
    }

    private static DataSource initDb() throws SQLException {
        String ddl = ""
                + "drop table if exists person;"
                + "CREATE TABLE IF NOT EXISTS person (\n"
                + "person_id bigint primary key,\n"
                + "first_name varchar,\n"
                + "last_name varchar,\n"
                + "middle_name varchar\n"
                + ")";
        DataSource dataSource = DbUtil.buildDataSource();
        DbUtil.applyDdl(ddl, dataSource);
        return dataSource;
    }

    // save in db
    private static void savePerson(Long id, String name, String lastName, String middleName, DataSource dataSource) {
        try (java.sql.Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO person (person_id, first_name, last_name, middle_name) VALUES (?, ?, ?, ?)");
            statement.setLong(1, id);
            statement.setString(2, name);
            statement.setString(3, lastName);
            statement.setString(4, middleName);
            int result = statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // delete from db
    private static void deletePerson(Long personId, DataSource dataSource) {
        try (java.sql.Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM person WHERE person_id = ?");
            statement.setLong(1, personId);
            int rows = statement.executeUpdate();
            System.out.println("removed " + rows + " rows for person_id = " + personId + " from db");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static class Consumer extends DefaultConsumer {
        private final DataSource dataSource;

        public Consumer(Channel channel, DataSource dataSource) {
            super(channel);
            this.dataSource = dataSource;
        }

        @Override
        public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
            String message = new String(body, "UTF-8");
            System.out.println("Received message: " + message);

            // Разбиваем сообщение на составляющие
            String[] parts = message.split(":");
            String action = parts[0];
            String[] data = parts[1].split(",");

            // Обработка сообщения в зависимости от действия
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
    }
}
