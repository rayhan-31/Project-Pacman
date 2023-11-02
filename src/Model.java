import javax.swing.*;
import java.awt.*;

public class Model extends JPanel {
    private Dimension dimension= new Dimension(614,614);
    private final int blockSize=34;
    private final int blockNumber=18;
    private final int screenSize= blockSize*blockNumber;

    private final int screenData[]={
        19,26,18,26,22,0 ,0 ,0 ,0 ,0 ,19,18,26,26,26,26,26,22,
        21,0 ,21,0 ,17,26,26,18,26,26,16,20,0 ,0 ,0 ,0 ,0 ,21,
        21,0 ,21,0 ,21,0 ,0 ,21,0 ,0 ,17,16,26,26,26,30,0 ,21,
        21,0 ,21,0 ,21,0 ,19,16,22,0 ,17,20,0 ,0 ,0 ,0 ,0 ,21,
        17,26,24,26,28,0 ,17,16,20,0 ,25,24,26,26,26,26,26,20,
        21,0 ,0 ,0 ,0 ,0 ,17,16,20,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,21,
        21,0 ,27,26,26,18,24,24,16,26,26,18,26,26,26,30,0 ,21,
        21,0 ,0 ,0 ,0 ,21,0 ,0 ,21,0 ,0 ,21,0 ,0 ,0 ,0 ,0 ,21,
        17,26,26,26,26,20,0 ,27,24,30,0 ,17,18,26,18,18,18,28,
        21,0 ,0 ,0 ,0 ,21,0 ,0 ,0 ,0 ,0 ,17,28,0 ,17,24,28,0 ,
        21,0 ,27,26,26,16,26,26,26,26,26,20,0 ,0 ,21,0 ,0 ,0 ,
        21,0 ,0 ,0 ,0 ,21,0 ,0 ,0 ,0 ,0 ,17,26,18,16,26,26,22,
        17,26,18,26,18,20,0 ,23,0 ,23,0 ,21,0 ,17,20,0 ,0 ,21,
        21,0 ,21,0 ,25,20,0 ,17,18,20,0 ,21,0 ,17,24,30,0 ,21,
        21,0 ,21,0 ,0 ,17,26,16,24,24,26,20,0 ,21,0 ,0 ,0 ,21,
        29,0 ,17,26,26,28,0 ,21,0 ,0 ,0 ,17,18,16,26,26,18,28,
        0 ,0 ,21,0 ,0 ,0 ,0 ,17,26,26,26,24,24,20,0 ,0 ,21,0 ,
        27,26,24,26,26,26,26,28,0 ,0 ,0 ,0 ,0 ,25,26,26,28,0

    };
    private final int LevelData[]={

            19,26,18,26,22,0 ,0 ,0 ,0 ,0 ,19,18,26,26,26,26,26,22,
            21,0 ,21,0 ,17,26,26,18,26,26,16,20,0 ,0 ,0 ,0 ,0 ,21,
            21,0 ,21,0 ,21,0 ,0 ,21,0 ,0 ,17,16,26,26,26,30,0 ,21,
            21,0 ,21,0 ,21,0 ,19,16,22,0 ,17,20,0 ,0 ,0 ,0 ,0 ,21,
            17,26,24,26,28,0 ,17,16,20,0 ,25,24,26,26,26,26,26,20,
            21,0 ,0 ,0 ,0 ,0 ,17,16,20,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,21,
            21,0 ,27,26,26,18,24,24,16,26,26,18,26,26,26,30,0 ,21,
            21,0 ,0 ,0 ,0 ,21,0 ,0 ,21,0 ,0 ,21,0 ,0 ,0 ,0 ,0 ,21,
            17,26,26,26,26,20,0 ,27,24,30,0 ,17,18,26,18,18,18,28,
            21,0 ,0 ,0 ,0 ,21,0 ,0 ,0 ,0 ,0 ,17,28,0 ,17,24,28,0 ,
            21,0 ,27,26,26,16,26,26,26,26,26,20,0 ,0 ,21,0 ,0 ,0 ,
            21,0 ,0 ,0 ,0 ,21,0 ,0 ,0 ,0 ,0 ,17,26,18,16,26,26,22,
            17,26,18,26,18,20,0 ,23,0 ,23,0 ,21,0 ,17,20,0 ,0 ,21,
            21,0 ,21,0 ,25,20,0 ,17,18,20,0 ,21,0 ,17,24,30,0 ,21,
            21,0 ,21,0 ,0 ,17,26,16,24,24,26,20,0 ,21,0 ,0 ,0 ,21,
            29,0 ,17,26,26,28,0 ,21,0 ,0 ,0 ,17,18,16,26,26,18,28,
            0 ,0 ,21,0 ,0 ,0 ,0 ,17,26,26,26,24,24,20,0 ,0 ,21,0 ,
            27,26,24,26,26,26,26,28,0 ,0 ,0 ,0 ,0 ,25,26,26,28,0

    };
    public Model(){
        loadImg();
    }
    private void play(Graphics2D g){
        drawPacman(g);
        movePacman();;
    }
    private Image turnDown, turnUp, turnRight, turnLeft, Pacman, lives, ghost;
    private void loadImg(){
        turnDown = new ImageIcon("/src/Images/down.gif").getImage();
        turnUp = new ImageIcon("/src/Images/up.gif").getImage();
        turnLeft = new ImageIcon("/src/Images/left.gif").getImage();
        turnRight = new ImageIcon("/src/Images/right.gif").getImage();
        ghost = new ImageIcon("/src/Images/ghost.gif").getImage();
        lives = new ImageIcon("/src/Images/heart.png").getImage();
        Pacman= new ImageIcon("/src/Image/pacman.png").getImage();
    }
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.black);
        g2d.fillRect(0,0, dimension.width, dimension.height);

        drawMap(g2d);
        play(g2d);
        Toolkit.getDefaultToolkit().sync();
        g2d.dispose();
    }
    private int xTurn, yTurn, pacX, pacY, pacdX, pacdY, score;
    private final int pacmanSpeed = 6;
    public void drawPacman(Graphics2D g){

        if (xTurn == -1) {
            g.drawImage(turnLeft, pacX + 1, pacY + 1, this);
        } else if (xTurn == 1) {
            g.drawImage(turnRight, pacX + 1, pacY + 1, this);
        } else if (yTurn == -1) {
            g.drawImage(turnUp, pacX + 1, pacY + 1, this);
        } else {
            g.drawImage(turnDown, pacX + 1, pacY + 1, this);
        }
    }


    private void movePacman() {

        int position, ch;

        if (pacX % blockSize == 0 && pacY % blockSize == 0) {
            position = pacX / blockSize + blockNumber * (int) (pacY / blockSize);
            ch = screenData[position];

            if ((ch & 16) != 0) {
                screenData[position] = (int) (ch & 15);
                score++;
            }

            if (xTurn != 0 || yTurn != 0) {
                if (!((xTurn == -1 && yTurn == 0 && (ch & 1) != 0)
                        || (xTurn == 1 && yTurn == 0 && (ch & 4) != 0)
                        || (xTurn == 0 && yTurn == -1 && (ch & 2) != 0)
                        || (xTurn == 0 && yTurn == 1 && (ch & 8) != 0))) {
                    pacdX = xTurn;
                    pacdY = yTurn;
                }
            }

            // Check for standstill
            if ((pacdX == -1 && pacdY == 0 && (ch & 1) != 0)
                    || (pacdX == 1 && pacdY == 0 && (ch & 4) != 0)
                    || (pacdX == 0 && pacdY == -1 && (ch & 2) != 0)
                    || (pacdX == 0 && pacdY == 1 && (ch & 8) != 0)) {
                pacdX = 0;
                pacdY = 0;
            }
        }
        pacX = pacX + pacmanSpeed * pacdX;
        pacY = pacY + pacmanSpeed * pacdY;
    }
    public void drawMap(Graphics2D g){
        int i=0, x, y;
        for(y=0; y<screenSize; y+=blockSize){
            for(x=0; x<screenSize; x+=blockSize){
                g.setColor(new Color(0,72,251));
                g.setStroke(new BasicStroke(5));

                if(screenData[i]==0){
                    g.fillRect(x,y,blockSize,blockSize);
                }
                if((screenData[i]&1)!=0){
                    g.drawLine(x,y,x,y+blockSize-1);
                }
                if((screenData[i]&2)!=0){
                    g.drawLine(x,y,x+blockSize-1,y);
                }
                if((screenData[i]&4)!=0){
                    g.drawLine(x+blockSize-1,y,x+blockSize-1,y);
                }
                if((screenData[i]&8)!=0){
                    g.drawLine(x,y+blockSize-1,x+blockSize-1,y+blockSize-1);
                }
                if((screenData[i]&16)!=0){
                    g.setColor(new Color(255,255,255));
                    g.fillOval(x+10,y+10,6,6);
                }
                i++;
            }
        }

    }
}
