package com.java.bootstrap;

import com.java.model.Course;
import com.java.service.CourseService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final CourseService courseService;

    public DataLoader(CourseService courseService) {

        this.courseService = courseService;
    }

    @Override
    public void run(String... args) throws Exception {

        Course course1 = new Course();
        course1.setCourseName("Computer Science");
        course1.setNumberOfStudents(0);

        courseService.save(course1);

        Course course2 = new Course();
        course2.setCourseName("Law");
        course2.setNumberOfStudents(0);

        courseService.save(course2);

    }
}
