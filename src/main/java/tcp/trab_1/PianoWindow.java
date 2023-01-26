package tcp.trab_1;

import org.jfugue.player.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PianoWindow extends JPanel implements ActionListener {
    private JTextArea textArea;

    public PianoWindow() {
        this.setFocusable(true);
        this.requestFocus();

        this.textArea = new JTextArea();
        this.textArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BorderLayout());
        inputPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        inputPanel.add(textArea, BorderLayout.CENTER);
//        this.textArea.setPreferredSize(new Dimension());

        this.setPreferredSize(new Dimension(300, 200));
        this.setLayout(new BorderLayout());
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel label = new JLabel("Entrada: ");
        JButton button = new JButton("Tocar");

        this.add(label, BorderLayout.LINE_START);
        this.add(inputPanel, BorderLayout.CENTER);
        this.add(button, BorderLayout.LINE_END);

        button.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    public static JFrame createWindow() {
        JFrame frame = new JFrame("Piano");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        frame.setContentPane(new PianoWindow());
        frame.pack();

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        return frame;
    }
}
