package com.sams.model;

import com.sams.dao.PaperAssignment_D;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "paper_assignment")
public class PaperAssignment_D_model implements PaperAssignment_D {

    @Id
    private String id;

    @ManyToOne
    @JoinColumn(name = "paper_id", referencedColumnName = "id")
    private Paper_D_model paperModel;

    @ManyToOne
    @JoinColumn(name = "assigner_id", referencedColumnName = "id")
    private Account_D_model assigner;

    @ManyToOne
    @JoinColumn(name = "assignee_id", referencedColumnName = "id")
    private Account_D_model assignee;

    @Column(name = "is_accepted")
    private boolean isAccepted;

    public PaperAssignment_D_model() {
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
    public Paper_D_model getPaper() {
        return paperModel;
    }

    @Override
    public void setPaper(Paper_D_model paperModel) {
        this.paperModel = paperModel;
    }

    public Account_D_model getAssigner() {
        return assigner;
    }

    public void setAssigner(Account_D_model assigner) {
        this.assigner = assigner;
    }

    public Account_D_model getAssignee() {
        return assignee;
    }

    public void setAssignee(Account_D_model assignee) {
        this.assignee = assignee;
    }

    public Boolean getIsAccepted() {
        return isAccepted;
    }

    public void setIsAccepted(Boolean isAccepted) {
        this.isAccepted = isAccepted;
    }
}
