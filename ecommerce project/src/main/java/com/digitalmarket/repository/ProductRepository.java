package com.digitalmarket.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.*;
import com.digitalmarket.model.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
public interface ProductRepository extends JpaRepository<ProductEntity,Integer>{
List<ProductEntity> findByProductNameIgnoreCaseOrCategoryIgnoreCase(String productName,String Category);

Page<ProductEntity> findByPriceBetween(Double startprice,Double endprice,Pageable pageable);
}
