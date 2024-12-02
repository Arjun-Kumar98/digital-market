package com.digitalmarket.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.*;
import com.digitalmarket.model.*;
public interface BasketRepository extends JpaRepository<BasketEntity,Integer> {
	
	public BasketEntity getByCartId(Integer cartId);
	
	public List<BasketEntity> findByCartIdCartId(Integer cartId);
	public BasketEntity findByCartIdCartIdAndProductIdProductId(Integer cartId,Integer productId);
	public List<BasketEntity> findAll();
	public void deleteByProductId(Integer productId);

}
