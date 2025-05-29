package com.TechM.Repository;

import com.TechM.Model.Course;
import com.TechM.Model.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CourseRepository extends JpaRepository<Course, Long> {
    Course findByCourseTitle(String title);
    List<Course> findByCategory(String category);
    List<Course> findByCourseTitleContainingIgnoreCase(String courseTitle);
    boolean existsByCategory(String category);
    List<Course> findByIsactiveTrue();
    List<Course> findByIsactiveFalse();

    boolean existsByCourseTitle(String courseTitle);
}
