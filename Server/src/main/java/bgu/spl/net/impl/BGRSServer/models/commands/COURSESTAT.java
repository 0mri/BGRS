package bgu.spl.net.impl.BGRSServer.models.commands;

import bgu.spl.net.impl.BGRSServer.BGRSProtocol;
import bgu.spl.net.impl.BGRSServer.api.Command;
import bgu.spl.net.impl.BGRSServer.api.User;
import bgu.spl.net.impl.BGRSServer.models.course.Course;
import bgu.spl.net.impl.BGRSServer.models.db.DatabaseError;

public class COURSESTAT extends Command {
    private int course_id;

    public COURSESTAT(int courseID) {
        this.OPCODE = 7;
        this.course_id = courseID;
    }

    @Override
    public Command exec(BGRSProtocol bgrsProtocol) {
        User req_user = bgrsProtocol.getUser();
        Course c;
        try {
            c = DB.courseStatus(req_user, course_id);
        } catch (DatabaseError e) {
            return new ERR(OPCODE);
        }
        return new ACK(OPCODE, c.toString());
    }

}
