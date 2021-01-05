package bgu.spl.net.impl.BGRSServer.models.commands;

import bgu.spl.net.impl.BGRSServer.BGRSProtocol;
import bgu.spl.net.impl.BGRSServer.api.Command;
import bgu.spl.net.impl.BGRSServer.models.db.DatabaseError;

public class KDAMCHECK extends Command {
    private int course_id;

    public KDAMCHECK(int courseID) {
        this.OPCODE = 6;
        this.course_id = courseID;
    }

    @Override
    public Command exec(BGRSProtocol bgrsProtocol) {
        String ans = "";
        try {
            ans = DB.getCourse(course_id).getKdams().toString();
        } catch (DatabaseError e) {

        }
        return new ACK(OPCODE, ans);
    }

}
