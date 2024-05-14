

import java.awt.Image;
import javax.swing.*;

/**
 * Ez az absztrakt osztály az ősosztálya az összes grafikus objektumnak.
 * Cél, hogy az összes grafikus objektumot egységesen lehessen tárolni.
 */
public abstract class Graphics   {

    /**
     * Tárolja az objektum ID-jét, aminek a segítségével az adott objektumra referálunk.
     * Ez az ID az objektum kirajzolása során is látható.
     */
    protected String id;

    //Grafikus ososztaly
    protected final static String filePath = "RESOURCES/IMAGES/Texture2D/";
    protected JLabel picLabel;
    /**
     * Tárolja annak a képnek az elérési útját, amely az objektum aktuális textúráját adja.
     */
    protected Image IMG;
    protected ImageIcon icon;

    public JLabel getIdLabel(){
        JLabel picLabel2 = new JLabel(id);
        picLabel2.setOpaque(false);
        picLabel2.setBounds(0, 0, 1280, 720);
        return  picLabel2;
    }
    public Graphics() {
        id = "cactus";
        picLabel = new JLabel();
        icon = new ImageIcon(filePath+"cactus.png");
        IMG = icon.getImage();
        picLabel = new JLabel(new ImageIcon(IMG));
        picLabel.setOpaque(false);
        picLabel.setBounds(0, 0, 1280, 720);
    }

    public int getPosX(){
        return 0;
    }
    public int getPosY(){
        return 0;
    }

    public Graphics(String id) {

        this.id = id;
        picLabel = new JLabel();
        icon = new ImageIcon(filePath+id+".png");
        IMG = icon.getImage();
        picLabel = new JLabel(new ImageIcon(IMG));
        picLabel.setOpaque(false);
        picLabel.setBounds(0, 0, 1280, 720);
    }

    /**
     *  A grafikus objektum kinézetének átállítására szolgál:
     *  az Update()-ben megváltozó image változó értékét felhasználva betölti
     *  és beállítja az objektum új textúráját.
     * @param n
     */
    public void ChangeAppearance(String n) {
        icon = new ImageIcon(filePath+n+".png");
        IMG = icon.getImage();
        picLabel.setIcon(icon);

    }
    public void ResizeImage(int Width, int Height) {
        icon = new ImageIcon(IMG.getScaledInstance(Width, Height, 1));
        picLabel.setIcon(icon);
    }
    public void RelocateImage(int X, int Y){
        picLabel.setBounds(X, Y, 80,80);
    }
    JLabel getLabel(){return picLabel;}

    /**
     * Absztrakt függvény, a leszármazott grafikus osztályokban kerül felüldefiniálásra.
     * A függvény a grafikus objektumhoz tartozó játék objektum állapota alapján
     * frissíti a grafikus objektum kinézetét.
     */
    public abstract void update();

    void refresh (){picLabel = new JLabel(new ImageIcon(IMG));}

}
