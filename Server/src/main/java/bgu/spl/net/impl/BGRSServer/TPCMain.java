package bgu.spl.net.impl.BGRSServer;

import bgu.spl.net.srv.Server;

public class TPCMain {
    public static void main(String[] args) {
        try {
            Server.threadPerClient(Integer.parseInt(args[0]), // port
                    () -> new BGRSProtocol(), // protocol factory
                    () -> new BGRSEncoderDecoder() // message encoder decoder factory
            ).serve();

        } catch (ArrayIndexOutOfBoundsException e) {
            throw e;
        }
    }

}
