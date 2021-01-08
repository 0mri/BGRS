package bgu.spl.net.impl.BGRSServer.models.commands;

import bgu.spl.net.impl.BGRSServer.BGRSProtocol;
import bgu.spl.net.impl.BGRSServer.api.Command;
import bgu.spl.net.impl.BGRSServer.api.Request;
import bgu.spl.net.impl.BGRSServer.models.db.DatabaseError;

public class KDAMCHECK extends Request {
    private int course_id;

    public KDAMCHECK(short opcode) {
        this.OPCODE = opcode;
    }

    @Override
    public Command exec(BGRSProtocol bgrsProtocol) {
        String ans;
        try {
            ans = DB.getKdams(course_id).toString().replace(" ", "");
        } catch (DatabaseError e) {
            return new ERR(OPCODE);
        }
        return new ACK(OPCODE, ans);
    }

    @Override
    public Command decode(byte nextByte) {
        pushByte(nextByte);
        if (len == 2) {
            this.course_id = (bytesToShort(bytes));
            return this;
        }
        return null;

    }

}
