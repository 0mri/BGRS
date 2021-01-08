package bgu.spl.net.impl.BGRSServer.models.course;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import bgu.spl.net.impl.BGRSServer.api.User;

public class Course {
    private final int courseNum;
    private final String courseName;
    private final Set<Integer> KdamCoursesList;
    private final int numOfMaxStudents;
    private HashMap<String, User> students;

    public Course(int id, String name, Set<Integer> kdams, int slots) {
        this.courseNum = id;
        this.courseName = name;
        this.KdamCoursesList = kdams;
        this.numOfMaxStudents = slots;
        this.students = new HashMap<>();
    }

    public Course(String[] course) {

        this.courseNum = Integer.parseInt(course[0]);
        this.courseName = course[1];
        this.numOfMaxStudents = Integer.parseInt(course[3]);
        String[] arr = course[2].replace("[", "").replace("]", "").split("\\s*,\\s*");
        Set<Integer> kd = new HashSet<>();
        for (String string : arr)
            if (!string.isEmpty())
                kd.add(Integer.parseInt(string));
        this.KdamCoursesList = kd;
        this.students = new HashMap<>();
    }

    public int getEmptySlots() {
        return numOfMaxStudents - students.size();
    }

    public HashMap<String, User> getStudents() {
        return students;
    }

    public int getID() {
        return this.courseNum;
    }

    public Set<Integer> getKdams() {
        return this.KdamCoursesList;
    }

    public String toString() {
        String ans = String.format("Course: (%1$s) %2$s \n", courseNum, courseName);
        ans += String.format("Seats Available: %1$s/%2$s \n", getEmptySlots(), numOfMaxStudents);
        ans += String.format("Students Registered: %1$s", this.students.keySet().toString().replace(" ", ""));
        return ans;
    }
}
