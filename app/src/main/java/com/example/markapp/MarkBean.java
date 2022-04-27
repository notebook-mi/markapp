package com.example.markapp;

import java.io.Serializable;

public class MarkBean implements Serializable {

    private String title;//标题
    private String time;//时间
    private String content;
    private int key;
    private int getItemType=0;


    public int getGetItemType() {
        return getItemType;
    }

    public void setGetItemType(int getItemType) {
        this.getItemType = getItemType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }
}
