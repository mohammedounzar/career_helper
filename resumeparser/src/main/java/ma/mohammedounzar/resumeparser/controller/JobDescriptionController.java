package ma.mohammedounzar.resumeparser.controller;

import ma.mohammedounzar.resumeparser.exceptions.BadRequestException;
import ma.mohammedounzar.resumeparser.model.JobDescription;
import ma.mohammedounzar.resumeparser.repository.JobDescriptionRepository;
import ma.mohammedounzar.resumeparser.service.JobDescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/parser")
public class JobDescriptionController {
    private final JobDescriptionService jobDescriptionService;

    @Autowired
    public JobDescriptionController(JobDescriptionService jobDescriptionService) {
        this.jobDescriptionService = jobDescriptionService;
    }

    @PostMapping("/saveJobDescription")
    public void saveJobDescription(@RequestBody JobDescription jobDescription) {
        jobDescriptionService.saveJobDescription(jobDescription);
    }
}
