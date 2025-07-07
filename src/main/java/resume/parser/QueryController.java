package resume.parser;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import resume.parser.Database.ResumeParserDao;

import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Controller
@RequestMapping("/query")
public class QueryController {

    @Autowired
    private ResumeParserDao resumeParserDao;

    @GetMapping("/")
    public String query() {
        return "query";
    }

    @GetMapping("/results")
    public String sqlResults() {
        return "sqlResults";
    }

    @PostMapping("/execute")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> executeQuery(@RequestBody Map<String, String> request) {
        try {
            String naturalLanguage = request.get("naturalLanguage");
            
            String sql = convertNaturalLanguageToSQL(naturalLanguage);
            
            List<Map<String, Object>> results = resumeParserDao.executeQuery(sql);
            
            // Create redirect URL with parameters
            StringBuilder redirectUrl = new StringBuilder("/query/results?");
            redirectUrl.append("sql=").append(URLEncoder.encode(sql, StandardCharsets.UTF_8));
            
            if (results != null && !results.isEmpty()) {
                redirectUrl.append("&results=").append(URLEncoder.encode(new com.fasterxml.jackson.databind.ObjectMapper().writeValueAsString(results), StandardCharsets.UTF_8));
            }
            
            Map<String, Object> response = new HashMap<>();
            response.put("redirectUrl", redirectUrl.toString());
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Error processing query: " + e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    private String convertNaturalLanguageToSQL(String naturalLanguage) {
        String query = naturalLanguage.toLowerCase();
        
        if (query.contains("all candidates") || query.contains("show me all")) {
            return "SELECT * FROM candidate";
        }
        else if (query.contains("count") || query.contains("how many")) {
            return "SELECT COUNT(*) as count FROM candidate";
        }
        else if (query.contains("name containing") || query.contains("name like")) {
            return "SELECT * FROM candidate WHERE full_name LIKE '%Aftab%'";
        }
        else if (query.contains("skills") || query.contains("skill")) {
            return "SELECT DISTINCT unnest(skills) as skill FROM candidate";
        }
        else {
            return "SELECT * FROM candidate";
        }
    }
} 