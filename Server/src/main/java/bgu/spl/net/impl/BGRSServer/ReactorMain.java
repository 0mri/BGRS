package bgu.spl.net.impl.BGRSServer;

import bgu.spl.net.impl.BGRSServer.api.User;
import bgu.spl.net.impl.BGRSServer.api.User.Role;
import bgu.spl.net.impl.BGRSServer.models.course.Course;
import bgu.spl.net.impl.BGRSServer.models.db.*;
import bgu.spl.net.impl.BGRSServer.models.user.Student;
import bgu.spl.net.srv.Server;

public class ReactorMain {

    public static void main(String[] args) {
        Database db = Database.getInstance();
        try {
            db.registerUser("omri", "123", Role.Student);
        } catch (DatabaseError e) {
            e.printStackTrace();
        }
        try {
            User a = db.getUser("omri");
            System.out.println(a.isAdmin());
        } catch (DatabaseError e1) {
            System.out.println(e1);
        }
        try {
            User omri = db.getUser("omri");
            omri.login();
            db.courseReg(omri, db.getCourse(530));
            db.courseReg(omri, db.getCourse(912));
            db.courseReg(omri, db.getCourse(482));
        } catch (DatabaseError e1) {
            System.out.println(e1);
        }

        try {
            Server.reactor(Integer.parseInt(args[1]), Integer.parseInt(args[0]), // port
                    () -> new BGRSProtocol(), // protocol factory
                    () -> new BGRSEncoderDecoder() // message encoder decoder factory
            ).serve();
        } catch (ArrayIndexOutOfBoundsException e) {
            throw e;
        }

    }
}
