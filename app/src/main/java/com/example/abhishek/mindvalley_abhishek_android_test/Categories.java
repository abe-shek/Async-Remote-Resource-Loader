package com.example.abhishek.mindvalley_abhishek_android_test;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Categories {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("photo_count")
    @Expose
    private Integer photoCount;
    @SerializedName("links")
    @Expose
    private CategoryLinks categoryLinks;

    /**
     * @return The id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return The title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return The photoCount
     */
    public Integer getPhotoCount() {
        return photoCount;
    }

    /**
     * @param photoCount The photo_count
     */
    public void setPhotoCount(Integer photoCount) {
        this.photoCount = photoCount;
    }

    /**
     * @return The category links
     */
    public CategoryLinks getCategoryLinks() {
        return categoryLinks;
    }

    /**
     * @param categoryLinks The category links
     */
    public void setLinks(CategoryLinks categoryLinks) {
        this.categoryLinks = categoryLinks;
    }

}