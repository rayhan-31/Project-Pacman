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
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.black);
        g2d.fillRect(0,0, dimension.width, dimension.height);

        drawMap(g2d);
        Toolkit.getDefaultToolkit().sync();
        g2d.dispose();
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
