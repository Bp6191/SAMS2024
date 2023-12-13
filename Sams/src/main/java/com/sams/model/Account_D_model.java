package com.sams.model;

import com.sams.dao.Account_D;
import jakarta.persistence.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.security.SecureRandom;
import java.util.Base64;

@Entity
@Table(name = "account")
public class Account_D_model implements Account_D {
    @Id
    private String id;

    @Column(name = "role")
    private String role;

    @Column(name = "display_name")
    private String display_name;

    @Column(name = "password_hash")
    private String password;

    @Column(name = "session_key")
    private String session_key;

    private static final BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
    private static final SecureRandom secureRandom = new SecureRandom();
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder();
    private static final int token_len = 128;

    public Account_D_model() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getDisplayName() {
        return display_name;
    }

    public void setDisplayName(String display_name) {
        this.display_name = display_name;
    }

    public void setPassword(String password) {
        this.password = bc.encode(password);
    }

    public boolean matchPassword(String password) {
        System.out.println(password);
        return bc.matches(password, this.password);
    }
    public String setSession_key(String password) {
        byte[] randomBytes = new byte[token_len];
        secureRandom.nextBytes(randomBytes);
        this.session_key = base64Encoder.encodeToString(randomBytes);
        return this.session_key;
    }
    public boolean matchSession_key(String session_key) {
        return session_key.equals(this.session_key);
    }
}