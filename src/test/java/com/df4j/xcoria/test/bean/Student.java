package com.df4j.xcoria.test.bean;

public class Student extends Person {
    private String course;

    public Student() {
    }

    public Student(String name, int age, String course) {
        super(name, age);
        this.course = course;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }
}
