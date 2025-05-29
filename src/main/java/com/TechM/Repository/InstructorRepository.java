package com.TechM.Repository;

import com.TechM.Model.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InstructorRepository extends JpaRepository<Instructor, Long> {

    List<Instructor> findAllById(Long id);
  //  @Query("SELECT DISTINCT i FROM Instructor i JOIN i.courses c WHERE c.category = :category")
  @Query(
          value = "SELECT DISTINCT i.* FROM instructor i " +
                  "JOIN instructor_course ic ON i.id = ic.instructor_id " +
                  "JOIN course c ON c.course_id = ic.course_id " +
                  "WHERE c.category = :category",
          nativeQuery = true
  )
  List<Instructor> findInstructorByCourseCategoryNative(@Param("category") String category);

}
