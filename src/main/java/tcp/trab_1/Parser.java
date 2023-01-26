package tcp.trab_1;

import tcp.trab_1.exception.InputParseException;
import tcp.trab_1.exception.RepeatCharacterIsTheFirstCharacter;
import tcp.trab_1.exception.UnfinishedWordException;

import java.util.LinkedList;

public class Parser {
    public static boolean nopIsACharacter = true;

    public static LinkedList<Action> parse(String input) throws InputParseException {
        char[] chars = input.toCharArray();

        LinkedList<PreAction> preActions = new LinkedList<>();

        for (int i = 0; i < chars.length; i++) {
            PreAction preAction = switch (chars[i]) {
                case 'a', 'A' -> PreAction.PLAY_A;
                case 'b'      -> PreAction.PLAY_B;
                case 'c', 'C' -> PreAction.PLAY_C;
                case 'd', 'D' -> PreAction.PLAY_D;
                case 'e', 'E' -> PreAction.PLAY_E;
                case 'f', 'F' -> PreAction.PLAY_F;
                case 'g', 'G' -> PreAction.PLAY_G;
                case ' '      -> PreAction.PLAY_SILENT;
                case '+'      -> PreAction.DOUBLE_VOLUME;
                case '-'      -> PreAction.RESET_VOLUME;
                case 'o', 'O', 'i', 'I', 'u', 'U' -> PreAction.REPEAT_PREVIOUS_NOTE_OR_RING_PHONE;
                case 'R'     -> {
                    if (!(i+1 < chars.length))
                        throw new UnfinishedWordException("R+\" or \"R-", "<EOF>", i);
                    i++;
                    yield switch (chars[i]) {
                        case '+' -> PreAction.OCTAVE_UP;
                        case '-' -> PreAction.OCTAVE_DOWN;
                        default -> throw new UnfinishedWordException("R+\" or \"R-", "R" + chars[i], i-1);
                    };
                }
                case '?'      -> PreAction.PLAY_RANDOM_NOTE;
                case '\n'     -> PreAction.CHANGE_INSTRUMENT;
                case 'B'      -> {
                    if (!(i+3 < chars.length) || chars[i+1] != 'P' || chars[i+2] != 'M' || chars[i+3] != '+')
                        yield PreAction.PLAY_B;
                    i += 3;
                    yield PreAction.BPM_UP;
                }
                case ';' -> PreAction.RANDOM_BPM;
                default -> PreAction.NOP;
            };

            preActions.add(preAction);
        }

        LinkedList<Action> actions = new LinkedList<>();

        PreAction lag = null;
        for (PreAction preAction : preActions) {
            if (preAction.equals(PreAction.REPEAT_PREVIOUS_NOTE_OR_RING_PHONE)) {
                if (lag == null)
                    throw new RepeatCharacterIsTheFirstCharacter();
                if (lag.isNote())
                    actions.add(lag.translateIntoAction());
                else
                    actions.add(Action.RING_THE_PHONE);
            } else if (preAction.equals(PreAction.NOP)) {
                if (nopIsACharacter)
                    lag = preAction;
            } else {
                actions.add(preAction.translateIntoAction());
                lag = preAction;
            }
        }

        return actions;
    }
}
