package tcp.trab_1;

import tcp.trab_1.exception.InputParseException;
import tcp.trab_1.parse.Action;
import tcp.trab_1.parse.Parser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class ApplicationWindow extends JPanel {
    private final JTextArea textArea;

    public ApplicationWindow() {
        this.setFocusable(true);
        this.requestFocus();

        this.setPreferredSize(new Dimension(600, 120));
        this.setLayout(new BorderLayout());
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        {
            JLabel label = new JLabel("Entrada: ");
            this.add(label, BorderLayout.LINE_START);
        }

        {
            this.textArea = new JTextArea();
            this.textArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            this.textArea.setLineWrap(true);

            JScrollPane scrollPane = new JScrollPane(this.textArea);
            scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

            this.add(scrollPane, BorderLayout.CENTER);
        }

        {
            JPanel submitPanel = new JPanel();
            submitPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 2));
            submitPanel.setPreferredSize(new Dimension(110, 0));

            {
                JButton playButton = new JButton("Tocar");
                playButton.setPreferredSize(new Dimension(110, 30));
                playButton.addActionListener(new SubmitHandler());
                submitPanel.add(playButton);
            }

            {
                JButton openFileButton = new JButton("Abrir arquivo");
                openFileButton.setPreferredSize(new Dimension(110, 30));
                openFileButton.addActionListener(new OpenFileHandler());
                submitPanel.add(openFileButton);
            }

            {
                JButton saveMIDIButton = new JButton("Salvar MIDI");
                saveMIDIButton.setPreferredSize(new Dimension(110, 30));
                saveMIDIButton.addActionListener(new SaveMIDIHandler());
                submitPanel.add(saveMIDIButton);
            }

            this.add(submitPanel, BorderLayout.LINE_END);
        }
    }

    public static JFrame createWindow() {
        JFrame frame = new JFrame("Sound Player");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        frame.setContentPane(new ApplicationWindow());
        frame.pack();

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        return frame;
    }

    public class SubmitHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String input = textArea.getText();
            Parser parser = Parser.parse(input);

            ArrayList<Action> actions = new ArrayList<>();
            try {
                parser.forEach(actions::add);
            } catch (InputParseException exception) {
                JOptionPane.showMessageDialog(null, "Entrada invalida!", "Erro!", JOptionPane.ERROR_MESSAGE);
                return;
            }

            SoundPlayer.play(actions);
        }
    }

    public class OpenFileHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            // Use JFileChooser to open a file
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Abra um arquivo");
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

            JFrame frame = new JFrame();
            {
                frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                frame.setContentPane(fileChooser);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }

            fileChooser.addActionListener((ignored) -> {
                File file = fileChooser.getSelectedFile();
                frame.dispose();

                if (file == null)
                    return;

                if (!file.exists()) {
                    JOptionPane.showMessageDialog(null, "Arquivo nao encontrado!");
                    System.err.printf("File (%s) does not exist%n", file.getAbsolutePath());
                    return;
                }

                try (FileInputStream fileInputStream = new FileInputStream(file)) {
                    String fileContents = new String(fileInputStream.readAllBytes(), StandardCharsets.UTF_8);
                    textArea.setText(fileContents);
                } catch (IOException exception) {
                    JOptionPane.showMessageDialog(null, "Nao foi possivel ler o arquivo");
                    System.err.printf("Could not read the file! (%s)%n", file.getAbsolutePath());
                    exception.printStackTrace();
                }
            });
        }
    }

    public class SaveMIDIHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Use JFileChooser to open a file
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Salvar MIDI");
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

            JFrame frame = new JFrame();
            {
                frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                frame.setContentPane(fileChooser);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }

            fileChooser.addActionListener((ignored) -> {
                File file = fileChooser.getSelectedFile();
                frame.dispose();

                if (file.exists()) {
                    int result = JOptionPane.showConfirmDialog(null, "Esse arquivo ja existe, voce deseja sobrescrever?", "Sobrescrever?", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

                    if (result != JOptionPane.YES_OPTION) {
                        return;
                    }
                }

                String input = textArea.getText();
                Parser parser = Parser.parse(input);

                ArrayList<Action> actions = new ArrayList<>();
                try {
                    parser.forEach(actions::add);
                } catch (InputParseException exception) {
                    JOptionPane.showMessageDialog(null, "Entrada invalida!", "Erro!", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    SoundPlayer.saveToMidi(actions, file);
                    JOptionPane.showMessageDialog(null, "Arquivo salvo com sucesso!");
                } catch (IOException ex) {
                    System.err.println("Could not save the file!");
                    JOptionPane.showMessageDialog(null, "Nao foi possivel salvar o arquivo!", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            });
        }
    }
}
