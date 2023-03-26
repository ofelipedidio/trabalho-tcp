package tcp.trab_1.parse;

import tcp.trab_1.OptionalIterator;
import tcp.trab_1.exception.InputParseException;

import java.util.Iterator;
import java.util.Optional;

public class Parser implements OptionalIterator<Action> {
    private final Iterator<Token> tokens;
    private Token lag = null;

    public Parser(Iterator<Token> tokens) {
        this.tokens = tokens;
    }

    @Override
    public Optional<Action> next() {
        if (!tokens.hasNext()) {
            return Optional.empty();
        }

        Token token = tokens.next();

        Action action = switch (token) {
            // Notes
            case A -> Action.PLAY_A;
            case B -> Action.PLAY_B;
            case C -> Action.PLAY_C;
            case D -> Action.PLAY_D;
            case E -> Action.PLAY_E;
            case F -> Action.PLAY_F;
            case G -> Action.PLAY_G;

            // Control (double/octave)
            case SPACE -> Action.DOUBLE_VOLUME_ROLL_OVER;
            case QUESTION_MARK -> Action.OCTAVE_UP_ROLL_OVER;

            // Control (instrument)
            case EXCLAMATION -> Action.CHANGE_INSTRUMENT_TO_GENERAL_MIDI_117;
            case OTHER_VOWEL -> Action.CHANGE_INSTRUMENT_TO_GENERAL_MIDI_7;
            case NEW_LINE -> Action.CHANGE_INSTRUMENT_TO_GENERAL_MIDI_15;
            case SEMICOLON -> Action.CHANGE_INSTRUMENT_TO_GENERAL_MIDI_76;
            case COMMA -> Action.CHANGE_INSTRUMENT_TO_GENERAL_MIDI_20;

            // Handle other characters
            case OTHER_CHARACTERS -> {
                if (lag == null) {
                    throw new InputParseException("Cannot repeat previous token because there is no previous token");
                }

                yield switch (lag) {
                    case A -> Action.PLAY_A;
                    case B -> Action.PLAY_B;
                    case C -> Action.PLAY_C;
                    case D -> Action.PLAY_D;
                    case E -> Action.PLAY_E;
                    case F -> Action.PLAY_F;
                    case G -> Action.PLAY_G;
                    default -> Action.PAUSE;
                };
            }

            // Handle digits
            case DIGIT_0 -> Action.INCREASE_INSTRUMENT_BY_0;
            case DIGIT_1 -> Action.INCREASE_INSTRUMENT_BY_1;
            case DIGIT_2 -> Action.INCREASE_INSTRUMENT_BY_2;
            case DIGIT_3 -> Action.INCREASE_INSTRUMENT_BY_3;
            case DIGIT_4 -> Action.INCREASE_INSTRUMENT_BY_4;
            case DIGIT_5 -> Action.INCREASE_INSTRUMENT_BY_5;
            case DIGIT_6 -> Action.INCREASE_INSTRUMENT_BY_6;
            case DIGIT_7 -> Action.INCREASE_INSTRUMENT_BY_7;
            case DIGIT_8 -> Action.INCREASE_INSTRUMENT_BY_8;
            case DIGIT_9 -> Action.INCREASE_INSTRUMENT_BY_9;
        };

        lag = token;

        return Optional.of(action);
    }

    public static Parser parse(String input) {
        return new Parser(new Lexer(input.toCharArray()).iterator());
    }

    public static void main(String[] args) {
        String input = "A A A";
        Parser parser = new Parser(new Lexer(input.toCharArray()).iterator());

        for (Action action : parser) {
            System.out.println(action);
        }
    }
}
