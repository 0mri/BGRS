package bgu.spl.net.impl.BGRSServer.models.commands;

import bgu.spl.net.impl.BGRSServer.BGRSProtocol;
import bgu.spl.net.impl.BGRSServer.api.Command;
import bgu.spl.net.impl.BGRSServer.api.Request;
import bgu.spl.net.impl.BGRSServer.models.db.DatabaseError;

public class STUDENTSTAT extends Request {
    private String _uname;

    public STUDENTSTAT(short opcode) {
        this.OPCODE = opcode;
    }

    @Override
    public Command exec(BGRSProtocol bgrsProtocol) {
        String ans;
        try {
            ans = DB.studentStatus(bgrsProtocol.getUser(), _uname);
        } catch (DatabaseError e) {
            System.out.println(e);
            return new ERR(OPCODE);
        }
        return new ACK(OPCODE, ans);
    }

    @Override
    public Command decode(byte nextByte) {
        if (nextByte == '\0') {
            this._uname = (popString(bytes));
            return this;
        }
        pushByte(nextByte);
        return null;
    }

}
