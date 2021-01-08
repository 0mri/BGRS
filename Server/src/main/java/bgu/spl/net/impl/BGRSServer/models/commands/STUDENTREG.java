package bgu.spl.net.impl.BGRSServer.models.commands;

import bgu.spl.net.impl.BGRSServer.BGRSProtocol;
import bgu.spl.net.impl.BGRSServer.api.Command;
import bgu.spl.net.impl.BGRSServer.api.Request;
import bgu.spl.net.impl.BGRSServer.api.User.Role;
import bgu.spl.net.impl.BGRSServer.models.db.DatabaseError;

public class STUDENTREG extends Request {
    private String _uname = null;
    private String _pwd = null;

    public STUDENTREG(short opcode) {
        this.OPCODE = opcode;
    }

    @Override
    public Command exec(BGRSProtocol bgrsProtocol) {
        if (bgrsProtocol.getUser() != null)
            return new ERR(OPCODE);
        try {
            DB.registerUser(_uname, _pwd, Role.Student);
        } catch (DatabaseError e) {
            return new ERR(OPCODE);
        }
        return new ACK(OPCODE, "");
    }

    @Override
    public Command decode(byte nextByte) {
        if (nextByte == '\0') {
            num_of_zeros++;
            if (num_of_zeros == 1)
                this._uname = popString(bytes);

            if (num_of_zeros == 2) {
                this._pwd = popString(bytes);
                return this;
            }
        }
        pushByte(nextByte);
        return null;
    }

}
