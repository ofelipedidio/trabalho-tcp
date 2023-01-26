package tcp.trab_1.exception;

public class UnfinishedWordException extends InputParseException {
    public UnfinishedWordException(String expected, String found, int index) {
        super("Expected \"%s\", found \"%s\" at index %s".formatted(expected, found, index));
    }
}
