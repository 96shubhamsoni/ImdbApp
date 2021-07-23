package com.example.contactapp.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ContactBody {

    @SerializedName("meta")
    public Meta meta;

    @SerializedName("content")
    public List<ContactInfo> contactInfoList = new ArrayList<>();

    public class Meta{
        @SerializedName("success")
        public boolean success;

        @SerializedName("message")
        public String message;

        @SerializedName("pageNumber")
        public int pageNumber;

        @SerializedName("pageSize")
        public int pageSize;
    }

    public static class ContactInfo {
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
        public int isStarred;

        @Override
        public String toString() {
            return "ContactInfo{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", phone='" + phone + '\'' +
                    ", thumbnail='" + thumbnail + '\'' +
                    ", email='" + email + '\'' +
                    ", isStarred=" + isStarred +
                    '}';
        }

    }
}
