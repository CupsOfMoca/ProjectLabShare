//Grafikus Cistern
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * Ez az osztály felelős egy ciszterna vizuális megjelenítéséért
 * és programbeli komponens állapotának összekötéséért.
 */
public class GraphicsCistern extends Graphics   {
    /**
     * Tárolja azt a ciszternát aminek az állapotát meg kell jelenítenie.
     */
    private Cistern relative;

    public JLabel getIdLabel(){
        JLabel picLabel2 = new JLabel(id);
        picLabel2.setOpaque(false);
        picLabel2.setBounds(relative.posX+40, relative.posY+50, 80, 80);
        return  picLabel2;
    }

    public GraphicsCistern(Cistern c) {
        relative = c;
        this.id = relative.getId();
        picLabel = new JLabel();
        icon = new ImageIcon(filePath+"cistern.png");
        IMG = icon.getImage();
        picLabel = new JLabel(new ImageIcon(IMG));
        picLabel.setOpaque(false);
        picLabel.setBounds(relative.posX, relative.posY, 80, 80);
    }
    public SComponent getRelative(){
        return relative;
    }
    public int getPosX(){
        return relative.getPosX();
    }
    public int getPosY(){
        return relative.getPosY();
    }

    /**
     * Ezzel a függvénnyel frissítjük az eltárolt ciszterna kinézetét.
     * Lekéri az adott komponens adott állapotát és a szerint választja ki a képernyőn lévő képet.
     */
    public void update(){
        if(relative.getItem()!=null){
            ChangeAppearance("cistern_hp");
        }
        else{
            ChangeAppearance("cistern");
        }
    };

    void refresh (){picLabel = new JLabel(new ImageIcon(IMG));}

}
