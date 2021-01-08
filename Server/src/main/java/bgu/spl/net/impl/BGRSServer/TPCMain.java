package bgu.spl.net.impl.BGRSServer;

import bgu.spl.net.impl.BGRSServer.models.db.Database;
import bgu.spl.net.srv.Server;

public class TPCMain {
    public static void main(String[] args) {
        Database db = Database.getInstance();
        if (db.initialize("./Courses.txt")) {
            try {
                Server.threadPerClient(Integer.parseInt(args[0]), // port
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
