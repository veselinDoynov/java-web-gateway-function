package web.function;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.HttpClientErrorException;
import web.function.entity.Student;
import web.function.services.StudentService;

@SpringBootApplication
public class WebFunctionApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebFunctionApplication.class, args);
    }

    @Autowired
    private StudentService studentService;

    @Bean
    public Function<Student, Student> createStudent() {
        return student -> {
            try {
                return studentService.createStudent(student);
            } catch (JSONException | HttpClientErrorException e) {
                e.printStackTrace();
                return null;
            }
        };
    }

    @Bean
    public Function<Integer, Student> getStudent() {

        try {
            return studentId -> studentService.getStudent(studentId);
        } catch (HttpClientErrorException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Bean
    public Supplier<List<Student>> listStudents() {

        try {
            return () -> studentService.listStudents();
        } catch (HttpClientErrorException e) {
            e.printStackTrace();
            return null;
        }
    }
}
