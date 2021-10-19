package com.heaerie.sparrow.service;

import java.util.Date;

public class AuthToken {
    String userId;
    String role;
    Date expire;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Date getExpire() {
        return expire;
    }

    public void setExpire(Date expire) {
        this.expire = expire;
    }

    @Override
    public String toString() {
        return "AuthToken {" +
                "userId='" + userId + '\'' +
                ", role='" + role + '\'' +
                ", expire=" + expire +
                '}';
    }
}
