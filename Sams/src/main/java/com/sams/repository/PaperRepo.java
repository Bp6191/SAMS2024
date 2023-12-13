package com.sams.repository;

import com.sams.model.Paper_D_model;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PaperRepo extends JpaRepository<Paper_D_model, String> {}
