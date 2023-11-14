import javax.swing.*;

public class Pacman extends JFrame {
    private String playerName;

    Pacman() {
        playerName = askForName();
        add(new Model(playerName, this));
    }

    private String askForName() {
        return JOptionPane.showInputDialog(this, "Enter your name:");
    }
    public static void main(String[] args) {
        Pacman pac = new Pacman();

        pac.setVisible(true);
        pac.setTitle("Pacman");
        pac.setSize(380, 420);
        pac.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        pac.setLocationRelativeTo(null);
    }
}
