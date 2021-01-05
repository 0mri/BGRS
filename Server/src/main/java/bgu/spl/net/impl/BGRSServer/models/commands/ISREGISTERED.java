package bgu.spl.net.impl.BGRSServer.models.commands;

import bgu.spl.net.impl.BGRSServer.BGRSProtocol;
import bgu.spl.net.impl.BGRSServer.api.Command;
import bgu.spl.net.impl.BGRSServer.models.db.DatabaseError;

public class ISREGISTERED extends Command {
    private int course_id;

    public ISREGISTERED(int courseID) {
        this.OPCODE = 9;
        this.course_id = courseID;
    }

    @Override
    public Command exec(BGRSProtocol bgrsProtocol) {
        boolean ans;
        try {
            ans = DB.isRegistered(bgrsProtocol.getUser(), course_id);
        } catch (DatabaseError e) {
            return new ACK(OPCODE, "NOT REGISTERED");
        }
        if (ans)
            return new ACK(OPCODE, "REGISTERED");
        return new ACK(OPCODE, "NOT REGISTERED");
    }

}
