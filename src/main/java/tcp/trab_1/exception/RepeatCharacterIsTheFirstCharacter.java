package tcp.trab_1.exception;

public class RepeatCharacterIsTheFirstCharacter extends InputParseException {
    public RepeatCharacterIsTheFirstCharacter() {
        super("The repeat character cannot be the first character");
    }
}
