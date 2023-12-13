package com.sams.dao;

import com.sams.model.Account_D_model;
import com.sams.model.Paper_D_model;

public interface PaperReview_D {
    String getId();
    void setId(String id);

    Account_D_model getAuthor();
    void setAuthor(Account_D_model author);

    Paper_D_model getPaper();
    void setPaper(Paper_D_model paperModel);

    String getContents();
    void setContents(String contents);

}

