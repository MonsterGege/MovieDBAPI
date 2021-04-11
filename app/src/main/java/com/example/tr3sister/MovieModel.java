package com.example.tr3sister;

public class MovieModel {

    String title;
    String rating;
    String img;
    String overview;
    String release_date;

    public MovieModel(){
        this.title = title;
        this.rating = rating;
        this.img = img;
        this.overview = overview;
        this.release_date = release_date;

    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
