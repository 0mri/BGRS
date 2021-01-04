package bgu.spl.net.impl.BGRSServer.models.db;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import bgu.spl.net.impl.BGRSServer.api.User;
import bgu.spl.net.impl.BGRSServer.api.User.Role;
import bgu.spl.net.impl.BGRSServer.models.course.Course;
import bgu.spl.net.impl.BGRSServer.models.user.Admin;
import bgu.spl.net.impl.BGRSServer.models.user.Student;

/**
 * Passive object representing the Database where all courses and users are
 * stored.
 * <p>
 * This class must be implemented safely as a thread-safe singleton. You must
 * not alter any of the given public methods of this class.
 * <p>
 * You can add private fields and methods to this class as you see fit.
 */
public class Database {
    private ReadWriteLock rwlock;

    private static class DatabaseHolder {
        private static final Database db = new Database();
    }

    private HashMap<String, User> users;
    private HashMap<Integer, Course> courses;

    // to prevent user from creating new Database
    private Database() {
        rwlock = new ReentrantReadWriteLock();
        initialize("./Courses.txt");
    }

    /**
     * Retrieves the single instance of this class.
     */
    public static Database getInstance() {
        return DatabaseHolder.db;
    }

    /**
     * loades the courses from the file path specified into the Database, returns
     * true if successful.
     */
    boolean initialize(String coursesFilePath) {
        rwlock.writeLock().lock();
        courses = new HashMap<>();
        users = new HashMap<>();
        User u1 = new Admin("Ahii", "1234");
        System.out.println(u1.getUserName());
        try {
            for (String line : Files.readAllLines(Paths.get(coursesFilePath))) {
                String[] course_string = line.split("\\|");
                courses.putIfAbsent(Integer.parseInt(course_string[0]), new Course(course_string));
            }
        } catch (IOException e) {
            return false;
        } finally {
            rwlock.writeLock().unlock();
        }
        return true;

    }

    public User getUser(String username) throws DatabaseError {
        rwlock.readLock().lock();
        User user = users.get(username);
        rwlock.readLock().unlock();
        if (user != null)
            return user;
        throw new DatabaseError("user not exist!");
    }

    public boolean isRegistered(String username) throws DatabaseError {
        rwlock.readLock().lock();
        User user = users.get(username);
        rwlock.readLock().unlock();
        return user != null;
    }

    public void registerUser(String username, String password, Role r) throws DatabaseError {
        rwlock.writeLock().lock();
        if (isRegistered(username))
            throw new DatabaseError("username already exist!");
        User new_user;
        switch (r) {
            case Admin:
                new_user = new Admin(username, password);
                break;
            default:
                new_user = new Student(username, password);
                break;
        }
        users.put(username, new_user);
        System.out.println(username + " Registered! as " + r);
        rwlock.writeLock().unlock();
    }

    public Course getCourse(int courseID) throws DatabaseError {
        Course c = courses.get(courseID);
        if (c == null)
            throw new DatabaseError("course does not exist!");
        return c;
    }

    public void courseReg(User user, Course course) throws DatabaseError {
        if (!user.isLoggedIn())
            throw new DatabaseError("user is not logged in!");
        if (!user.isStudent())
            throw new DatabaseError("can't register admin to course!");
        if (course.getEmptySlots() == 0)
            throw new DatabaseError("no slots!");
        if (isRegistered(user, course))
            throw new DatabaseError("already register to this course!");
        for (Integer kdamID : course.getKdams())
            if (!isRegistered(user, courses.get(kdamID)))
                throw new DatabaseError("missing kdam course " + kdamID);

        rwlock.writeLock().lock();
        courses.get(course.getID()).getStudents().put(user.getUserName(), user);
        System.out.println(course);
        rwlock.writeLock().unlock();

    }

    public boolean isRegistered(User user, Course course) throws DatabaseError {
        rwlock.readLock().lock();
        boolean ans = course.getStudents().containsKey(user.getUserName());
        rwlock.readLock().unlock();
        return ans;
    }

    public Course courseStatus(User user, int courseID) throws DatabaseError {
        if (!user.isAdmin())
            throw new DatabaseError("Permission Denied");
        return courses.get(courseID);
    }

    public List<Integer> getStudentCourses(User user) throws DatabaseError {
        if (!user.isStudent())
            throw new DatabaseError("Permission Denied");
        List<Integer> t = new ArrayList<>();
        for (Integer courseID : courses.keySet())
            if (courses.get(courseID).getStudents().containsKey(user.getUserName()))
                t.add(courseID);
        return t;
    }

    public String studentStatus(User user) throws DatabaseError {
        if (!user.isLoggedIn())
            throw new DatabaseError("user is not logged in!");
        if (!user.isStudent())
            throw new DatabaseError("Permission Denied");
        String ans;
        ans = String.format("Student: %1$s\n", user.getUserName());
        ans += String.format("Courses: %1$s/%2$s \n", this.getStudentCourses(user));
        return ans;
    }

    public void unregisterFromCourse(User user, Course course) throws DatabaseError {
        if (!user.isLoggedIn())
            throw new DatabaseError("user is not logged in!");
        if (!user.isStudent())
            throw new DatabaseError("can't unregister admin to course!");
        if (!isRegistered(user, course))
            throw new DatabaseError("you are not register to this course!");

        course.getStudents().remove(user.getUserName());
    }

    public void logIn(String username, String password) throws DatabaseError {
        User user = getUser(username);
        if (!user.validatePassword(password))
            throw new DatabaseError("passwords not match!");
        else if (user.isLoggedIn())
            throw new DatabaseError("user already logged in");
        user.login();
    }

    public void logout(User user) throws DatabaseError {
        if (!user.isLoggedIn())
            throw new DatabaseError("this user is not logged in!");
        user.logout();
    }
}
