package resume.parser.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Education {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String degree;
    private String institution;
    private int start_year;
    private int end_year;

    @ManyToOne
    @JoinColumn(name = "candidate_id")
    private Candidate candidate;

    public Education() {}

    public Education(String degree, String institution, int start_year, int end_year, Candidate candidate) {
        this.degree = degree;
        this.institution = institution;
        this.start_year = start_year;
        this.end_year = end_year;
        this.candidate = candidate;
    }

    public Long getId() {
        return id;
    }

    public String getDegree() {
        return degree;
    }

    public String getInstitution() {
        return institution;
    }

    public int getStart_year() {
        return start_year;
    }

    public int getEnd_year() {
        return end_year;
    }

    public Candidate getCandidate() {
        return candidate;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public void setStart_year(int start_year) {
        this.start_year = start_year;
    }

    public void setEnd_year(int end_year) {
        this.end_year = end_year;
    }

    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }
}
