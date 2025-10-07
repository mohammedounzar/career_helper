package ma.mohammedounzar.resumeparser.service;

import ma.mohammedounzar.resumeparser.model.JobDescription;
import ma.mohammedounzar.resumeparser.repository.JobDescriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import static ma.mohammedounzar.resumeparser.common.Constants.JOB_DESC_TOPIC;

@Service
public class JobDescriptionService {
    private final JobDescriptionRepository jobDescriptionRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public JobDescriptionService(JobDescriptionRepository jobDescriptionRepository, KafkaTemplate<String, String> kafkaTemplate) {
        this.jobDescriptionRepository = jobDescriptionRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void saveJobDescription(JobDescription jobDescription) {
        JobDescription savedJobDescription = jobDescriptionRepository.save(jobDescription);

        // Exceptions management needed here
        kafkaTemplate.send(JOB_DESC_TOPIC, savedJobDescription.getId().toString());
    }
}
