package tcp.trab_1;

import tcp.trab_1.exception.InputParseException;
import tcp.trab_1.parse.Action;
import tcp.trab_1.parse.Lexer;
import tcp.trab_1.parse.Parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.LinkedList;

public class Main {
    public static void main(String[] args) {
        // There are 2 ways of executing the program:
        // 1 - No args - opens the app window
        // 2 - "command --play <input>" - plays the input file
        if (args.length == 0) {
            ApplicationWindow.createWindow();
        } else if (args.length == 2) {
            if (!args[0].equals("--play")) {
                System.err.println("Invalid arguments");
                return;
            }

            File file = new File(args[1]);

            if (!file.exists()) {
                System.err.println("File does not exist");
                return;
            }

            try (FileInputStream fileInputStream = new FileInputStream(file)) {
                byte[] bytes = fileInputStream.readAllBytes();
                String input = new String(bytes, StandardCharsets.UTF_8);

                //LinkedList<Action> actions = new Parser(new Lexer(input.toCharArray()).iterator());
                Iterable<Action> actions = Parser.parse(input);
                SoundPlayer.play(actions);
            } catch (IOException e) {
                System.err.println("Could not read the file!");
                e.printStackTrace();
            } catch (InputParseException e) {
                System.err.println("The file does not follow the expected syntax!");
            }
        }
    }
}