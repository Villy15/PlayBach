package com.example.s15.campanilla.villanueva.playbach.Classes;

public class Songs {
    private String title;
    private String youtubeUrl;
    private String thumbnail;
    private String contributor;

    // No-argument constructor
    public Songs() {
    }


    public Songs(String title,  String youtubeUrl, String thumbnail, String contributor) {
        this.title = title;
        this.youtubeUrl = youtubeUrl;
        this.thumbnail = thumbnail;
        this.contributor = contributor;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYoutubeUrl() {
        return youtubeUrl;
    }

    public void setYoutubeUrl(String youtubeUrl) {
        this.youtubeUrl = youtubeUrl;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getContributor() {
        return contributor;
    }

    public void setContributor(String contributor) {
        this.contributor = contributor;
    }
}
