package com.example.tring.user;

import androidx.annotation.NonNull;

import java.util.List;

public class User
{
    private static User instance = new User();
    private String id;
    private String name;
    private String gender;
    private String nickname;
    private String phoneNumber;
    private String signUpType;
    // EMAIL,GOOGLE,KAKAO,NAVER
    private String self_Introduction;

    private List<String> tripDestination;
    private List<String> tripStyle;

    private User () { }



    public static User getInstance() { return instance;}

    public void setId(String id) { instance.id = id;}
    public void setName(String name) { instance.name = name; }
    public void setGender(String gender) { instance.gender = gender; }
    public void setNickname(String nickname) { instance.nickname = nickname; }
    public void setPhoneNumber(String phoneNumber) { instance.phoneNumber = phoneNumber; }
    public void setSignUpType(String signUpType) { instance.signUpType = signUpType; }
    public void setSelf_Introduction(String self_Introduction) { instance.self_Introduction = self_Introduction; }
    public void setTripDestination(List<String> tripDestination) { instance.tripDestination = tripDestination; }
    public void setTripStyle(List<String> tripStyle) { instance.tripStyle = tripStyle; }

    public String getId() {
        return instance.id;
    }

    public String getName() {
        return instance.name;
    }

    public String getGender() {
        return instance.gender;
    }

    public String getNickname() {
        return instance.nickname;
    }

    public String getPhoneNumber() {
        return instance.phoneNumber;
    }

    public String getSignUpType() {
        return instance.signUpType;
    }

    public String getSelf_Introduction() {
        return instance.self_Introduction;
    }

    public List<String> getTripDestination() {
        return instance.tripDestination;
    }

    public List<String> getTripStyle() {
        return instance.tripStyle;
    }

    @NonNull
    @Override
    public String toString()
    {
        return super.toString();
    }
}
