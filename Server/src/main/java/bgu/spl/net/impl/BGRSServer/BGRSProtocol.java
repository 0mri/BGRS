package bgu.spl.net.impl.BGRSServer;

import bgu.spl.net.api.MessagingProtocol;
import bgu.spl.net.impl.BGRSServer.api.Command;
import bgu.spl.net.impl.BGRSServer.api.User;

public class BGRSProtocol implements MessagingProtocol<Command> {
    private User user = null;
    private boolean shouldTerminate = false;

    @Override
    public Command process(Command cmd) {
        return cmd.exec(this);
    }

    @Override
    public boolean shouldTerminate() {
        return this.shouldTerminate;
    }

    public User getUser() {
        return this.user;
    }

}
