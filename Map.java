import java.util.ArrayList;

public class Map {

    private ArrayList<SComponent> CompList;				//Palya komponenseinek listaja.

    /**
     * Default Map konstruktor
     */
    Map(){
        CompList = new ArrayList<SComponent>();
    }

    /**
     * Konstruktor, meg lehet adni SComponent listaval a terkepet
     * @param components
     */
    Map(ArrayList<SComponent> components){
        CompList = components;
    }

    /**
     * Copy-Constructor
     * @param map
     */
    Map(Map map){
        CompList = map.getComponents();
    }

    /**
     * Visszaadja az eltarolt komponenseket
     * @return ArrayList<SComponent>
     */
    public  ArrayList<SComponent> getComponents(){
        return CompList;
    }

    /**
     * Hozzaad egy komponenst a terkephez
     * @param c
     */
    public void addComponents(SComponent c){
        CompList.add(c);
    }
    
    public void resetMoveWater()
    {
    	for(SComponent c : CompList)
    	{
    		c.setMovedWater(false);
    	}
    }
}
