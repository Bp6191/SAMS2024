package com.sams.dao;

import com.sams.model.Account_D_model;
import com.sams.model.Paper_D_model;

public interface PaperAssignment_D {
    String getId();
    void setId(String id);

    Paper_D_model getPaper();

    void setPaper(Paper_D_model paperModel);

    Account_D_model getAssigner();

    void setAssigner(Account_D_model assigner);

    Account_D_model getAssignee();

    void setAssignee(Account_D_model assignee);

    Boolean getIsAccepted();

    void setIsAccepted(Boolean isAccepted);

}

