package bgu.spl.net.impl.BGRSServer.api;

import bgu.spl.net.impl.BGRSServer.BGRSProtocol;
import bgu.spl.net.impl.BGRSServer.models.db.Database;

public abstract class Command {
    protected short OPCODE;
    protected Database DB = Database.getInstance();

    public abstract Command exec(BGRSProtocol bgrsProtocol);
}
