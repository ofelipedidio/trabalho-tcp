package tcp.trab_1;

public enum PreAction {
    // Notes
    PLAY_A(Action.PLAY_A, true),
    PLAY_B(Action.PLAY_B, true),
    PLAY_C(Action.PLAY_C, true),
    PLAY_D(Action.PLAY_D, true),
    PLAY_E(Action.PLAY_E, true),
    PLAY_F(Action.PLAY_F, true),
    PLAY_G(Action.PLAY_G, true),
    PLAY_SILENT(Action.PLAY_SILENT),
    PLAY_RANDOM_NOTE(Action.PLAY_RANDOM_NOTE),
    REPEAT_PREVIOUS_NOTE_OR_RING_PHONE(null),
    NOP(null),

    // Volume
    DOUBLE_VOLUME(Action.DOUBLE_VOLUME),
    RESET_VOLUME(Action.RESET_VOLUME),

    // Octave
    OCTAVE_UP(Action.OCTAVE_UP),
    OCTAVE_DOWN(Action.OCTAVE_DOWN),

    // Instrument
    CHANGE_INSTRUMENT(Action.CHANGE_INSTRUMENT),

    // BPM
    BPM_UP(Action.BPM_UP),
    RANDOM_BPM(Action.RANDOM_BPM);

    private final Action action;
    private final boolean isNote;

    private PreAction(Action action) {
        this(action, false);
    }

    private PreAction(Action action, boolean isNote) {
        this.action = action;
        this.isNote = isNote;
    }

    public boolean isNote() {
        return this.isNote;
    }

    public Action translateIntoAction() {
        return action;
    }
}
