package model;

import static constant.UserConstant.USER_DEFAULT_MONEY;

public class User {
    private final String name;
    private final String phoneNumber;
    private final String userId;
    private final String userPw;
    private int userMoney = USER_DEFAULT_MONEY;

    public User(String name, String phoneNumber, String userId, String userPw) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.userId = userId;
        this.userPw = userPw;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserPw() {
        return userPw;
    }

    public int getUserMoney() {
        return userMoney;
    }

    public void setUserMoney(int userMoney) {
        this.userMoney = userMoney;
    }
}
