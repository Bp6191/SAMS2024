package com.sams.dao;

public interface Account_D {
    public String  getId();
    public  void setId(String id);
    public String getRole();

    public void setRole(String role);

    public  String getDisplayName();

    public void setDisplayName(String  name);

    public  void setPassword (String password);
    public boolean  matchPassword(String pass);
    public String setSession_key(String pass);
    public boolean matchSession_key(String key);
}
