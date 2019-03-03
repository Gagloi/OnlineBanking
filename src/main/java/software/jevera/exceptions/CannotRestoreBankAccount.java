package software.jevera.exceptions;

public class CannotRestoreBankAccount extends RuntimeException {

    public CannotRestoreBankAccount(String message) {
        super(message);
    }
}
