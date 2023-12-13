package com.sams.controller;

import com.sams.exception.ResourceNotFound_exp;
import com.sams.model.PaperReview_D_model;
import com.sams.repository.AccountRepo;
import com.sams.repository.PaperReviewRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/")
public class PaperReview {

    @Autowired
    private PaperReviewRepo paperReviewRepo;

    @Autowired
    private AccountRepo accountRepo;

    // Get all paper reviews
    @GetMapping("/paper_reviews")
    public List<PaperReview_D_model> getAllPaperReviews() {
        return paperReviewRepo.findAll();
    }

    // Create paper review rest api
    @PostMapping("/paper_reviews")
    public PaperReview_D_model createPaperReview(@RequestBody PaperReview_D_model paperReviewModel) {
        return this.paperReviewRepo.save(paperReviewModel);
    }

    // Get paper review by id rest api
    @GetMapping("/paper_reviews/{id}")
    public ResponseEntity<PaperReview_D_model> getPaperReviewById(@PathVariable String id) {
        PaperReview_D_model paperReviewModel = this.paperReviewRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFound_exp("PaperReview not exist with id :" + id));
        return ResponseEntity.ok(paperReviewModel);
    }

    // Update paper review rest api
    @PutMapping("/paper_reviews/{id}")
    public ResponseEntity<PaperReview_D_model> updatePaperReview(@PathVariable String id, @RequestBody PaperReview_D_model paperReviewModelDetails) {
        PaperReview_D_model paperReviewModel = this.paperReviewRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFound_exp("PaperReview not exist with id :" + id));

        paperReviewModel.setAuthor(paperReviewModelDetails.getAuthor());
        // Since Paper is commented out, we're not setting it here
        paperReviewModel.setContents(paperReviewModelDetails.getContents());

        PaperReview_D_model updatedPaperReviewModel = this.paperReviewRepo.save(paperReviewModel);
        return ResponseEntity.ok(updatedPaperReviewModel);
    }

    // Delete paper review rest api
    @DeleteMapping("/paper_reviews/{id}")
    public ResponseEntity<Map<String, Boolean>> deletePaperReview(@PathVariable String id) {
        PaperReview_D_model paperReviewModel = this.paperReviewRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFound_exp("PaperReview not exist with id :" + id));

        this.paperReviewRepo.delete(paperReviewModel);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}
