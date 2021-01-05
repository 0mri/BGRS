package bgu.spl.net.impl.BGRSServer.models.commands;

import bgu.spl.net.impl.BGRSServer.BGRSProtocol;
import bgu.spl.net.impl.BGRSServer.api.Command;
import bgu.spl.net.impl.BGRSServer.models.db.DatabaseError;

public class LOGOUT extends Command {

    public LOGOUT(String username, String password) {
        this.OPCODE = 4;
    }

    @Override
    public Command exec(BGRSProtocol bgrsProtocol) {
        try {
            DB.logout(bgrsProtocol.getUser());
        } catch (DatabaseError e) {
            return new ERR(OPCODE);
        }
        return new ACK(OPCODE, "");
    }
}
