
//Grafikus pumpa

import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * Ez az osztály felelős egy pumpa vizuális megjelenítéséért
 * és programbeli komponens állapotának összekötéséért.
 */
public class GraphicsPump extends Graphics   {
    /**
     *  Tárolja azt a pumpát aminek az állapotát meg kell jelenítenie.
     */
    private Pump relative;

    public GraphicsPump(Pump p) {
        relative = p;
        this.id = relative.getId();
        picLabel = new JLabel();
        icon = new ImageIcon(filePath+"pump_wk_nw.png");
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


    /**
     * Ezzel a függvénnyel frissítjük az eltárolt pumpa kinézetét.
     * Lekéri az adott komponens adott állapotát és a szerint választja ki a képernyőn lévő képet.
     */
    public void update(){
        if(relative.broken){
            if(relative.hasWater){
                ChangeAppearance("pump_br_hw");
            }
            else {
                ChangeAppearance("pump_br_nw");
            }
        }else {
            if (relative.getHasWater()) {
                ChangeAppearance("pump_wk_hw");
            } else {
                ChangeAppearance("pump_wk_nw");
            }
        }
    };

    void refresh (){picLabel = new JLabel(new ImageIcon(IMG));}

}
