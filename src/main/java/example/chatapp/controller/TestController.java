package example.chatapp.controller;


import example.chatapp.service.KafkaConsumerService;
import example.chatapp.service.KafkaProducerService;
import example.chatapp.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class TestController {

    @Autowired
    private KafkaProducerService kafkaProducerService;

    @Autowired
    private KafkaConsumerService kafkaConsumerService;

    @Autowired
    private RedisService redisService;

    @PostMapping("/kafka/send")
    public String sendToKafka(@RequestParam String message) {
        kafkaProducerService.sendMessage(message);
        return "Message sent to Kafka";
    }
    // i want to get the message which is send by kafka producer
    @GetMapping
    public String getKafkaMessage() {
        // This method should return the message received from Kafka
        // You need to implement a way to store the received messages in your KafkaConsumerService
        return "Received message from Kafka: "+kafkaConsumerService.getMsgList();
    }

    @PostMapping("/redis/save")
    public String saveToRedis(@RequestParam String key, @RequestParam String value) {
        redisService.save(key, value);
        return "Saved to Redis";
    }

    @GetMapping("/redis/get")
    public String getFromRedis(@RequestParam String key) {
        return redisService.get(key);
    }
}
