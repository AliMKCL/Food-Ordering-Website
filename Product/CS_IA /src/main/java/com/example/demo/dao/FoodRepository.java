package com.example.demo.dao;

import com.example.demo.entities.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface FoodRepository extends JpaRepository<Food,Integer> {

    @Query(value="SELECT * FROM digestibles WHERE name = ?1",nativeQuery = true)
    Food findByName(String name);

}
