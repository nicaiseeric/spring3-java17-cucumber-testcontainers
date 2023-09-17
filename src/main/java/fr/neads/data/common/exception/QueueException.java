package fr.neads.data.common.exception;

public class QueueException extends RuntimeException {
    public QueueException(String message) {
        super(message);
    }

    public QueueException(Throwable cause) {
        super(cause);
    }

    public QueueException(String message, Throwable cause) {
        super(message, cause);
    }
}