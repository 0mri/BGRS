package bgu.spl.net.impl.BGRSServer.api;

import bgu.spl.net.impl.BGRSServer.BGRSProtocol;

public interface Command {

    Command exec(BGRSProtocol bgrsProtocol);
}
