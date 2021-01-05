package bgu.spl.net.impl.BGRSServer.models.commands;

import bgu.spl.net.impl.BGRSServer.BGRSProtocol;
import bgu.spl.net.impl.BGRSServer.api.Command;
import bgu.spl.net.impl.BGRSServer.models.db.DatabaseError;

public class STUDENTSTAT extends Command {
    private String _uname;

    public STUDENTSTAT(String username) {
        this.OPCODE = 8;
        this._uname = username;
    }

    @Override
    public Command exec(BGRSProtocol bgrsProtocol) {
        String ans = "";
        try {
            ans = DB.studentStatus(DB.getUser(_uname));
        } catch (DatabaseError e) {
            return new ERR(OPCODE);
        }
        return new ACK(OPCODE, ans);
    }

}
