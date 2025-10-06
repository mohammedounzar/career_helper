package ma.mohammedounzar.aiservice;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {

    private final ChatClient chatClient;

    public ChatController(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    @GetMapping("/calculateMatch")
    public String calculateMatch(
            @RequestParam String jobDescription,
            @RequestParam String resumeText
    ) {
        String prompt = String.format("""
        You are a resume analysis assistant.

        Compare the following job description and resume, 
        and give a *match score* from 0 to 100 based on how well 
        the resume fits the job requirements.

        Respond only with the numeric score.

        Job Description:
        %s

        Resume:
        %s
        """, jobDescription, resumeText);

        return chatClient.prompt()
                .user(prompt)
                .call()
                .content();
    }
}
