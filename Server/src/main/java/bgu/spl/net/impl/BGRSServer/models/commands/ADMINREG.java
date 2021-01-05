package bgu.spl.net.impl.BGRSServer.models.commands;

import bgu.spl.net.impl.BGRSServer.BGRSProtocol;
import bgu.spl.net.impl.BGRSServer.api.Command;
import bgu.spl.net.impl.BGRSServer.api.User.Role;
import bgu.spl.net.impl.BGRSServer.models.db.DatabaseError;

public class ADMINREG extends Command {
    private final String _uname;
    private final String _pwd;

    public ADMINREG(String username, String password) {
        this.OPCODE = 1;
        this._uname = username;
        this._pwd = password;
    }

    @Override
    public Command exec(BGRSProtocol bgrsProtocol) {
        try {
            DB.registerUser(_uname, _pwd, Role.Admin);
        } catch (DatabaseError e) {
            return new ERR(OPCODE);
        }
        return new ACK(OPCODE, "");
    }

}
