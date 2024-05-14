
//Grafikus jatekos

import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * Ez az osztály felelős egy játékos vizuális megjelenítéséért és programbeli állapotának összekötéséért.
 */
public class GraphicsPlayer extends Graphics   {

    /**
     * Tárolja azt a játékost aminek az állapotát meg kell jelenítenie.
     */
    private Player relative;

    public GraphicsPlayer(String name, Player p) {
        relative = p;
        this.id = relative.getId();
        picLabel = new JLabel();
        icon = new ImageIcon(filePath+name+".png");
        IMG = icon.getImage();
        picLabel = new JLabel(new ImageIcon(IMG));
        picLabel.setOpaque(false);
        picLabel.setBounds(relative.getPosition().posX, relative.getPosition().posY-40, 80, 80);
    }

    public JLabel getIdLabel(){
        JLabel picLabel2 = new JLabel(id);
        picLabel2.setOpaque(false);
        picLabel2.setBounds(relative.getPosition().posX+30, relative.getPosition().posY-90, 80, 80);
        return  picLabel2;
    }
    public int getPosX(){
        return relative.getPosition().getPosX();
    }
    public int getPosY(){
        return relative.getPosition().getPosY();
    }


    /**
     * Ezzel a függvénnyel frissítjük az eltárolt játékos kinézetét.
     * Lekéri az adott játékos adott állapotát és a szerint választja ki a képernyőn lévő képet.
     */
    public void update(){
        RelocateImage(relative.getPosition().posX,relative.getPosition().posY-40);
    };

    void refresh (){picLabel = new JLabel(new ImageIcon(IMG));}

}
