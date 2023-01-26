package tcp.trab_1;

import org.jfugue.pattern.Pattern;
import org.jfugue.player.Player;
import tcp.trab_1.exception.InputParseException;

import javax.sound.midi.*;
import java.util.Iterator;
import java.util.LinkedList;

public class Main {
    public static void main(String[] args) throws InvalidMidiDataException, InputParseException {
//        Pattern p1 = new Pattern("V0 I[Piano] Eq Ch. | Eq Ch. | Dq Eq Dq Cq");
//        Pattern p2 = new Pattern("V1 I[Flute] Rw     | Rw     | GmajQQQ  CmajQ");
//        Player player = new Player();
//        player.play(p1, p2);

        PianoWindow.createWindow();

//        LinkedList<Action> actions = Parser.parse("FAEFDECR-BR+        F FAEFDEFG G");
//        System.out.println(actions);
//
//        LinkedList<String> text = new LinkedList<>();
//
//        int octave = 5;
//
//        for (Action action : actions) {
//            switch (action) {
//                case PLAY_A -> text.add("A" + octave + "it");
//                case PLAY_B -> text.add("B" + octave + "it");
//                case PLAY_C -> text.add("C" + octave + "it");
//                case PLAY_D -> text.add("D" + octave + "it");
//                case PLAY_E -> text.add("E" + octave + "it");
//                case PLAY_F -> text.add("F" + octave + "it");
//                case PLAY_G -> text.add("G" + octave + "it");
//                case PLAY_SILENT -> text.add("R" + "it");
//                case OCTAVE_UP -> octave++;
//                case OCTAVE_DOWN -> octave--;
//            };
//        }
//
//        String sequence = join(" ", text);
//        System.out.println(sequence);
//
//        Player player = new Player();
//        player.play(sequence);
    }

    public static String join(String separator, Iterable<String> iterable) {
        StringBuilder joined = new StringBuilder();
        Iterator<String> iterator = iterable.iterator();
        if (iterator.hasNext())
            joined = new StringBuilder(iterator.next());
        while (iterator.hasNext())
            joined.append(separator).append(iterator.next());
        return joined.toString();
    }
}