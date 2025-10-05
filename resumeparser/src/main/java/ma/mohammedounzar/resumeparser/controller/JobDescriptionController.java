package ma.mohammedounzar.resumeparser.controller;

import ma.mohammedounzar.resumeparser.exceptions.BadRequestException;
import ma.mohammedounzar.resumeparser.model.JobDescription;
import ma.mohammedounzar.resumeparser.repository.JobDescriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/parser")
public class JobDescriptionController {
    private final JobDescriptionRepository jobDescriptionRepository;

    @Autowired
    public JobDescriptionController(JobDescriptionRepository jobDescriptionRepository) {
        this.jobDescriptionRepository = jobDescriptionRepository;
    }

    @PostMapping("/saveJobDescription")
    public void saveJobDescription(@RequestBody JobDescription jobDescription) {
        jobDescriptionRepository.save(jobDescription);
    }
}
