package com.digitalmarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.*;
import com.digitalmarket.model.*;

public interface UserRepository extends JpaRepository<UserEntity,Integer> {
	Optional<UserEntity> findByEmailId(String userName);
}
