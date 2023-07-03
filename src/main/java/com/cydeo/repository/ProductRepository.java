package com.cydeo.repository;

import com.cydeo.entity.Category;
import com.cydeo.entity.Company;
import com.cydeo.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAll();
    List<Product> findAllByIsDeletedOrderByCategoryAscNameAsc(boolean logic);
//    List<Product> findAllByCompany(Company company);


}
