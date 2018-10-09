package com.jobo.myrxmvpbaseproject.entity;

import java.io.Serializable;

/**
 * 用户实体类
 */

public class UserBean implements Serializable {
    /**
     * userAcconut : admin
     * userImgUrl : http://localhost:8080//upload/1.jpg
     * userId : 9987ab1e3d5d11e8a5a7000c2982d30f
     * userName : 管理员
     * userOrgId: //组织id
     */

    private String userAcconut;
    private String userImgUrl;
    private String userId;
    private String userName;
    private String userOrgId;

    public String getUserAcconut() {
        return userAcconut;
    }

    public void setUserAcconut(String userAcconut) {
        this.userAcconut = userAcconut;
    }

    public String getUserImgUrl() {
        return userImgUrl;
    }

    public void setUserImgUrl(String userImgUrl) {
        this.userImgUrl = userImgUrl;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserOrgId() {
        return userOrgId;
    }

    public void setUserOrgId(String orgId) {
        userOrgId = orgId;
    }

    @Override
    public String toString() {
        return "UserBean{" +
                "userAcconut='" + userAcconut + '\'' +
                ", userImgUrl='" + userImgUrl + '\'' +
                ", userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", userOrgId='" + userOrgId + '\'' +
                '}';
    }
}
