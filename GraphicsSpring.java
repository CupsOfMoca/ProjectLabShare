
//Grafikus forras

import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * Ez az osztály felelős egy forrás vizuális megjelenítéséért
 * és programbeli komponens állapotának összekötéséért.
 */
public class GraphicsSpring extends Graphics   {
    /**
     *  Tárolja azt a forrást aminek az állapotát meg kell jelenítenie.
     */
    private Spring relative;

    public GraphicsSpring(Spring s) {
        relative = s;
        this.id = relative.getId();
        picLabel = new JLabel();
        icon = new ImageIcon(filePath+"spring.png");
        IMG = icon.getImage();
        picLabel = new JLabel(new ImageIcon(IMG));
        picLabel.setOpaque(false);
        picLabel.setBounds(relative.posX, relative.posY, 80, 80);
    }

    public JLabel getIdLabel(){
        JLabel picLabel2 = new JLabel(id);
        picLabel2.setOpaque(false);
        picLabel2.setBounds(relative.posX+40, relative.posY+50, 80, 80);
        return  picLabel2;
    }
    public int getPosX(){
        return relative.getPosX();
    }
    public int getPosY(){
        return relative.getPosY();
    }


    public void update(){}

    void refresh (){picLabel = new JLabel(new ImageIcon(IMG));}

}
