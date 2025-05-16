package example.chatapp.service;


import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class KafkaConsumerService {
    List<String>msgList=new ArrayList<>();

    @KafkaListener(topics = "my-topic", groupId = "my-group")
    public void listen(String message) {
        msgList.add(message);
        System.out.println("Received message from Kafka: " + message);
    }
    public List<String> getMsgList() {
        System.out.println(msgList);
        return msgList;
    }
}
