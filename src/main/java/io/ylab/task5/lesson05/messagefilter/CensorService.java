package io.ylab.task5.lesson05.messagefilter;

import com.rabbitmq.client.Channel;
import org.springframework.stereotype.Component;

import java.io.IOException;

@SuppressWarnings("SqlResolve")
@Component
public class CensorService {

    private DbManager dbManager;
    private RabbitPublisher rabbitPublisher;

    public CensorService(DbManager dbManager, RabbitPublisher rabbitPublisher) {
        this.dbManager = dbManager;
        this.rabbitPublisher = rabbitPublisher;
    }

    public void processInputMessage(String message) throws IOException {
        String[] words = message.split("[ ,;.:!?]+");
        String processedMessage = message;

        for (String word : words) {
            boolean found = dbManager.isBadWord(word);

            if (found) {
                StringBuilder processedWord = new StringBuilder(word.substring(0, 1));
                for (int i = 1; i < word.length() - 1; i++) {
                    processedWord.append("*");
                }
                processedWord.append(word.substring(word.length() - 1));

                processedMessage = processedMessage.replace(word, processedWord.toString());
            }
        }

        System.out.println("input = " + message + " output = " + processedMessage);
        rabbitPublisher.publishCensored(processedMessage);
    }
}
