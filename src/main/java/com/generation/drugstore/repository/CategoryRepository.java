package com.generation.drugstore.repository;

import com.generation.drugstore.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category,Long> {

    public List <Category> findAllByNameContainingIgnoreCase (
            @Param("type")String name);
}
