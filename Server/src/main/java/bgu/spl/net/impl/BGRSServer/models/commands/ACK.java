package bgu.spl.net.impl.BGRSServer.models.commands;

import java.nio.charset.StandardCharsets;

import bgu.spl.net.impl.BGRSServer.BGRSProtocol;
import bgu.spl.net.impl.BGRSServer.api.Command;
import bgu.spl.net.impl.BGRSServer.api.Response;

public class ACK extends Response {
    private short _msg_opcode;
    private String _msg;

    public ACK(short opcode, String message) {

        this.OPCODE = 12;
        this._msg_opcode = opcode;
        this._msg = message;
    }

    @Override
    public Command exec(BGRSProtocol bgrsProtocol) {
        return null;
    }

    @Override
    public byte[] encode() {
        byte[] opcode = shortToBytes(OPCODE);
        byte[] msg_opcode = shortToBytes(_msg_opcode);
        byte[] content = (_msg + '\0').getBytes(StandardCharsets.US_ASCII);

        return mergeArrays(mergeArrays(opcode, msg_opcode), content);
    }

    @Override
    public String toString() {
        return "ACK " + _msg_opcode + "\n" + _msg;
    }

}
