package com.baidao.tracker;

/**
 * Created by hexi on 16/2/19.
 */
public class Config {
    public static final class User {
        private String username;
        private Integer userType;
        private String cusUniqueId;
        private int company;
        private boolean login;

        public User(String username, Integer userType, String cusUniqueId, int company, boolean login) {
            this.username = username;
            this.userType = userType;
            this.cusUniqueId = cusUniqueId;
            this.company = company;
            this.login = login;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public Integer getUserType() {
            return userType;
        }

        public void setUserType(Integer userType) {
            this.userType = userType;
        }

        public String getCusUniqueId() {
            return cusUniqueId;
        }

        public void setCusUniqueId(String cusUniqueId) {
            this.cusUniqueId = cusUniqueId;
        }

        public int getCompany() {
            return company;
        }

        public void setCompany(int company) {
            this.company = company;
        }

        public boolean isLogin() {
            return login;
        }

        public void setLogin(boolean login) {
            this.login = login;
        }
    }

    private static User user;

    public static void setUser(User user) {
        Config.user = user;
    }

    public static User getUser() {
        return user;
    }
}
