package com.generation.drugstore.repository;

import com.generation.drugstore.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    public List <Product> findAllByNameContainingIgnoreCase(@Param("nome")String nome);

    public List <Product> findAllByNameAndLaboratory(String name, String laboratory);

    public List <Product> findAllByNameOrLaboratory(String name, String laboratory);

    List<Product> findAllByPriceBetween(BigDecimal start, BigDecimal end);


}
