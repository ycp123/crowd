package com.atguigu.crowd;

import java.util.List;

public class Address {
    private String province;
    private String city;
    private String street;
    private List<Subject> subjectList;

    public Address() {
    }

    public Address(String province, String city, String street, List<Subject> subjectList) {
        this.province = province;
        this.city = city;
        this.street = street;
        this.subjectList = subjectList;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public List<Subject> getSubjectList() {
        return subjectList;
    }

    public void setSubjectList(List<Subject> subjectList) {
        this.subjectList = subjectList;
    }

    @Override
    public String toString() {
        return "Address{" +
                "province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", subjectList=" + subjectList +
                '}';
    }
}
