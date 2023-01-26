package tcp.trab_1;

import org.jfugue.player.Player;
import tcp.trab_1.exception.InputParseException;

import java.util.LinkedList;
import java.util.Random;

public class SoundPlayer {
    private final Random random = new Random();
    private int octave = 5;
    private double bpm = 100.0d;

    public void executeAction(Action action) {
        Player player = new Player();

        switch (action) {
            // Notes
            case PLAY_A:
                // TODO selecionar instrumento
                // Toca a nota A (dó) no oitavo "octave" com duração it (1/8 + 1/32)
                player.play("A" + octave + "it");
                break;
            case PLAY_B:
                // TODO selecionar instrumento
                player.play("B" + octave + "it");
                break;
            case PLAY_C:
                // TODO selecionar instrumento
                player.play("C" + octave + "it");
                break;
            case PLAY_D:
                // TODO selecionar instrumento
                player.play("D" + octave + "it");
                break;
            case PLAY_E:
                // TODO selecionar instrumento
                player.play("E" + octave + "it");
                break;
            case PLAY_F:
                // TODO selecionar instrumento
                player.play("F" + octave + "it");
                break;
            case PLAY_G:
                // TODO selecionar instrumento
                player.play("G" + octave + "it");
                break;
            case PLAY_SILENT:
                break;
            case PLAY_RANDOM_NOTE:
                char note = (char) ('A' + random.nextInt(7));
                player.play(note + octave + "it");
                break;

            // Volume
            case DOUBLE_VOLUME:
                // TODO
                break;
            case RESET_VOLUME:
                // TODO
                break;

            // Sounds
            case RING_THE_PHONE:
                // TODO
                break;

            // Octave
            case OCTAVE_UP:
                octave++;
                break;
            case OCTAVE_DOWN:
                octave = 5;
                break;

            // Instrument
            case CHANGE_INSTRUMENT:
                // TODO
                break;

            // BPM
            case BPM_UP:
                // TODO
                break;
            case RANDOM_BPM:
                double bpmDelta = (random.nextDouble() * 100.0d) - 50.0d;
                this.bpm = 100.0d + bpmDelta;
                break;
        }
    }

    public double getBPM() {
        return this.bpm;
    }

    public static void main(String[] args) throws InputParseException, InterruptedException {
        LinkedList<Action> actions = Parser.parse("?A?B?C?D?E?F?G-BR+        A B C D E");
        System.out.println(actions);

        SoundPlayer soundPlayer = new SoundPlayer();

        for (Action action : actions) {
            soundPlayer.executeAction(action);
            Thread.sleep(1000L * ((long) (60.0d / soundPlayer.getBPM())));
        }
    }
}
