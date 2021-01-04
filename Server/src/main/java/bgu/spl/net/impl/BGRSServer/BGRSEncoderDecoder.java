package bgu.spl.net.impl.BGRSServer;

import bgu.spl.net.api.MessageEncoderDecoder;
import bgu.spl.net.impl.BGRSServer.api.Command;

public class BGRSEncoderDecoder implements MessageEncoderDecoder<Command> {
    private byte[] bytesArr = new byte[1 << 10];
    private int length = 0;
    private short OPCODE;

    @Override
    public Command decodeNextByte(byte nextByte) {
        System.out.println(nextByte);
        return null; // not a line yet
    }

    @Override
    public byte[] encode(Command message) {
        // TODO Auto-generated method stub
        return null;
    }

}
