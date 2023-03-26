package tcp.trab_1;

import org.jfugue.player.Player;
import tcp.trab_1.parse.Action;

public class SoundPlayer {

    private Player player;
    private int currentInstrument;
    private int currentOctave;

    public SoundPlayer() {
        player = new Player();
        currentInstrument = 0;
        currentOctave = 5;
    }

    private void executeActions(Iterable<Action> actions) {
        StringBuilder patternBuilder = parseActionsIntoJFugueString(actions);

        try {
            player.play(patternBuilder.toString());
        } catch (RuntimeException exception) {
            throw new RuntimeException("Could not play the pattern: '" + patternBuilder.toString() + "'", exception);
        }
    }

    public StringBuilder parseActionsIntoJFugueString(Iterable<Action> actions) {
        StringBuilder patternBuilder = new StringBuilder();

        for (Action action : actions) {
            handleAction(patternBuilder, action);
        }
        return patternBuilder;
    }

    private void handleAction(StringBuilder patternBuilder, Action action) {
        switch (action) {
            case PLAY_A -> patternBuilder.append("A").append(currentOctave).append(" ");
            case PLAY_B -> patternBuilder.append("B").append(currentOctave).append(" ");
            case PLAY_C -> patternBuilder.append("C").append(currentOctave).append(" ");
            case PLAY_D -> patternBuilder.append("D").append(currentOctave).append(" ");
            case PLAY_E -> patternBuilder.append("E").append(currentOctave).append(" ");
            case PLAY_F -> patternBuilder.append("F").append(currentOctave).append(" ");
            case PLAY_G -> patternBuilder.append("G").append(currentOctave).append(" ");
            case DOUBLE_VOLUME_ROLL_OVER -> patternBuilder.append("DOUBLE_VOLUME_ROLL_OVER ");
            case OCTAVE_UP_ROLL_OVER -> currentOctave = (currentOctave + 1) % 10;
            case CHANGE_INSTRUMENT_TO_GENERAL_MIDI_117 -> {
                currentInstrument = 117;
                patternBuilder.append("I[").append(getJFugueInstrumentLabel(currentInstrument)).append("] ");
            }
            case CHANGE_INSTRUMENT_TO_GENERAL_MIDI_7 -> {
                currentInstrument = 7;
                patternBuilder.append("I[").append(getJFugueInstrumentLabel(currentInstrument)).append("] ");
            }
            case CHANGE_INSTRUMENT_TO_GENERAL_MIDI_15 -> {
                currentInstrument = 15; // Tubular_Bells
                patternBuilder.append("I[").append(getJFugueInstrumentLabel(currentInstrument)).append("] ");
            }
            case CHANGE_INSTRUMENT_TO_GENERAL_MIDI_76 -> {
                currentInstrument = 76;
                patternBuilder.append("I[").append(getJFugueInstrumentLabel(currentInstrument)).append("] ");
            }
            case CHANGE_INSTRUMENT_TO_GENERAL_MIDI_20 -> {
                currentInstrument = 20;
                patternBuilder.append("I[").append(getJFugueInstrumentLabel(currentInstrument)).append("] ");
            }
            case INCREASE_INSTRUMENT_BY_0, INCREASE_INSTRUMENT_BY_1, INCREASE_INSTRUMENT_BY_2, INCREASE_INSTRUMENT_BY_3, INCREASE_INSTRUMENT_BY_4, INCREASE_INSTRUMENT_BY_5, INCREASE_INSTRUMENT_BY_6, INCREASE_INSTRUMENT_BY_7, INCREASE_INSTRUMENT_BY_8, INCREASE_INSTRUMENT_BY_9 -> {
                currentInstrument = (currentInstrument + (action.ordinal() - Action.INCREASE_INSTRUMENT_BY_0.ordinal())) % 128;
                patternBuilder.append("I[").append(getJFugueInstrumentLabel(currentInstrument)).append("] ");
            }
            case PAUSE -> patternBuilder.append("R ");
        }
    }

