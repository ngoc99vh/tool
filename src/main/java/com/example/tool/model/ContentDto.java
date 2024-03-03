package com.example.tool.model;

import java.util.List;

public class ContentDto {
    private String userName;
    private String password;

    public List<String> getProxy() {
        return proxy;
    }

    public void setProxy(List<String> proxy) {
        this.proxy = proxy;
    }

    private List<String> proxy;
    private List<Contents> contents;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Contents> getContents() {
        return contents;
    }

    public void setContents(List<Contents> contents) {
        this.contents = contents;
    }

    public static class Contents{
        private String content;
        private String image;
        private String idGroup;

        public String getIdGroup() {
            return idGroup;
        }

        public void setIdGroup(String idGroup) {
            this.idGroup = idGroup;
        }

        public Contents(String content, String image, String idGroup) {
            this.content = content;
            this.image = image;
            this.idGroup = idGroup;
        }

        public Contents() {
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }
    }

}
