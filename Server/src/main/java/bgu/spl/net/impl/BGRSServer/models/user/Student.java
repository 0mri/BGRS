package bgu.spl.net.impl.BGRSServer.models.user;

import java.util.ArrayList;

import bgu.spl.net.impl.BGRSServer.api.User;
import bgu.spl.net.impl.BGRSServer.models.course.Course;

public class Student extends User {

    public Student(String username, String password, ArrayList<Course> courses) {
        super(username, password);
    }

    public Student(String username, String password) {
        super(username, password);
    }

    @Override
    public Role getRole() {
        return Role.Student;
    }

    @Override
    public boolean isAdmin() {
        return false;
    }

    @Override
    public boolean isStudent() {
        return true;
    }

    // public String toString() {
        
    //     // Student: hhhaddock

    //     // Courses: [42] // if the student hasn’t registered to any course yet, simply
    //     // print []
    // }
}
