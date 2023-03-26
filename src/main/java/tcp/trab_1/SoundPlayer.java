package tcp.trab_1;

import org.jfugue.midi.MidiDictionary;
import org.jfugue.midi.MidiFileManager;
import org.jfugue.pattern.Pattern;
import org.jfugue.player.Player;
import tcp.trab_1.parse.Action;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class SoundPlayer {
    private static class PlayerState {
        public int currentInstrument;
        public int currentOctave;

        public PlayerState(int currentInstrument, int currentOctave) {
            this.currentInstrument = currentInstrument;
            this.currentOctave = currentOctave;
        }
    }

    private SoundPlayer() {}

    private static String parseActionsIntoJFugueString(Iterable<Action> actions) {
        StringBuilder patternBuilder = new StringBuilder();
        PlayerState playerState = new PlayerState(0, 5);

        for (Action action : actions) {
            handleAction(patternBuilder, playerState, action);
        }


        patternBuilder.append("R R R");
        return patternBuilder.toString();
    }

    private static void handleAction(StringBuilder patternBuilder, PlayerState state, Action action) {
        switch (action) {
            case PLAY_A -> patternBuilder.append("A").append(state.currentOctave).append(" ");
            case PLAY_B -> patternBuilder.append("B").append(state.currentOctave).append(" ");
            case PLAY_C -> patternBuilder.append("C").append(state.currentOctave).append(" ");
            case PLAY_D -> patternBuilder.append("D").append(state.currentOctave).append(" ");
            case PLAY_E -> patternBuilder.append("E").append(state.currentOctave).append(" ");
            case PLAY_F -> patternBuilder.append("F").append(state.currentOctave).append(" ");
            case PLAY_G -> patternBuilder.append("G").append(state.currentOctave).append(" ");
            case DOUBLE_VOLUME_ROLL_OVER -> patternBuilder.append("DOUBLE_VOLUME_ROLL_OVER ");
            case OCTAVE_UP_ROLL_OVER -> state.currentOctave = (state.currentOctave + 1) % 10;
            case CHANGE_INSTRUMENT_TO_GENERAL_MIDI_114 -> {
                state.currentInstrument = 113;
                patternBuilder.append("I[").append(getInstrument(state.currentInstrument)).append("] ");
            }
            case CHANGE_INSTRUMENT_TO_GENERAL_MIDI_7 -> {
                state.currentInstrument = 6;
                patternBuilder.append("I[").append(getInstrument(state.currentInstrument)).append("] ");
            }
            case CHANGE_INSTRUMENT_TO_GENERAL_MIDI_15 -> {
                state.currentInstrument = 14; // Tubular_Bells
                patternBuilder.append("I[").append(getInstrument(state.currentInstrument)).append("] ");
            }
            case CHANGE_INSTRUMENT_TO_GENERAL_MIDI_76 -> {
                state.currentInstrument = 75;
                patternBuilder.append("I[").append(getInstrument(state.currentInstrument)).append("] ");
            }
            case CHANGE_INSTRUMENT_TO_GENERAL_MIDI_20 -> {
                state.currentInstrument = 19;
                patternBuilder.append("I[").append(getInstrument(state.currentInstrument)).append("] ");
            }
            case INCREASE_INSTRUMENT_BY_0, INCREASE_INSTRUMENT_BY_1, INCREASE_INSTRUMENT_BY_2, INCREASE_INSTRUMENT_BY_3, INCREASE_INSTRUMENT_BY_4, INCREASE_INSTRUMENT_BY_5, INCREASE_INSTRUMENT_BY_6, INCREASE_INSTRUMENT_BY_7, INCREASE_INSTRUMENT_BY_8, INCREASE_INSTRUMENT_BY_9 -> {
                state.currentInstrument = (state.currentInstrument + (action.ordinal() - Action.INCREASE_INSTRUMENT_BY_0.ordinal())) % 128;
                patternBuilder.append("I[").append(getInstrument(state.currentInstrument)).append("] ");
            }
            case PAUSE -> patternBuilder.append("R ");
        }
    }

    private static String getInstrument(int index) {
        return MidiDictionary.INSTRUMENT_BYTE_TO_STRING.get((byte) index);
    }

    public static void play(Iterable<Action> actions) {
        new Thread(() -> {
            String pattern = parseActionsIntoJFugueString(actions);

            Player player = new Player();
            try {
                player.play(pattern);
            } catch (RuntimeException exception) {
                throw new RuntimeException("Could not play the pattern: '" + pattern + "'", exception);
            }
        }).start();
    }

    public static void saveToMidi(Iterable<Action> actions, File file) throws IOException {
        Pattern pattern = new Pattern(parseActionsIntoJFugueString(actions));
        MidiFileManager.savePatternToMidi(pattern, file);
    }

    public static void main(String[] args) {
        // This checks every instrument
        for (int i = 0; i < 128; i++) {
            ArrayList<Action> actions = new ArrayList<>(2+i);
            actions.add(Action.INCREASE_INSTRUMENT_BY_0);

            for (int j = 0; j < i; j++) {
                actions.add(Action.INCREASE_INSTRUMENT_BY_1);
            }

            actions.add(Action.PLAY_C);

            System.out.println("Playing " + i);

            try {
                play(actions);
            } catch (RuntimeException e) {
                System.out.println("Failed to play " + i + " (" + getInstrument(i) + ")");
                break;
            }
        }
    }
}
