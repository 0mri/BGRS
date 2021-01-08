package bgu.spl.net.impl.BGRSServer;

import java.util.Arrays;
import bgu.spl.net.api.MessageEncoderDecoder;
import bgu.spl.net.impl.BGRSServer.api.Command;
import bgu.spl.net.impl.BGRSServer.api.Request;
import bgu.spl.net.impl.BGRSServer.api.Response;

public class BGRSEncoderDecoder implements MessageEncoderDecoder<Command> {
    private byte[] bytes = new byte[2];
    private int len = 0;
    private short OPCODE = 0;
    private Request req;

    @Override
    public Command decodeNextByte(byte nextByte) {
        if (req != null) {
            Command cmd = req.decode(nextByte);
            if (cmd != null) {
                reset();
                return cmd;
            }
        }

        pushByte(nextByte);

        if (len == 2 && OPCODE == 0) {
            OPCODE = bytesToShort(bytes);
            req = Request.get_command_by_opcode(OPCODE);
            if (OPCODE == 4 || OPCODE == 11) {
                Command cmd = req.decode(nextByte);
                reset();
                return cmd;
            }
        }
        return null;
    }

    private void reset() {
        OPCODE = 0;
        len = 0;
        req = null;
    }

    @Override
    public byte[] encode(Command msg) {
        Response b = (Response) msg;
        System.out.println(b);
        return b.encode();
    }

    private void pushByte(byte nextByte) {
        if (len >= bytes.length)
            bytes = Arrays.copyOf(bytes, len * 2);

        bytes[len++] = nextByte;
    }

    public short bytesToShort(byte[] byteArr) {

        short result = (short) ((byteArr[0] & 0xff) << 8);
        result += (short) (byteArr[1] & 0xff);

        return result;
    }

}
