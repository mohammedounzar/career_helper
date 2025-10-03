package ma.mohammedounzar.resumeparser.model;

import jakarta.persistence.*;

@Entity
@Table(name = "resume")
public class Resume {

    public enum State {
        WAITING,
        OCR_QUEUED, OCR_IN_PROGRESS, OCR_DONE, OCR_FAILED,
        PARSE_IN_PROGRESS, PARSE_DONE, PARSE_FAILED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String objectPath;

    private String ocrResult;

    private String parseResult;

    private State state;

    // --- Constructors ---
    public Resume() {}

    public Resume(Long id, String objectPath, String ocrResult, String parseResult, State state) {
        this.id = id;
        this.objectPath = objectPath;
        this.ocrResult = ocrResult;
        this.parseResult = parseResult;
        this.state = state;
    }

    // --- Getters & Setters ---
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

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
