package ma.mohammedounzar.resumeparser;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.TopicBuilder;

import static ma.mohammedounzar.resumeparser.common.Constants.*;

@SpringBootApplication
public class ResumeParserApplication {

    public static void main(String[] args) {
        SpringApplication.run(ResumeParserApplication.class, args);
    }

    // Defines (creates if not already existing) a Kafka topic named OCR_REQUESTS_TOPIC
    @Bean
    public NewTopic ocr_requests_topic() {
        return TopicBuilder.name(OCR_REQUESTS_TOPIC)
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic ocr_responses_topic() {
        return TopicBuilder.name(OCR_RESPONSES_TOPIC)
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic job_desc_topic() {
        return TopicBuilder.name(JOB_DESC_TOPIC)
                .partitions(1)
                .replicas(1)
                .build();
    }

    // Listens for messages published to the OCR_RESPONSES_TOPIC topic in Kafka
    @KafkaListener(topics = OCR_RESPONSES_TOPIC, id="resume-parser")
    public void listen(String message) {
        System.out.println(message);
    }
}
