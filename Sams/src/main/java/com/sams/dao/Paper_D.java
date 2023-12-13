package com.sams.dao;

import com.sams.model.Account_D_model;

import java.util.List;


public interface Paper_D {
    String getId();


    String getTitle();


    String getSummary();


    String getFilepath();


    String getPreviousId();

    void addAuthors(List<Account_D_model> a);

    List<Account_D_model> getAuthors();

    void setPreviousId(String previous);
}
