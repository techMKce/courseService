package com.TechM.Repository;

import com.TechM.Model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course,Long> {
    Course findByCourseTitle(String title);
    List<Course> findByCategory(String category);
    List<Course>  findByIsActiveTrue();
    List<Course>  findByIsActiveFalse();
    Optional<Course> searchCoursesByTitle(String title);

    List<Course> findByTitleContainingIgnoreCase(String title);

    
    //By Sanjay => I'm not sure about DB structure ,so i made a generic query
    @Query("SELECT c FROM Course c WHERE c.courseTitle LIKE CONCAT(:prefix, '%')")
    List<Course> findCoursesByPrefix(@Param("prefix") String prefix);


}
