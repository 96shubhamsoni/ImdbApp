package com.example.contactapp.model;


import com.google.gson.annotations.SerializedName;

public class StarResponse
{
    @SerializedName("meta")
    public  Meta meta;

    @SerializedName("content")
    public Content content;


    public class Meta
    {
        @SerializedName("success")
        public boolean success;

        @SerializedName("message")
        public String message;
    }

    public class Content
    {
        @SerializedName("updatedContactInfo")
        public UpdatedContactInfo updatedContactInfo;

    }


    public class UpdatedContactInfo
    {
        @SerializedName("id")
        public int id;

        @SerializedName("name")
        public String name;

        @SerializedName("phone")
        public String phone;

        @SerializedName("thumbnail")
        public String thumbnail;

        @SerializedName("email")
        public String email;

        @SerializedName("isStarred")
        public boolean isStarred;

    }

}

