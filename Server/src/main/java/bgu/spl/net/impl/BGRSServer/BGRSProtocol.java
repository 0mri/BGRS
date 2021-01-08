package bgu.spl.net.impl.BGRSServer;

import bgu.spl.net.api.MessagingProtocol;
import bgu.spl.net.impl.BGRSServer.api.Command;
import bgu.spl.net.impl.BGRSServer.api.User;

public class BGRSProtocol implements MessagingProtocol<Command> {
    private User auth_user = null;
    private boolean shouldTerminate = false;
    String[] msg;

    @Override
    public Command process(Command cmd) {
        return cmd.exec(this);
    }

    @Override
    public boolean shouldTerminate() {
        return this.shouldTerminate;
    }

    public void loginUser(User user) {
        System.out.println("successfully logged in user -" + user.getUserName());
        this.auth_user = user;
    }

    public void logoutUser() {
        this.auth_user = null;
        this.shouldTerminate = true;
    }

    public User getUser() {
        return this.auth_user;
    }

}
