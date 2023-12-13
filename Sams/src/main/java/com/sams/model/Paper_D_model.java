package com.sams.model;

import com.sams.dao.Paper_D;
import jakarta.persistence.*;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;


@Entity
@Table(name = "paper_model")
public class Paper_D_model implements Paper_D {
    @Id
    private String id;

    @Column(name = "title")
    private String title;

    @Column(name = "abstract_summary")
    private String abstractSummary;

    @Column(name = "filepath")
    private String path;

    @Column(name = "previous_version_id")
    private String previousId;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Account_D_model> authors;
    public Paper_D_model(
            String title,
            String abstract_summary,
            String filepath
    ) {
        super();
        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.abstractSummary = abstract_summary;
        this.path = filepath;
        this.previousId = null;
        this.authors = new LinkedList<>();
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public String getSummary() {
        return this.abstractSummary;
    }

    @Override
    public String getFilepath() {
        return this.path;
    }

    @Override
    public String getPreviousId() {
        return this.previousId;
    }

    @Override
    public void addAuthors(List<Account_D_model> authors) {
        this.authors.addAll(authors);
    }

    @Override
    public List<Account_D_model> getAuthors() {
        return authors;
    }

    @Override
    public void setPreviousId(String previousId) {
        this.previousId = previousId;
    }
}
