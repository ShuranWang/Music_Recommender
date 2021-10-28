package au.edu.unsw.infs3634.musicrecommender.beans;


import java.io.Serializable;


public class ListBean implements Serializable {

    public String name, pic, people, score, cate, url;

    public ListBean(String name, String pic, String people, String score, String cate, String url) {
        this.name = name;
        this.pic = pic;
        this.people = people;
        this.score = score;
        this.cate = cate;
        this.url = url;
    }

    public ListBean() {
    }
}
