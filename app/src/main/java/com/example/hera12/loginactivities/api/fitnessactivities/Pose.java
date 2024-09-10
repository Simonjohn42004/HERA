package com.example.hera12.loginactivities.api.fitnessactivities;

import com.google.gson.annotations.SerializedName;

public class Pose {

    @SerializedName("english_name")
    private String name;
    @SerializedName("pose_description")
    private String description;
    @SerializedName("url_png")
    private String imageUrl;

    public Pose(String name, String description, String imageUrl) {
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }


    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
