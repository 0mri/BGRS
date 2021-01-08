package bgu.spl.net.impl.BGRSServer.models.commands;

import bgu.spl.net.impl.BGRSServer.BGRSProtocol;
import bgu.spl.net.impl.BGRSServer.api.Command;
import bgu.spl.net.impl.BGRSServer.api.Request;
import bgu.spl.net.impl.BGRSServer.models.db.DatabaseError;

public class ISREGISTERED extends Request {
    private int course_id;

    public ISREGISTERED(short opcode) {
        this.OPCODE = opcode;
    }

    @Override
    public Command exec(BGRSProtocol bgrsProtocol) {
        boolean ans;
        try {
            ans = DB.isRegistered(bgrsProtocol.getUser(), course_id);
        } catch (DatabaseError e) {
            return new ERR(OPCODE);
        }
        if (ans)
            return new ACK(OPCODE, "REGISTERED");
        return new ACK(OPCODE, "NOT REGISTERED");
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
