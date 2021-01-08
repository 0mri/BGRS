package bgu.spl.net.impl.BGRSServer.models.commands;

import bgu.spl.net.impl.BGRSServer.BGRSProtocol;
import bgu.spl.net.impl.BGRSServer.api.Command;
import bgu.spl.net.impl.BGRSServer.api.Request;
import bgu.spl.net.impl.BGRSServer.models.db.DatabaseError;

public class COURSEREG extends Request {
    private int course_id = 0;

    public COURSEREG(short opcode) {
        this.OPCODE = opcode;
    }

    public void set(short courseNum) {
        this.course_id = courseNum;
    }

    @Override
    public Command exec(BGRSProtocol bgrsProtocol) {
        try {
            DB.courseReg(bgrsProtocol.getUser(), course_id);
        } catch (DatabaseError e) {
            System.out.println(e);
            return new ERR(OPCODE);
        }
        return new ACK(OPCODE, "");
    }

    @Override
    public Command decode(byte nextByte) {
        pushByte(nextByte);
        if (len == 2) {
            set(bytesToShort(bytes));
            System.out.println("COURSE ID: " + this.course_id);
            return this;
        }

        return null;
    }

}
