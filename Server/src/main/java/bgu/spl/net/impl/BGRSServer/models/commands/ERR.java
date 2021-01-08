package bgu.spl.net.impl.BGRSServer.models.commands;

import bgu.spl.net.impl.BGRSServer.BGRSProtocol;
import bgu.spl.net.impl.BGRSServer.api.Command;
import bgu.spl.net.impl.BGRSServer.api.Response;

public class ERR extends Response {
    private short _err_code;

    public ERR(short opcode) {
        this.OPCODE = 13;
        this._err_code = opcode;
    }

    @Override
    public Command exec(BGRSProtocol bgrsProtocol) {
        return null;
    }

    @Override
    public byte[] encode() {
        byte[] opcode = shortToBytes(OPCODE);
        byte[] err_code = shortToBytes(_err_code);

        return mergeArrays(opcode, err_code);
    }

    @Override
    public String toString() {
        return "ERROR " + _err_code;
    }
}
