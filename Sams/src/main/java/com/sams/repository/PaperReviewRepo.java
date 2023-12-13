package com.sams.repository;

import com.sams.model.PaperReview_D_model;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaperReviewRepo extends JpaRepository<PaperReview_D_model, String> {}

