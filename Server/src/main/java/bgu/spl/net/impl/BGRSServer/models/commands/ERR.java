package bgu.spl.net.impl.BGRSServer.models.commands;

import java.lang.reflect.Constructor;

import bgu.spl.net.impl.BGRSServer.BGRSProtocol;
import bgu.spl.net.impl.BGRSServer.api.Command;

public class ERR extends Command {
    private int err_code;

    public ERR(int opcode) {
        this.OPCODE = 13;
        this.err_code = opcode;
    }

    @Override
    public Command exec(BGRSProtocol bgrsProtocol) {
        return null;
    }

}
