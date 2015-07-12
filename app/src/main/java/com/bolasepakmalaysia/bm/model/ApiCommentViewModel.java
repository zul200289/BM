package com.bolasepakmalaysia.bm.model;

/**
 * Created by zul on 21-Jan-15.
 */
public class ApiCommentViewModel {

    private String body;
    private int id;
    private String userid;
    private String username;
    private String rouserimage;

    public ApiCommentViewModel(int id, String username, String body, String userid, String rouserimage){
        this.id = id;
        this.userid = userid;
        this.username = username;
        this.body = body;
        this.rouserimage = rouserimage;
    }

    public int getId(){
        return this.id;
    }

    public String getUsername(){
        return this.username;
    }

    public String getBody(){
        return this.body;
    }

    public String getUserId(){
        return this.userid;
    }

    public String getRouserimage(){
        return this.rouserimage;
    }

    public String getRouserimagecleaned() {
        if (getRouserimage().startsWith("avatar")){
            return "http://www.bolasepakmalaysia.com/content/images/avatar/" + this.rouserimage;
        } else if ( getRouserimage().startsWith("/content") ) {
            return "http://www.bolasepakmalaysia.com" + this.rouserimage;
        }
        return this.rouserimage;
    }
}
