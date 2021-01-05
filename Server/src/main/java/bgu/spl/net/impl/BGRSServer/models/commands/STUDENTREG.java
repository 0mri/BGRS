package bgu.spl.net.impl.BGRSServer.models.commands;

import bgu.spl.net.impl.BGRSServer.BGRSProtocol;
import bgu.spl.net.impl.BGRSServer.api.Command;
import bgu.spl.net.impl.BGRSServer.api.User.Role;
import bgu.spl.net.impl.BGRSServer.models.db.DatabaseError;

public class STUDENTREG extends Command {
    private final String _uname;
    private final String _pwd;

    public STUDENTREG(String username, String password) {
        this.OPCODE = 2;
        this._uname = username;
        this._pwd = password;
    }

    @Override
    public Command exec(BGRSProtocol bgrsProtocol) {
        try {
            DB.registerUser(_uname, _pwd, Role.Student);
        } catch (DatabaseError e) {
            return new ERR(OPCODE);
        }
        return new ACK(OPCODE, "");
    }
}
