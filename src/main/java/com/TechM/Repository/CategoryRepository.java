package com.TechM.Repository;

import com.TechM.Model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category,String> {
    Optional<Category> findById(String category);

    Optional<Category> findByCategory(String category);
}
