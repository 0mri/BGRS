package bgu.spl.net.impl.BGRSServer.models.db;

public class DatabaseError extends Exception {
    public DatabaseError(String message) {
        super(message);
    }

    DatabaseError() {
        super();
    }

    /**
     *
     */
    private static final long serialVersionUID = 6076394601953916408L;

}
