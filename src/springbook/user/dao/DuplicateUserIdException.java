package springbook.user.dao;

public class DuplicateUserIdException extends RuntimeException {

    DuplicateUserIdException(Throwable cause) {
        super(cause);
    }
}
