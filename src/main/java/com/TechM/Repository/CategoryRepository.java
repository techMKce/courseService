package com.TechM.Repository;

import com.TechM.Model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,String> {
}
