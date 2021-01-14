package com.roshan1dot8.springbatch.repository;

import com.roshan1dot8.springbatch.model.EnterpriseSurveyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EnterpriseSurveyRepository extends JpaRepository<EnterpriseSurveyEntity, UUID> {
}
