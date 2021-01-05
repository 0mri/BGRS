package bgu.spl.net.impl.BGRSServer.models.commands;

import bgu.spl.net.impl.BGRSServer.BGRSProtocol;
import bgu.spl.net.impl.BGRSServer.api.Command;
import bgu.spl.net.impl.BGRSServer.api.User;
import bgu.spl.net.impl.BGRSServer.models.course.Course;
import bgu.spl.net.impl.BGRSServer.models.db.DatabaseError;

public class COURSEREG extends Command {
    private int course_id;

    public COURSEREG(int courseID) {
        this.OPCODE = 5;
        this.course_id = courseID;
    }

    @Override
    public Command exec(BGRSProtocol bgrsProtocol) {
        User user = bgrsProtocol.getUser();
        try {
            DB.courseReg(user, course_id);
        } catch (DatabaseError e) {
            return new ERR(OPCODE);
        }
        return new ACK(OPCODE, "");
    }

}
