package com.sams.controller;

import com.sams.exception.InvalidRole_exp;
import com.sams.exception.ResourceNotFound_exp;
import com.sams.model.Account_D_model;
import com.sams.model.PaperAssignment_D_model;
import com.sams.model.Paper_D_model;
import com.sams.repository.AccountRepo;
import com.sams.repository.PaperAssignmentRepo;
import com.sams.repository.PaperRepo;
import jakarta.persistence.EntityNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/")
public class PaperAssignmentController {

    @Autowired
    private PaperAssignmentRepo paperAssignmentRepo;

    @Autowired
    private AccountRepo accountRepo;

    @Autowired
    private PaperRepo paperRepo;

    @GetMapping("/test")
    public String test() {
        return "test";
    }

    // Get all paper assignments
    @GetMapping("/paper_assignments")
    public List<PaperAssignment_D_model> getAllPaperAssignments() {
        return paperAssignmentRepo.findAll();
    }

    // Create paper assignment rest api
    @PostMapping("/paper_assignments")
    public PaperAssignment_D_model createPaperAssignment(@RequestBody PaperAssignment_D_model paperAssignmentModel) {
        try {
            if (paperAssignmentModel.getAssigner() == null || paperAssignmentModel.getAssignee() == null) {
                throw new InvalidRole_exp("Invalid assigner or assignee.");
            }
            String assignerId = paperAssignmentModel.getAssigner().getId();
            String assigneeId = paperAssignmentModel.getAssignee().getId();
            if (assignerId == null || assigneeId == null) {
                throw new InvalidRole_exp("Invalid assigner or assignee.");
            }
            if (paperAssignmentModel.getPaper() == null || paperAssignmentModel.getPaper().getId() == null) {
                throw new ResourceNotFound_exp("Invalid paper assignment.");
            }

            Account_D_model assigner = accountRepo.getReferenceById(assignerId);
            Account_D_model assignee = accountRepo.getReferenceById(assigneeId);
            Paper_D_model paperModel = this.paperRepo.getReferenceById(paperAssignmentModel.getPaper().getId());
            if (assigner == null || assignee == null
                    || !assigner.getRole().equalsIgnoreCase("pcc")
                    || !assignee.getRole().equalsIgnoreCase("pcm")) {
                throw new InvalidRole_exp("Invalid role, assigner must be PCC, assignee must be PCM");
            }

            if (paperModel == null) {
                throw new ResourceNotFound_exp("Paper not found");
            }

            paperAssignmentModel.setAssigner(assigner);
            paperAssignmentModel.setAssignee(assignee);
            paperAssignmentModel.setPaper(paperModel);
            paperAssignmentModel.setId(UUID.randomUUID().toString());
            paperAssignmentModel.setIsAccepted(false);
            return paperAssignmentRepo.save(paperAssignmentModel);
        } catch (EntityNotFoundException e) {
            throw new RuntimeException("Assigner not found or assignee not found");
        }
    }

    // Get paper assignment by id rest api
    @GetMapping("/paper_assignments/{id}")
    public ResponseEntity<PaperAssignment_D_model> getPaperAssignmentById(@PathVariable String id) {
        PaperAssignment_D_model paperAssignmentModel = paperAssignmentRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFound_exp("PaperAssignment not exist with id :" + id));
        return ResponseEntity.ok(paperAssignmentModel);
    }

    // Update paper assignment rest api
    @PutMapping("/paper_assignments/{id}")
    public ResponseEntity<PaperAssignment_D_model> updatePaperAssignment(@PathVariable String id, @RequestBody PaperAssignment_D_model paperAssignmentModelDetails) {
        PaperAssignment_D_model paperAssignmentModel = paperAssignmentRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFound_exp("PaperAssignment not exist with id :" + id));

        paperAssignmentModel.setIsAccepted(paperAssignmentModelDetails.getIsAccepted());

        PaperAssignment_D_model updatedPaperAssignmentModel = paperAssignmentRepo.save(paperAssignmentModel);

        return ResponseEntity.ok(updatedPaperAssignmentModel);
    }

    // Delete paper assignment rest api
    @DeleteMapping("/paper_assignments/{id}")
    public ResponseEntity<Map<String, Boolean>> deletePaperAssignment(@PathVariable String id) {
        PaperAssignment_D_model paperAssignmentModel = paperAssignmentRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFound_exp("PaperAssignment not exist with id :" + id));

        paperAssignmentRepo.delete(paperAssignmentModel);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}
