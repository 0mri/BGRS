package bgu.spl.net.impl.BGRSServer.models.commands;

import bgu.spl.net.impl.BGRSServer.BGRSProtocol;
import bgu.spl.net.impl.BGRSServer.api.Command;
import bgu.spl.net.impl.BGRSServer.models.db.DatabaseError;

public class MYCOURSES extends Command {
    public MYCOURSES() {
        this.OPCODE = 11;
    }

    @Override
    public Command exec(BGRSProtocol bgrsProtocol) {
        String ans = "";
        try {
            ans = DB.getStudentCourses(bgrsProtocol.getUser()).toString();
        } catch (DatabaseError e) {

        }
        return new ACK(OPCODE, ans);
    }

}
