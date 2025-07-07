package resume.parser.Entities;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Candidate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String full_name;
    private String email;
    private String phone_number;
    private String linkedin_url;
    private String github_url;
    private String summary;

    @OneToMany(mappedBy = "candidate", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Skill> skills;

    @OneToMany(mappedBy = "candidate", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WorkExperience> workExperience;

    @OneToMany(mappedBy = "candidate", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Project> projects;

    @OneToMany(mappedBy = "candidate", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Education> education;

    public Candidate() {}

    public Candidate(String full_name, String email, String phone_number, String linkedin_url, String github_url, String summary, List<Skill> skills, List<WorkExperience> workExperience, List<Project> projects, List<Education> education) {
        this.full_name = full_name;
        this.email = email;
        this.phone_number = phone_number;
        this.linkedin_url = linkedin_url;
        this.github_url = github_url;
        this.summary = summary;
        this.skills = skills;
        this.workExperience = workExperience;
        this.projects = projects;
        this.education = education;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getFull_name() {
        return full_name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public String getLinkedin_url() {
        return linkedin_url;
    }

    public String getGithub_url() {
        return github_url;
    }

    public String getSummary() {
        return summary;
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public List<WorkExperience> getWorkExperience() {
        return workExperience;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public List<Education> getEducation() {
        return education;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public void setLinkedin_url(String linkedin_url) {
        this.linkedin_url = linkedin_url;
    }

    public void setGithub_url(String github_url) {
        this.github_url = github_url;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }

    public void setWorkExperience(List<WorkExperience> workExperience) {
        this.workExperience = workExperience;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public void setEducation(List<Education> education) {
        this.education = education;
    }
}

