package bgu.spl.net.impl.BGRSServer.models.commands;

import bgu.spl.net.impl.BGRSServer.BGRSProtocol;
import bgu.spl.net.impl.BGRSServer.api.Command;

public class ADMINREG implements Command {

    @Override
    public Command exec(BGRSProtocol bgrsProtocol) {
        return (pc) -> {
            System.out.println("hi");
            return null;
        };
    }

}
