package ma.mohammedounzar.resumeparser.model;

import jakarta.persistence.*;

@Entity
public class JobDescription {
    @Id @GeneratedValue
    private Long id;

    @Column(length = 2000)
    private String description;

    public JobDescription(Long id, String description) {
        this.id = id;
        this.description = description;
    }

    public JobDescription() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
