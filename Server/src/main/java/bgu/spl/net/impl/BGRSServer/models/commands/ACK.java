package bgu.spl.net.impl.BGRSServer.models.commands;

import bgu.spl.net.impl.BGRSServer.BGRSProtocol;
import bgu.spl.net.impl.BGRSServer.api.Command;

public class ACK extends Command {
    private int _msg_opcode;
    private String _msg;

    public ACK(int OPCODE, String message) {
        this.OPCODE = 12;
        this._msg_opcode = OPCODE;
        this._msg = message;
    }

    @Override
    public Command exec(BGRSProtocol bgrsProtocol) {
        return null;
    }

}
