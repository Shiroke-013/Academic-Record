package com.java.bootstrap;

import com.java.model.Course;
import com.java.model.Professor;
import com.java.services.CourseService;
import com.java.services.ProfessorService;
import com.java.services.map.CourseServiceMap;
import com.java.services.map.ProfessorServiceMap;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final CourseService courseService;
    private final ProfessorService professorService;

    public DataLoader() {
        courseService = new CourseServiceMap();
        professorService = new ProfessorServiceMap();
    }

    @Override
    public void run(String... args) throws Exception {

        Professor professor1 = new Professor();
        professor1.setId(1);
        professor1.setFirsName("Juan");
        professor1.setLastName("McCormick");

        professorService.save(professor1);

        Professor professor2 = new Professor();
        professor2.setId(2);
        professor2.setFirsName("Daniel");
        professor2.setLastName("Correa");

        professorService.save(professor2);

        System.out.println("Loaded Professors....");

        Course course1 = new Course();
        course1.setId(1);
        course1.setCourseName("Computer Science");
        course1.setNumberOfStudents(0);

        courseService.save(course1);

        Course course2 = new Course();
        course2.setId(2);
        course2.setCourseName("Law");
        course2.setNumberOfStudents(0);

        courseService.save(course2);

        System.out.println("Loaded Courses....");
    }
}
