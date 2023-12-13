package com.sams.repository;

import com.sams.model.PaperAssignment_D_model;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaperAssignmentRepo extends JpaRepository<PaperAssignment_D_model, String> {}

