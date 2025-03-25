package com.example.demo.dao;

import com.example.demo.entities.Digestible;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DigestibleRepository extends JpaRepository<Digestible,Integer> {

}
