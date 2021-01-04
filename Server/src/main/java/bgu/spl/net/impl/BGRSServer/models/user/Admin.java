
package bgu.spl.net.impl.BGRSServer.models.user;

import bgu.spl.net.impl.BGRSServer.api.User;

public class Admin extends User {
    public Admin(String username, String password) {
        super(username, password);
    }

    @Override
    public Role getRole() {
        return Role.Admin;
    }

    @Override
    public boolean isAdmin() {
        return true;
    }

    @Override
    public boolean isStudent() {
        return false;
    }
}
