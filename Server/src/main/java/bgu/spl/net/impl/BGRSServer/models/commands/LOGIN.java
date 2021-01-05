package bgu.spl.net.impl.BGRSServer.models.commands;

import bgu.spl.net.impl.BGRSServer.BGRSProtocol;
import bgu.spl.net.impl.BGRSServer.api.Command;
import bgu.spl.net.impl.BGRSServer.models.db.DatabaseError;

public class LOGIN extends Command {
    private String _uname;
    private String _pwd;

    public LOGIN(String username, String password) {
        this.OPCODE = 3;
        this._uname = username;
        this._pwd = password;
    }

    @Override
    public Command exec(BGRSProtocol bgrsProtocol) {
        try {
            DB.login(_uname, _pwd);
        } catch (DatabaseError e) {
            return new ERR(OPCODE);
        }
        return new ACK(OPCODE, "");
    }

}
