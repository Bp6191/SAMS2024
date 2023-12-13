package com.sams.repository;

import com.sams.model.Account_D_model;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepo extends JpaRepository<Account_D_model, String> {}
