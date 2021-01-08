package bgu.spl.net.impl.BGRSServer.models.commands;

import bgu.spl.net.impl.BGRSServer.BGRSProtocol;
import bgu.spl.net.impl.BGRSServer.api.Command;
import bgu.spl.net.impl.BGRSServer.api.Request;
import bgu.spl.net.impl.BGRSServer.api.User;
import bgu.spl.net.impl.BGRSServer.models.course.Course;
import bgu.spl.net.impl.BGRSServer.models.db.DatabaseError;

public class COURSESTAT extends Request {
    private int course_id;

    public COURSESTAT(short opcode) {
        this.OPCODE = opcode;
    }

    public void set(short courseNum) {
        this.course_id = courseNum;
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

    @Override
    public Command decode(byte nextByte) {
        pushByte(nextByte);
        if (len == 2) {
            this.course_id = bytesToShort(bytes);
            return this;
        }
        return null;

    }

}
