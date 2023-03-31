package io.ylab.task4.io.ylab.intensive.lesson04.eventsourcing.api;

import com.rabbitmq.client.Channel;
import io.ylab.task4.io.ylab.intensive.lesson04.eventsourcing.Person;

import javax.sql.DataSource;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Тут пишем реализацию
 */
@SuppressWarnings({"SqlResolve"})
class PersonApiImpl implements PersonApi {
    private static final Logger logger = Logger.getLogger(PersonApiImpl.class.getName());
    private final Channel channel;
    private final String queueName;
    private final DataSource dataSource;

    private static final String QUEUE_NAME = "queue_name";

    public PersonApiImpl(Channel channel, DataSource dataSource) throws IOException {
        this.channel = channel;
        this.queueName = QUEUE_NAME;
        this.dataSource = dataSource;
        channel.queueDeclare(queueName, false, false, false, null);
    }

    public void deletePerson(Long id) {
        String message = "delete:" + id;
        try {
            channel.basicPublish("", queueName, null, message.getBytes(StandardCharsets.UTF_8));
            logger.info("Sent message: " + message);
        } catch (Exception e) {
            logger.warning("Failed to send message: " + message);
        }
    }

    public void savePerson(Long personId, String name, String lastName, String middleName) {
        String message = "save:" + personId + "," + name + "," + lastName + "," + middleName;
        try {
            channel.basicPublish("", queueName, null, message.getBytes(StandardCharsets.UTF_8));
            logger.info("Sent message: " + message);
        } catch (Exception e) {
            logger.warning("Failed to send message: " + message);
        }
    }

    public Person findPerson(Long personId) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM person WHERE person_id = ?");
            statement.setLong(1, personId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Person(resultSet.getLong("person_id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getString("middle_name"));
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Person> findAll() {
        List<Person> persons = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM person");
            while (resultSet.next()) {
                Long id = resultSet.getLong("person_id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String middleName = resultSet.getString("middle_name");
                persons.add(new Person(id, firstName, lastName, middleName));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return persons;
    }
}
