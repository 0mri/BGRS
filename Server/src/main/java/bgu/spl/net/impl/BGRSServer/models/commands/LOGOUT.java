package bgu.spl.net.impl.BGRSServer.models.commands;

import bgu.spl.net.impl.BGRSServer.BGRSProtocol;
import bgu.spl.net.impl.BGRSServer.api.Command;
import bgu.spl.net.impl.BGRSServer.api.Request;
import bgu.spl.net.impl.BGRSServer.models.db.DatabaseError;

public class LOGOUT extends Request {

    public LOGOUT(short opcode) {
        this.OPCODE = opcode;
    }

    @Override
    public Command exec(BGRSProtocol bgrsProtocol) {
        try {
            DB.logout(bgrsProtocol.getUser());
            bgrsProtocol.logoutUser();
        } catch (DatabaseError e) {
            return new ERR(OPCODE);
        }
        return new ACK(OPCODE, "");
    }

    @Override
    public Command decode(byte nextByte) {
        return this;
    }
}
