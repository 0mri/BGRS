package bgu.spl.net.impl.BGRSServer.api;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import bgu.spl.net.impl.BGRSServer.models.commands.ADMINREG;
import bgu.spl.net.impl.BGRSServer.models.commands.COURSEREG;
import bgu.spl.net.impl.BGRSServer.models.commands.COURSESTAT;
import bgu.spl.net.impl.BGRSServer.models.commands.ISREGISTERED;
import bgu.spl.net.impl.BGRSServer.models.commands.KDAMCHECK;
import bgu.spl.net.impl.BGRSServer.models.commands.LOGIN;
import bgu.spl.net.impl.BGRSServer.models.commands.LOGOUT;
import bgu.spl.net.impl.BGRSServer.models.commands.MYCOURSES;
import bgu.spl.net.impl.BGRSServer.models.commands.STUDENTREG;
import bgu.spl.net.impl.BGRSServer.models.commands.STUDENTSTAT;
import bgu.spl.net.impl.BGRSServer.models.commands.UNREGISTER;

public abstract class Request extends Command {
    protected byte[] bytes = new byte[1 << 10];
    protected int len = 0;
    protected int num_of_zeros = 0;

    public abstract Command decode(byte nextByte);

    // public abstract void set(String[] msg);

    public static Request get_command_by_opcode(short opcode) {
        switch (opcode) {
            case 1:
                return new ADMINREG(opcode);
            case 2:
                return new STUDENTREG(opcode);
            case 3:
                return new LOGIN(opcode);
            case 4:
                return new LOGOUT(opcode);
            case 5:
                return new COURSEREG(opcode);
            case 6:
                return new KDAMCHECK(opcode);
            case 7:
                return new COURSESTAT(opcode);
            case 8:
                return new STUDENTSTAT(opcode);
            case 9:
                return new ISREGISTERED(opcode);
            case 10:
                return new UNREGISTER(opcode);
            case 11:
                return new MYCOURSES(opcode);
            default:
                return null;
        }

    }

    protected void pushByte(byte nextByte) {
        if (len >= bytes.length)
            bytes = Arrays.copyOf(bytes, len * 2);

        bytes[len++] = nextByte;
    }

    protected String popString(byte[] bytes) {
        String result = new String(bytes, StandardCharsets.US_ASCII);
        len = 0;
        bytes = new byte[1 << 10];
        return result;
    }

    public short bytesToShort(byte[] byteArr) {

        short result = (short) ((byteArr[0] & 0xff) << 8);
        result += (short) (byteArr[1] & 0xff);

        return result;
    }

}
