package resume.parser.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String technical_skill;

    @ManyToOne
    @JoinColumn(name = "candidate_id")
    private Candidate candidate;

    public Skill() {}

    public Skill(String technical_skill, Candidate candidate) {
        this.technical_skill = technical_skill;
        this.candidate = candidate;
    }

    public Long getId() {
        return id;
    }

    public String getTechnical_skill() {
        return technical_skill;
    }

    public Candidate getCandidate() {
        return candidate;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTechnical_skill(String technical_skill) {
        this.technical_skill = technical_skill;
    }

    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }
}
