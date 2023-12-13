package com.sams.model;

import com.sams.dao.PaperReview_D;
import jakarta.persistence.*;

@Entity
@Table(name = "paper_review")
public class PaperReview_D_model implements PaperReview_D {

    @Id
    private String id;

    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private Account_D_model author;

    @ManyToOne
    @JoinColumn(name = "reviewing_paper", referencedColumnName = "id")
    private Paper_D_model paperModel;

    @Column(name = "contents")
    private String contents;

    public PaperReview_D_model() {
    }
    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public Account_D_model getAuthor() {
        return author;
    }

    @Override
    public void setAuthor(Account_D_model author) {
        this.author = author;
    }

    @Override
    public Paper_D_model getPaper() {
        return paperModel;
    }

    @Override
    public void setPaper(Paper_D_model paperModel) {
        this.paperModel = paperModel;
    }

    @Override
    public String getContents() {
        return contents;
    }

    @Override
    public void setContents(String contents) {
        this.contents = contents;
    }
}
