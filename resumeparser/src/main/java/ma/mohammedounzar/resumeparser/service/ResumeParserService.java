package ma.mohammedounzar.resumeparser.service;

import ma.mohammedounzar.resumeparser.model.Resume;
import ma.mohammedounzar.resumeparser.exceptions.NotFoundException;
import ma.mohammedounzar.resumeparser.repository.ResumeParserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import static ma.mohammedounzar.resumeparser.common.Constants.OCR_REQUESTS_TOPIC;

@Service
public class ResumeParserService {
    private final ResumeParserRepository resumeRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public ResumeParserService(ResumeParserRepository resumeRepository, KafkaTemplate<String, String> kafkaTemplate) {
        this.resumeRepository = resumeRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void parseResume(Long id) {
        var resume = resumeRepository.findById(id).orElseThrow(NotFoundException::new);

        kafkaTemplate.send(OCR_REQUESTS_TOPIC, resume.getId().toString());

        resume.setState(Resume.state.OCR_QUEUED);
        resumeRepository.save(resume);
    }
}
