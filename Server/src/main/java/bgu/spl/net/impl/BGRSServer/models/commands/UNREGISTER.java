package bgu.spl.net.impl.BGRSServer.models.commands;

import bgu.spl.net.impl.BGRSServer.BGRSProtocol;
import bgu.spl.net.impl.BGRSServer.api.Command;
import bgu.spl.net.impl.BGRSServer.models.db.DatabaseError;

public class UNREGISTER extends Command {
    private int course_id;

    public UNREGISTER(int courseID) {
        this.OPCODE = 10;
        this.course_id = courseID;
    }

    @Override
    public Command exec(BGRSProtocol bgrsProtocol) {
        try {
            DB.unregisterFromCourse(bgrsProtocol.getUser(), course_id);
        } catch (DatabaseError e) {
            return new ERR(OPCODE);
        }
        return new ACK(OPCODE, "");
    }

}
