package com.bolasepakmalaysia.bm.model;

import java.util.List;

/**
 * Created by zul on 21-Jan-15.
 */
public class ApiPostViewModel {

    private String article;
    private List<ApiCommentViewModel> comments;
    private int id;
    private String imageurl;
    private String summary;
    private String title;

    public ApiPostViewModel(){

    }

    public ApiPostViewModel(String article, List<ApiCommentViewModel> comments, int id,
                            String imageurl, String summary, String title){
        this.article = article;
        this.comments = comments;
        this.id = id;
        this.imageurl = imageurl;
        this.summary = summary;
        this.title = title;
    }

    public String getArticle(){ return this.article; }
    public List<ApiCommentViewModel> getComments(){ return this.comments; }
    public int getId() { return this.id; }
    public String getImageUrl(){ return this.imageurl; }
    public String getImageUrlCleaned(){
        if ( this.imageurl.startsWith("/")){
            return "http://www.bolasepakmalaysia.com" + this.imageurl;
        }
        return this.imageurl;
    }
    public String getSummary(){ return this.summary; }
    public String getTitle(){ return this.title; }

    public void setArticle(String article) {
        this.article = article;
    }

    public void setComments(List<ApiCommentViewModel> comments) {
        this.comments = comments;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
