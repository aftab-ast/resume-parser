package resume.parser;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import resume.parser.Entities.Candidate;
import resume.parser.Repositories.CandidateRepository;
import resume.parser.Entities.Education;
import resume.parser.Entities.Project;
import resume.parser.Entities.Skill;
import resume.parser.Entities.WorkExperience;

@Controller
public class ResumeUploadController {

    @Autowired
    private CandidateRepository candidateRepository;

    @GetMapping("/")
    public String uploadFile() {
        return "uploadResume";
    }

    @PostMapping("/upload")
    @ResponseBody
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file) {

        String openAiResponse = "{\"full_name\":\"AftabAnsari\",\"email\":\"aftab.ast@gmail.com\",\"phone_number\":\"9586937628\",\"linkedin_url\":\"https://www.linkedin.com/in/aftab-ansari-967392172/\",\"github_url\":\"https://github.com/aftab-ansari\",\"summary\":\"SoftwareDevelopmentEngineerwithcloseto4yearsexperiencedevelopingrobust,scalablesolutionsinhigh-volumeenvironments.Adeptatanalyzingcomplexchallengesandeagertoleveragefullstackexpertisetodriveimpactfulprojects.\",\"skills\":[\"C++\",\"Java\",\"SQL\",\"Javascript\",\"LWC\",\"HTML/CSS\",\"SayonaraDB\",\"OracleDB\",\"Linux\",\"Spring\",\"Perforce\",\"GitHub\",\"JUnit\",\"Jest\"],\"work_experience\":[{\"company_name\":\"Salesforce\",\"job_title\":\"SoftwareEngineer,MTS\",\"start_date\":\"Nov2022\",\"end_date\":\"Present\",\"role_detail\":\"WorkedontransactiondisputesystemsusingSalesforce+VisaAPIs.Builtpollingcronjobs,ServiceProcessStudio,hierarchicalAPIretrieval,POSTcomplaintAPIs.Mentorednewhiresandservedassinglepointofcontactforcustomers.\"},{\"company_name\":\"Salesforce\",\"job_title\":\"SoftwareEngineer,AMTS\",\"start_date\":\"June2021\",\"end_date\":\"Oct2022\",\"role_detail\":\"Developedtransactiondisputeandserviceprocesssystems.CreatedAPIs,handledbugfixesandvoiceofcustomertickets.Improvedsystemreliabilityanddeliverytimelines.\"},{\"company_name\":\"SalesforceInc\",\"job_title\":\"SoftwareEngineeringIntern\",\"start_date\":\"May2020\",\"end_date\":\"July2020\",\"role_detail\":\"BuiltAPIsforDBcreation,ensuredgreensignoffonbugfixes,maintainedtestcoverageabove80%,ensuredon-timeprojectdelivery.\"}],\"education\":[{\"degree\":\"BachelorofTechnology-CSE\",\"institution\":\"NationalInstituteofTechnologyWarangal\",\"start_year\":2017,\"end_year\":2021}],\"projects\":[{\"title\":\"WordCloudDevelopment\",\"description\":\"ImplementedadynamicWordCloudusingLightningWebComponentsandintegrateditwithNLP-processeddatausingNLTKandspaCyforkeywordextractionfromsurveyresponses.\"}]}";
        
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> resumeData;
        try {
            resumeData = objectMapper.readValue(openAiResponse, Map.class);
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Invalid JSON format");
        }

        var candidateId = persistResumeData(resumeData);
        return ResponseEntity.ok("Resume uploaded successfully. Your candidate id is " + candidateId);
    }

    private String persistResumeData(Map<String, Object> resumeData) {

        var candidate = setCandidate(resumeData);
        setWorkExperience(resumeData, candidate);
        setEducation(resumeData, candidate);
        setProjects(resumeData, candidate);
        setSkills(resumeData, candidate);

        candidate = candidateRepository.save(candidate);

        return candidate.getId().toString();
    }

    private Candidate setCandidate(Map<String, Object> resumeData) {
        Candidate candidate = new Candidate();

        candidate.setFull_name((String) resumeData.get("full_name"));
        candidate.setEmail((String) resumeData.get("email"));
        candidate.setPhone_number((String) resumeData.get("phone_number"));
        candidate.setLinkedin_url((String) resumeData.get("linkedin_url"));
        candidate.setGithub_url((String) resumeData.get("github_url"));
        candidate.setSummary((String) resumeData.get("summary"));

        return candidate;
    }

    private void setWorkExperience(Map<String, Object> resumeData, Candidate candidate) {
        List<Map<String, Object>> workExperience = (List<Map<String, Object>>) resumeData.get("work_experience");
        List<WorkExperience> workExperienceEntities = workExperience.stream().map(w -> {
            WorkExperience exp = new WorkExperience();
            exp.setCompany_name((String) w.get("company_name"));
            exp.setJob_title((String) w.get("job_title"));
            exp.setStart_date((String) w.get("start_date"));
            exp.setEnd_date((String) w.get("end_date"));
            exp.setRoleDetail((String) w.get("role_detail"));
            exp.setCandidate(candidate);
            return exp;
        }).toList();

        candidate.setWorkExperience(workExperienceEntities);
    }

    private void setEducation(Map<String, Object> resumeData, Candidate candidate) {
        List<Map<String, Object>> education = (List<Map<String, Object>>) resumeData.get("education");
        List<Education> educationEntities = education.stream().map(e -> {
            Education edu = new Education();
            edu.setDegree((String) e.get("degree"));
            edu.setInstitution((String) e.get("institution"));
            edu.setStart_year((Integer) e.get("start_year"));
            edu.setEnd_year((Integer) e.get("end_year"));
            edu.setCandidate(candidate);
            return edu;
        }).toList();

        candidate.setEducation(educationEntities);
    }

    private void setProjects(Map<String, Object> resumeData, Candidate candidate) {
        List<Map<String, Object>> projects = (List<Map<String, Object>>) resumeData.get("projects");
        List<Project> projectEntities = projects.stream().map(p -> {
            Project proj = new Project();
            proj.setTitle((String) p.get("title"));
            proj.setDescription((String) p.get("description"));
            proj.setCandidate(candidate);
            return proj;
        }).toList();

        candidate.setProjects(projectEntities);
    }

    private void setSkills(Map<String, Object> resumeData, Candidate candidate) {
        List<String> skills = (List<String>) resumeData.get("skills");
        List<Skill> skillEntities = skills.stream().map(s -> {
            Skill skill = new Skill();
            skill.setTechnical_skill(s);
            skill.setCandidate(candidate);
            return skill;
        }).toList();

        candidate.setSkills(skillEntities);
    }
}
