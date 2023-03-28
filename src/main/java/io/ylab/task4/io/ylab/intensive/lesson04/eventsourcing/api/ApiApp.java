package io.ylab.task4.io.ylab.intensive.lesson04.eventsourcing.api;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import io.ylab.task4.io.ylab.intensive.lesson04.DbUtil;
import io.ylab.task4.io.ylab.intensive.lesson04.RabbitMQUtil;
import io.ylab.task4.io.ylab.intensive.lesson04.eventsourcing.Person;

import java.util.List;


public class ApiApp {


    public static void main(String[] args) throws Exception {
        ConnectionFactory connectionFactory = initMQ();
        try (Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel()) {
            PersonApi personApi = new PersonApiImpl(channel, DbUtil.buildDataSource());

            // clear db
            clearDb(personApi);

            // check db is empty
            Thread.sleep(1000);
            assertEquals(personApi.findAll().size(), 0);

            // save persons
            personApi.savePerson(1L, "Ivan", "Ivanov", "Ivanovich");
            personApi.savePerson(2L, "Dima", "Dimanov", "Dimonovich");


            Thread.sleep(1000);
            // check person is saved
            assertEquals(personApi.findPerson(1L), new Person(1L, "Ivan", "Ivanov", "Ivanovich"));
            assertEquals(personApi.findPerson(2L), new Person(2L, "Dima", "Dimanov", "Dimonovich"));
            // check through findAll
            List<Person> allPersones = personApi.findAll();
            assertEquals(allPersones.size(), 2);
            // persons are presented
            assertEquals(allPersones.get(0), new Person(1L, "Ivan", "Ivanov", "Ivanovich"));
            assertEquals(allPersones.get(1), new Person(2L, "Dima", "Dimanov", "Dimonovich"));

            // delete one person
            personApi.deletePerson(1L);

            Thread.sleep(1000);

            assertEquals(personApi.findPerson(1L), null);  // person deleted
            allPersones = personApi.findAll();
            assertEquals(allPersones.size(), 1);
            assertEquals(allPersones.get(0), new Person(2L, "Dima", "Dimanov", "Dimonovich")); // old one is still here

            fifoTest(personApi);

            System.out.println("all good");
        }
    }

    private static void clearDb(PersonApi personApi) {
        List<Person> persons = personApi.findAll();
        for (Person p : persons) {
            personApi.deletePerson(p.getId());
        }
    }

    private static void fifoTest(PersonApi personApi) throws InterruptedException {
        clearDb(personApi);
        Thread.sleep(1000L);

        personApi.deletePerson(10L);  // delete nothing
        personApi.savePerson(10L, "Ivan", "Ivanov", "Ivanovich");
        personApi.deletePerson(11L); // delete nothing
        personApi.savePerson(11L, "Dima", "Dimanov", "Dimonovich");

        personApi.savePerson(12L, "Vasya", "Vasin", "Vasyanov");
        personApi.deletePerson(12L);  // actual removal

        Thread.sleep(1000);
        // only two persons should be left
        List<Person> allPersones = personApi.findAll();
        assertEquals(allPersones.size(), 2);
        assertEquals(allPersones.get(0), new Person(10L, "Ivan", "Ivanov", "Ivanovich"));
        assertEquals(allPersones.get(1), new Person(11L, "Dima", "Dimanov", "Dimonovich"));
    }

    private static ConnectionFactory initMQ() throws Exception {
        return RabbitMQUtil.buildConnectionFactory();
    }

    private static void assertEquals(Object actual, Object expected) {
        if (actual == null && expected == null) {
            return;
        }
        if (actual == null) {
            throw new AssertionError("Expected: " + expected + ", actual: " + actual);
        }
        if (!actual.equals(expected)) {
            throw new AssertionError("Expected: " + expected + ", actual: " + actual);
        }
    }
}
