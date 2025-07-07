package resume.parser.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class WorkExperience {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String company_name;
    private String job_title;
    private String start_date;
    private String end_date;
    private String roleDetail;

    @ManyToOne
    @JoinColumn(name = "candidate_id")
    private Candidate candidate;

    public WorkExperience() {}

    public WorkExperience(String company_name, String job_title, String start_date, String end_date, String roleDetail, Candidate candidate) {
        this.company_name = company_name;
        this.job_title = job_title;
        this.start_date = start_date;
        this.end_date = end_date;
        this.roleDetail = roleDetail;
        this.candidate = candidate;
    }

    public Long getId() {
        return id;
    }

    public String getCompany_name() {
        return company_name;
    }

    public String getJob_title() {
        return job_title;
    }

    public String getStart_date() {
        return start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public String getRoleDetail() {
        return roleDetail;
    }

    public Candidate getCandidate() {
        return candidate;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public void setJob_title(String job_title) {
        this.job_title = job_title;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public void setRoleDetail(String roleDetail) {
        this.roleDetail = roleDetail;
    }

    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }
}

