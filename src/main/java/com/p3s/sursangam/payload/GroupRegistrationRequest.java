package com.p3s.sursangam.payload;

public class GroupRegistrationRequest {
    private Long userId;
    private String groupType;


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getGroupType() {
        return groupType;
    }

    public void setGroupType(String groupType) {
        this.groupType = groupType;
    }
}
