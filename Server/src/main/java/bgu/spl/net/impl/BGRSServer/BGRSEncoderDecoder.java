package bgu.spl.net.impl.BGRSServer;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import bgu.spl.net.api.MessageEncoderDecoder;
import bgu.spl.net.impl.BGRSServer.api.Command;
import bgu.spl.net.impl.BGRSServer.models.commands.ISREGISTERED;

public class BGRSEncoderDecoder implements MessageEncoderDecoder<String> {
    private byte[] bytes = new byte[1 << 10];
    private int len = 0;
    private int zeros = 0;
    private short OPCODE;
    private final ByteBuffer lengthBuffer = ByteBuffer.allocate(2);

    @Override
    public String decodeNextByte(byte nextByte) {
        if (nextByte == '\n') {
            return popString();
        }

        pushByte(nextByte);
        return null; // not a line yet
    }

    @Override
    public byte[] encode(String message) {
        // TODO Auto-generated method stub
        System.out.println(message);
        return null;
    }

    private void pushByte(byte nextByte) {
        if (len >= bytes.length) {
            bytes = Arrays.copyOf(bytes, len * 2);
        }

        bytes[len++] = nextByte;
    }

    private String popString() {
        // notice that we explicitly requesting that the string will be decoded from
        // UTF-8
        // this is not actually required as it is the default encoding in java.
        String result = new String(bytes, 0, len, StandardCharsets.US_ASCII);
        len = 0;
        return result;
    }

    // private short popShort() {
    // short result = (short) ((opCodeBytes[0] & 0xff) << 8);
    // result += (short) (opCodeBytes[1] & 0xff);
    // opCodeBytes = new byte[2];
    // byteCounter = 0;
    // return result;
    // }

}