    private String getJFugueInstrumentLabel(int index) {
        String[] instrumentLabels = {
                "Piano",
                "Bright_Acoustic",
                "Electric_Grand",
                "Honky_Tonk",
                "Electric_Piano",
                "Electric_Piano_2",
                "Harpsichord",
                "Clav",
                "Celesta",
                "Glockenspiel",
                "Music_Box",
                "Vibraphone",
                "Marimba",
                "Xylophone",
                "Tubular_Bells",
                "Dulcimer",
                "Drawbar_Organ",
                "Percussive_Organ",
                "Rock_Organ",
                "Church_Organ",
                "Reed_Organ",
                "Accordion",
                "Harmonica",
                "Tango_Accordion",
                "Acoustic_Guitar_Nylon",
                "Acoustic_Guitar_Steel",
                "Electric_Guitar_Jazz",
                "Electric_Guitar_Clean",
                "Electric_Guitar_Muted",
                "Overdriven_Guitar",
                "Distortion_Guitar",
                "Guitar_Harmonics",
                "Acoustic_Bass",
                "Electric_Bass_Finger",
                "Electric_Bass_Pick",
                "Fretless_Bass",
                "Slap_Bass_1",
                "Slap_Bass_2",
                "Synth_Bass_1",
                "Synth_Bass_2",
                "Violin",
                "Viola",
                "Cello",
                "Contrabass",
                "Tremolo_Strings",
                "Pizzicato_Strings",
                "Orchestral_Harp",
                "Timpani",
                "String_Ensemble_1",
                "String_Ensemble_2",
                "Synth_Strings_1",
                "Synth_Strings_2",
                "Choir_Aahs",
                "Voice_Oohs",
                "Synth_Voice",
                "Orchestra_Hit",
                "Trumpet",
                "Trombone",
                "Tuba",
                "Muted_Trumpet",
                "French_Horn",
                "Brass_Section",
                "Synth_Brass_1",
                "Synth_Brass_2",
                "Soprano_Sax",
                "Alto_Sax",
                "Tenor_Sax",
                "Baritone_Sax",
                "Oboe",
                "English_Horn",
                "Bassoon",
                "Clarinet",
                "Piccolo",
                "Flute",
                "Recorder",
                "Pan_Flute",
                "Blown_Bottle",
                "Shakuhachi",
                "Whistle",
                "Ocarina",
                "Lead_1_Square",
                "Lead_2_Sawtooth",
                "Lead_3_Calliope",
                "Lead_4_Chiff",
                "Lead_5_Charang",
                "Lead_6_Voice",
                "Lead_7_Fifths",
                "Lead_8_Bass_Lead",
                "Pad_1_New_Age",
                "Pad_2_Warm",
                "Pad_3_Polysynth",
                "Pad_4_Choir",
                "Pad_5_Bowed",
                "Pad_6_Metallic",
                "Pad_7_Halo",
                "Pad_8_Sweep",
                "FX_1_Rain",
                "FX_2_Soundtrack",
                "FX_3_Crystal",
                "FX_4_Atmosphere",
                "FX_5_Brightness",
                "FX_6_Goblins",
                "FX_7_Echoes",
                "FX_8_Sci_Fi",
                "Sitar",
                "Banjo",
                "Shamisen",
                "Koto",
                "Kalimba",
                "Bagpipe",
                "Fiddle",
                "Shanai",
                "Tinkle_Bell",
                "Agogo",
                "Steel_Drums",
                "Woodblock",
                "Taiko_Drum",
                "Melodic_Tom",
                "Synth_Drum",
                "Reverse_Cymbal",
                "Guitar_Fret_Noise",
                "Breath_Noise",
                "Seashore",
                "Bird_Tweet",
                "Telephone_Ring",
                "Helicopter",
                "Applause",
                "Gunshot"
        };

        if (index >= 0 && index < instrumentLabels.length) {
            return instrumentLabels[index];
        }
        return "Piano"; // Default instrument
    }

    public static void play(Iterable<Action> actions) {
        SoundPlayer player = new SoundPlayer();
        player.executeActions(actions);
    }
}
