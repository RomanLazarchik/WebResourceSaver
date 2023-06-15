package com.example.webresourcesaver.repository;

import com.example.webresourcesaver.model.Resource;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResourceRepository extends JpaRepository<Resource, Long> {
}
