package com.sams.controller;

import com.sams.exception.ResourceNotFound_exp;
import com.sams.model.Account_D_model;
import com.sams.model.Paper_D_model;
import com.sams.repository.AccountRepo;
import com.sams.repository.PaperRepo;
import com.sams.request.PaperRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
@RequestMapping("/api/v1/")
public class Paper {
    @Autowired
    private PaperRepo paperRepo;

    @Autowired
    private AccountRepo accountRepo;

    // API get all papers in the system
    @GetMapping("/papers/all")
    public List<Paper_D_model> gelAllPapers() {
        return paperRepo.findAll();
    }

    // API to get all papers of the specified author
    @GetMapping("/papers/author/{author_id}")
    public List<Paper_D_model> getAllAuthorPapers(@PathVariable String author_id) {
        List<Paper_D_model> allPaperModels = paperRepo.findAll();
        Account_D_model authorToSearch = accountRepo.findById(author_id)
                .orElseThrow(() -> new ResourceNotFound_exp(
                        "Error: account does not exist. Account ID: " + author_id
                ));
        allPaperModels.removeIf(paper -> !paper.getAuthors().contains(authorToSearch));

        return allPaperModels;
    }

    // API to create a new paper
    @PostMapping("/papers/create")
    public ResponseEntity<Paper_D_model> createPaper(@RequestBody PaperRequest paperRequestParams) {
        Paper_D_model paperModel = createPaperObject(
                paperRequestParams.title,
                paperRequestParams.summary,
                paperRequestParams.path,
                paperRequestParams.ids
        );
        this.paperRepo.save(paperModel);

        return ResponseEntity.ok(paperModel);
    }

    // API to update a paper by creating a new PaperModel and linking it to a previous version
    @PutMapping("/papers/update/{paper_id}")
    public ResponseEntity<Paper_D_model> updatePaper(
            @PathVariable String paper_id,
            @RequestBody PaperRequest paperRequestParams
    ) {
        Paper_D_model oldPaperModel = paperRepo.findById(paper_id)
                .orElseThrow(() -> new ResourceNotFound_exp(
                        "Error: paper does not exist. Paper ID: " + paper_id
                ));

        Paper_D_model newPaperModel = createPaperObject(
                paperRequestParams.title,
                paperRequestParams.summary,
                paperRequestParams.path,
                paperRequestParams.ids
        );
        newPaperModel.setPreviousId(oldPaperModel.getId());
        paperRepo.save(newPaperModel);

        return ResponseEntity.ok(newPaperModel);
    }

    // API to delete a paper by ID
    @DeleteMapping("/papers/delete/{paper_id}")
    public ResponseEntity<Map<String, Boolean>> deletePaper(@PathVariable String paper_id) {
        Paper_D_model paperModelToDelete = paperRepo.findById(paper_id)
                .orElseThrow(() -> new ResourceNotFound_exp(
                        "Error: paper does not exist. Paper ID: " + paper_id
                ));
        paperRepo.delete(paperModelToDelete);

        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);

        return ResponseEntity.ok(response);
    }

    // create a new PaperModel object
    private Paper_D_model createPaperObject(
            String title, String summary, String path, String[] ids
    ) {
        Paper_D_model paperModel = new Paper_D_model(title, summary, path);
        List<Account_D_model> authors = accountRepo.findAllById(List.of(ids));
        paperModel.addAuthors(authors);

        return paperModel;
    }
}
