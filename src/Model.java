import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Model extends JPanel implements ActionListener {
    private Dimension dimension;
    private String playerName;
    private int highScore;
    private Pacman parentFrame;
    private final int blockSize=24;
    private final int blockNumber=15;
    private final int screenSize= blockSize*blockNumber;
    private int currentSpeed=3;

    private final int MAX_GHOSTS=12;
    private  int[] screenData;
    private final int LevelData[]={

        19, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 22,
        17, 16, 16, 16, 16, 24, 16, 16, 16, 16, 16, 16, 16, 16, 20,
        25, 24, 24, 24, 28, 0, 17, 16, 16, 16, 16, 16, 16, 16, 20,
        0,  0,  0,  0,  0,  0, 17, 16, 16, 16, 16, 16, 16, 16, 20,
        19, 18, 18, 18, 18, 18, 16, 16, 16, 16, 24, 24, 24, 24, 20,
        17, 16, 16, 16, 16, 16, 16, 16, 16, 20, 0,  0,  0,   0, 21,
        17, 16, 16, 16, 16, 16, 16, 16, 16, 20, 0,  0,  0,   0, 21,
        17, 16, 16, 16, 24, 16, 16, 16, 16, 20, 0,  0,  0,   0, 21,
        17, 16, 16, 20, 0, 17, 16, 16, 16, 16, 18, 18, 18, 18, 20,
        17, 24, 24, 28, 0, 25, 24, 24, 16, 16, 16, 16, 16, 16, 20,
        21, 0,  0,  0,  0,  0,  0,   0, 17, 16, 16, 16, 16, 16, 20,
        17, 18, 18, 22, 0, 19, 18, 18, 16, 16, 16, 16, 16, 16, 20,
        17, 16, 16, 20, 0, 17, 16, 16, 16, 16, 16, 16, 16, 16, 20,
        17, 16, 16, 20, 0, 17, 16, 16, 16, 16, 16, 16, 16, 16, 20,
        25, 24, 24, 24, 26, 24, 24, 24, 24, 24, 24, 24, 24, 24, 28

    };
    public  Model(String playerName, Pacman parentFrame) {
        this.playerName = playerName;
        this.parentFrame = parentFrame;
        loadHighScore();
        loadImg();
        inGameVariables();
        addKeyListener(new Adapter());
        setFocusable(true);
        intoGame();
    }
    private void loadHighScore() {
        try (Scanner scanner = new Scanner(new File("highscore.txt"))) {
            if (scanner.hasNextInt()) {
                highScore = scanner.nextInt();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveHighScore() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("highscore.txt"))) {
            writer.println(highScore);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateHighScore() {
        if (score > highScore) {
            highScore = score;
            saveHighScore();
        }
    }
    public void showIntro(Graphics2D g){
        String s= "Press Space to Start";
        g.setColor(Color.yellow);
        g.drawString(s,(screenSize)/4, 150);
    }
    private final Font fontS= new Font("Arial", Font.BOLD, 14);
    public void drawScore(Graphics2D g){
        g.setFont(fontS);
        g.setColor(new Color(5,181,79));
        String sc= "Score: "+score;
        g.drawString(sc, screenSize/2+96, screenSize+16);
        for(int i=0; i<live; i++) {
            g.drawImage(lives, i*28+8, screenSize+1, this);

        }
    }
    public void intoGame(){
        live=3;
        score=0;
        level();
        ghostNumber=6;
        currentSpeed=3;
    }
    private void play(Graphics2D g) {
        if (dying) {
            death();
        } else {
            movePacman();
            drawPacman(g);
            moveGhost(g);
            checkMap();
        }
    }
    private void checkMap(){
        int i=0;
        boolean isFinished= true;
        while(i<blockNumber*blockNumber && isFinished){
           if(screenData[i]!=0){
               isFinished=false;
           }
           i++;
        }
        if(isFinished){
            score+=50;
            if(ghostNumber<MAX_GHOSTS){
                currentSpeed++;
            }
            level();
        }
    }
    private int live;
    public void death(){
        live--;
        if(live==0){
            inGame=false;
        }
        levelContinue();
        updateHighScore();

    }
    private Image turnDown, turnUp, turnRight, turnLeft, Pacman, lives, ghost;
    private void loadImg(){
        turnDown = new ImageIcon("D:\\Pacman/down.gif").getImage();
        turnUp = new ImageIcon("D:\\Pacman/up.gif").getImage();
        turnLeft = new ImageIcon("D:\\Pacman/left.gif").getImage();
        turnRight = new ImageIcon("D:\\Pacman/right.gif").getImage();
        ghost = new ImageIcon("D:\\Pacman/ghost.gif").getImage();
        lives = new ImageIcon("D:\\Pacman/heart.png").getImage();

    }
    private Timer timer;
    public void inGameVariables(){
        screenData=new int[blockNumber*blockNumber];
        dimension = new Dimension(614,614);
        ghost_x= new int[MAX_GHOSTS];
        ghost_dx= new int[MAX_GHOSTS];
        ghost_y= new int[MAX_GHOSTS];
        ghost_dy= new int[MAX_GHOSTS];
        ghostSpeed= new int[MAX_GHOSTS];
        dx= new int[4];
        dy= new int[4];
        timer = new Timer(40,this);
        timer.start();

    }
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.black);
        g2d.fillRect(0,0, dimension.width, dimension.height);

        drawMap(g2d);
        drawScore(g2d);
        if(inGame){
            play(g2d);
        }
        else{
            showIntro(g2d);
        }

        Toolkit.getDefaultToolkit().sync();
        g2d.dispose();
        g.setColor(Color.white);
        g.drawString("High Score: " + highScore, 10, screenSize + 32);
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

    private int ghostNumber = 8;
    private int[] dx, dy, ghost_x, ghost_y, ghost_dx, ghost_dy, ghostSpeed;

    private void drawGhost(Graphics2D g, int x, int y) {
    	g.drawImage(ghost, x, y, this);
    }

    public void moveGhost(Graphics2D g2d) {
    int position;
    int count;

    for (int i = 0; i < ghostNumber; i++) {
        if (ghost_x[i] % blockSize == 0 && ghost_y[i] % blockSize == 0) {
            position = ghost_x[i] / blockSize + blockNumber * (int) (ghost_y[i] / blockSize);
            count = 0;

            if ((screenData[position] & 1) == 0 && ghost_dx[i] != 1) {
                dx[count] = -1;
                dy[count] = 0;
                count++;
            }

            if ((screenData[position] & 2) == 0 && ghost_dy[i] != 1) {
                dx[count] = 0;
                dy[count] = -1;
                count++;
            }

            if ((screenData[position] & 4) == 0 && ghost_dx[i] != -1) {
                dx[count] = 1;
                dy[count] = 0;
                count++;
            }

            if ((screenData[position] & 8) == 0 && ghost_dy[i] != -1) {
                dx[count] = 0;
                dy[count] = 1;
                count++;
            }

            if (count == 0) {
                if ((screenData[position] & 15) == 15) {
                    ghost_dx[i] = 0;
                    ghost_dy[i] = 0;
                } else {
                    ghost_dx[i] = -ghost_dx[i];
                    ghost_dy[i] = -ghost_dy[i];
                }
            } else {
                count = (int) (Math.random() * count);

                if (count > 3) {
                    count = 3;
                }

                ghost_dx[i] = dx[count];
                ghost_dy[i] = dy[count];
            }
        }

        // Update ghost positions after bounds check
        int newGhostX = ghost_x[i] + (ghost_dx[i] * ghostSpeed[i]);
        int newGhostY = ghost_y[i] + (ghost_dy[i] * ghostSpeed[i]);

        // Check bounds before updating ghost positions
        if (newGhostX >= 0 && newGhostX < screenSize - blockSize &&
            newGhostY >= 0 && newGhostY < screenSize - blockSize) {
            ghost_x[i] = newGhostX;
            ghost_y[i] = newGhostY;
        }

        drawGhost(g2d, ghost_x[i] + 1, ghost_y[i] + 1);

        if (pacX > (ghost_x[i] - 12) && pacX < (ghost_x[i] + 12) &&
            pacY > (ghost_y[i] - 12) && pacY < (ghost_y[i] + 12) && inGame) {
            dying = true;
        }
    }
}


    public void drawMap(Graphics2D g){
        int i=0, x, y;
        for(y=0; y<screenSize; y+=blockSize){
            for(x=0; x<screenSize; x+=blockSize){
                g.setColor(new Color(0,72,251));
                g.setStroke(new BasicStroke(5));

                if(LevelData[i]==0){
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

    public void level(){
        for(int i=0; i<blockNumber*blockNumber; i++){
            screenData[i]=LevelData[i];
        }
        levelContinue();
    }
    private final int validSpeeds[] = {1, 2, 3, 4, 6, 8};
    private boolean dying =false;
    public void levelContinue(){
        int xd=1;
        int random;
        for(int i=0; i<ghostNumber; i++){
            ghost_y[i]= 4*blockSize;
            ghost_x[i]= 4*blockSize;
            ghost_dy[i]=0;
            ghost_dx[i]=xd;
            xd=-xd;
            random= (int)(Math.random()*(currentSpeed+1));
            if(random>currentSpeed){
                random=currentSpeed;
            }
            ghostSpeed[i]= validSpeeds[random];
        }
        pacX= 7*blockSize;
        pacY= 11*blockSize;
        pacdX=0;
        pacdY=0;
        xTurn = 0;
        yTurn = 0;
        dying=false;
    }
    private boolean inGame=false;
    class Adapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
            int key= e.getKeyCode();
            if(inGame){
                if(key== KeyEvent.VK_LEFT){
                    xTurn = -1;
                    yTurn=0;

                }
                else if(key== KeyEvent.VK_RIGHT){
                    xTurn=1;
                    yTurn=0;
                }
                else if(key== KeyEvent.VK_UP){
                    xTurn=0;
                    yTurn=-1;
                }
                else if(key== KeyEvent.VK_DOWN){
                   xTurn=0;
                   yTurn=1;
                }
                else if(key== KeyEvent.VK_ESCAPE && timer.isRunning()){
                    inGame= false;
                }
            }else {
                if (key == KeyEvent.VK_SPACE) {
                    inGame = true;
                    intoGame();
                }
            }
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }
}
