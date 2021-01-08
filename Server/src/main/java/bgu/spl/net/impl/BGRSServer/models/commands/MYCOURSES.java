package bgu.spl.net.impl.BGRSServer.models.commands;

import bgu.spl.net.impl.BGRSServer.BGRSProtocol;
import bgu.spl.net.impl.BGRSServer.api.Command;
import bgu.spl.net.impl.BGRSServer.api.Request;
import bgu.spl.net.impl.BGRSServer.models.db.DatabaseError;

public class MYCOURSES extends Request {

    public MYCOURSES(short opcode) {
        this.OPCODE = opcode;
    }

    @Override
    public Command exec(BGRSProtocol bgrsProtocol) {
        if (bgrsProtocol.getUser() == null)
            return new ERR(OPCODE);

        String ans;
        try {
            ans = DB.getStudentCourses(bgrsProtocol.getUser()).toString().replace(" ", "");
        } catch (DatabaseError e) {
            return new ERR(OPCODE);
        }
        return new ACK(OPCODE, ans);
    }

    @Override
    public Command decode(byte nextByte) {
        return this;
    }

}
