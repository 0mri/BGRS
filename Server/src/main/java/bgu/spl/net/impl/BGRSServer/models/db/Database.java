package bgu.spl.net.impl.BGRSServer.models.db;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
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
    private ArrayList<Course> courses;

    // to prevent user from creating new Database
    private Database() {
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
    public boolean initialize(String coursesFilePath) {
        rwlock = new ReentrantReadWriteLock();
        rwlock.writeLock().lock();
        courses = new ArrayList<>();
        users = new HashMap<>();
        try {
            for (String line : Files.readAllLines(Paths.get(coursesFilePath))) {
                if (!line.isEmpty()) {
                    String[] course_string = line.split("\\|");
                    courses.add(new Course(course_string));
                }
            }
        } catch (IOException e) {
            return false;
        } finally {
            rwlock.writeLock().unlock();
        }
        return true;

    }

    public User getUser(String username) throws DatabaseError {
        // for (String user : this.users.keySet())
        // System.out.println(users.get(user).getUserName().equals("omri"));

        rwlock.readLock().lock();
        User user = users.get(username);
        rwlock.readLock().unlock();
        if (user != null)
            return user;
        throw new DatabaseError("user not exist!");
    }

    public boolean isRegistered(String username) throws DatabaseError {
        User user = users.get(username);
        return user != null;
    }

    public User registerUser(String username, String password, Role r) throws DatabaseError {
        rwlock.writeLock().lock();
        if (isRegistered(username)) {
            rwlock.writeLock().unlock();
            throw new DatabaseError("username already exist!");
        }
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
        rwlock.writeLock().unlock();
        return new_user;
    }

    public Course getCourse(int courseID) throws DatabaseError {
        rwlock.readLock().lock();
        Course c = null;
        for (Course course : courses) {
            if (course.getID() == courseID)
                c = course;
        }
        rwlock.readLock().unlock();
        if (c == null)
            throw new DatabaseError("course does not exist!");

        return c;
    }

    public void courseReg(User user, int courseID) throws DatabaseError {
        studentPermissions(user);
        Course course = getCourse(courseID);
        if (course.getEmptySlots() == 0)
            throw new DatabaseError("no slots!");
        if (isRegistered(user, courseID))
            throw new DatabaseError("already register to this course!");
        for (Integer kdamID : course.getKdams())
            if (!isRegistered(user, kdamID))
                throw new DatabaseError("missing kdam course " + kdamID);

        rwlock.writeLock().lock();
        course.getStudents().put(user.getUserName(), user);
        rwlock.writeLock().unlock();

    }

    public boolean isRegistered(User user, int courseID) throws DatabaseError {
        studentPermissions(user);
        Course course = getCourse(courseID);
        rwlock.readLock().lock();
        boolean ans = course.getStudents().containsKey(user.getUserName());
        rwlock.readLock().unlock();
        return ans;
    }

    public Course courseStatus(User user, int courseID) throws DatabaseError {
        adminPermissions(user);
        return getCourse(courseID);
    }

    public ArrayList<Integer> getStudentCourses(User user) throws DatabaseError {
        if (!user.isStudent())
            throw new DatabaseError("User is not student");
        ArrayList<Integer> student_courses = new ArrayList<>();
        for (Course course : courses)
            if (course.getStudents().containsKey(user.getUserName()))
                student_courses.add(course.getID());
        return student_courses;
    }

    public ArrayList<Integer> getKdams(int courseID) throws DatabaseError {
        ArrayList<Integer> kdams = new ArrayList<>();
        Set<Integer> c = getCourse(courseID).getKdams();
        for (Course course : courses)
            if (c.contains(course.getID()))
                kdams.add(course.getID());

        return kdams;
    }

    public String studentStatus(User user, String username) throws DatabaseError {
        adminPermissions(user);
        User u1 = getUser(username);
        if (!u1.isStudent())
            throw new DatabaseError("there is no courses for admin");
        String ans;
        ans = String.format("Student: %1$s \n", u1);
        ans += String.format("Courses: %1$s", getStudentCourses(u1).toString().replace(" ", ""));

        return ans;
    }

    public void unregisterFromCourse(User user, int course_id) throws DatabaseError {
        studentPermissions(user);
        Course course = getCourse(course_id);
        if (!isRegistered(user, course_id))
            throw new DatabaseError("you are not register to this course!");

        course.getStudents().remove(user.getUserName());
    }

    public User login(String username, String password) throws DatabaseError {
        User user = getUser(username);
        if (!user.validatePassword(password))
            throw new DatabaseError("passwords not match!");
        else if (user.isLoggedIn())
            throw new DatabaseError("user already logged in");
        user.login();
        return user;
    }

    public void logout(User user) throws DatabaseError {
        if (user == null)
            throw new DatabaseError("no user");
        if (!user.isLoggedIn())
            throw new DatabaseError("this user is not logged in!");
        user.logout();
    }

    private boolean adminPermissions(User u) throws DatabaseError {
        if (u == null)
            throw new DatabaseError("Permission Denied");
        if (!(u.isAdmin() && u.isLoggedIn()))
            throw new DatabaseError("Permission Denied");
        return true;
    }

    private boolean studentPermissions(User u) throws DatabaseError {
        if (u == null || !(u.isStudent() && u.isLoggedIn()))
            throw new DatabaseError("Permission Denied");
        return true;
    }
}
