package web.function.services;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import web.function.entity.Student;

import java.util.List;

@Component
public class StudentService {

    public String API_ENDPOINT = "api/v1/student";

    @Autowired
    private Environment env;

    public Student createStudent(Student student) throws JSONException {

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        JSONObject studentObject = new JSONObject();
        studentObject.put("firstName", student.getFirstName());
        studentObject.put("lastName", student.getLastName());
        studentObject.put("email", student.getEmail());

        HttpEntity<String> request =
                new HttpEntity<String>(studentObject.toString(), headers);

        return restTemplate.postForObject(env.getProperty("external_api") + API_ENDPOINT, request, Student.class);
    }

    public List<Student> listStudents() {

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return restTemplate.getForObject(env.getProperty("external_api") + API_ENDPOINT, List.class);
    }

    public Student getStudent(Integer studentId) {

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return restTemplate.getForObject(env.getProperty("external_api") + API_ENDPOINT + "/" + studentId, Student.class);
    }
}
