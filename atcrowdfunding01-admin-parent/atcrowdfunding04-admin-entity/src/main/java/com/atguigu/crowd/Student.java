package com.atguigu.crowd;

import java.util.Map;

public class Student {
    private Integer stuId;
    private String stuName;
    private Address address;
    private Map<String,String> map;
    public Student() {
    }

    public Student(Integer stuId, String stuName, Address address, Map<String, String> map) {
        this.stuId = stuId;
        this.stuName = stuName;
        this.address = address;
        this.map = map;
    }

    public Integer getStuId() {
        return stuId;
    }

    public void setStuId(Integer stuId) {
        this.stuId = stuId;
    }

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }

    @Override
    public String toString() {
        return "Student{" +
                "stuId=" + stuId +
                ", stuName='" + stuName + '\'' +
                ", address=" + address +
                ", map=" + map +
                '}';
    }
}
