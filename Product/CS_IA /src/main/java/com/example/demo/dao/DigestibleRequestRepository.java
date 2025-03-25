package com.example.demo.dao;

import com.example.demo.entities.DigestibleRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DigestibleRequestRepository extends JpaRepository<DigestibleRequest,Integer>
{

}
