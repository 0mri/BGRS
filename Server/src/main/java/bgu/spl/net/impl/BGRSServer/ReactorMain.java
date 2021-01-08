package bgu.spl.net.impl.BGRSServer;

import bgu.spl.net.impl.BGRSServer.models.db.*;
import bgu.spl.net.srv.Server;

public class ReactorMain {

    public static void main(String[] args) {
        Database db = Database.getInstance();
        if (db.initialize("./Courses.txt")) {
            try {
                Server.reactor(Integer.parseInt(args[1]), Integer.parseInt(args[0]), // port
                        () -> new BGRSProtocol(), // protocol factory
                        () -> new BGRSEncoderDecoder() // message encoder decoder factory
                ).serve();
            } catch (ArrayIndexOutOfBoundsException e) {
                throw e;
            }

        } else
            System.out.println("can't initialize db");

    }
}
