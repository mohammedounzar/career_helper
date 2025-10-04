package ma.mohammedounzar.resumeparser.controller;

import ma.mohammedounzar.resumeparser.exceptions.BadRequestException;
import ma.mohammedounzar.resumeparser.service.ResumeParserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/parser")
public class ResumeParserController {
    private final ResumeParserService parserService;

    @Autowired
    public ResumeParserController(ResumeParserService parserService) {
        this.parserService = parserService;
    }

    @GetMapping("/resumes/{id}")
    public void parseResume(@PathVariable Long id) {
        if (id == null) {
            throw new BadRequestException();
        }
        parserService.parseResume(id);
    }

}
