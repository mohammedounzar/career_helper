package ma.mohammedounzar.resumeparser.model;

import jakarta.persistence.*;

@Entity
public class Resume {
    public enum state {
        WAITING,
        OCR_QUEUED, OCR_IN_PROGRESS, OCR_DONE, OCR_FAILED,
        PARSE_IN_PROGRESS, PARSE_DONE, PARSE_FAILED,
    }

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String objectPath;

    @Lob
    private String ocrResult;

    private String parseResult;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private state state;

    // No-args constructor
    public Resume() {
    }

    // All-args constructor
    public Resume(Long id, String objectPath, String ocrResult, String parseResult, state state) {
        this.id = id;
        this.objectPath = objectPath;
        this.ocrResult = ocrResult;
        this.parseResult = parseResult;
        this.state = state;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getObjectPath() {
        return objectPath;
    }

    public void setObjectPath(String objectPath) {
        this.objectPath = objectPath;
    }

    public String getOcrResult() {
        return ocrResult;
    }

    public void setOcrResult(String ocrResult) {
        this.ocrResult = ocrResult;
    }

    public String getParseResult() {
        return parseResult;
    }

    public void setParseResult(String parseResult) {
        this.parseResult = parseResult;
    }

    public state getState() {
        return state;
    }

    public void setState(state state) {
        this.state = state;
    }
}
