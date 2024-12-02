package com.digitalmarket.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.*;
import com.digitalmarket.model.*;
public interface ProductRepository extends JpaRepository<ProductEntity,Integer>{
List<ProductEntity> findByProductNameOrCategory(String productName,String Category);

List<ProductEntity> findByPriceBetween(Double startprice,Double endprice);
}
