public abstract class SComponent {
    protected String id;
    protected boolean hasWater = false;
    protected boolean breakable, broken;
    protected int posX, posY;
    private Map map;
    protected boolean movedWater = false;

    /** Setterek és getterek */
    public void setMap(Map m){ map = m; }
    public Map getMap(){ return map; }
    public void setHasWater(boolean w){ hasWater = w; }
    public boolean getHasWater() { return hasWater; }
    public boolean setBreakable(boolean param) { return false; }
    public boolean getBreakable(){ return breakable; }
    public boolean setBroken(boolean param){ return false; }
    public boolean getBroken(){ return broken; }
    public void setPos(int x, int y){
        posX = x;
        posY = y;
    }
    public int getWaterCounter(){
        return 0;
    }
    public int getPosX(){ return posX; }
    public int getPosY(){ return posY; }
    public SComponent getItem(){ return null; }

    public SComponent[] getNeighbours(){ return null; }

    public PipeState getState() {
        return null;
    }

    public String getId(){
        return id;
    }

    public boolean isInput(Passive p){
        return true;
    }
    
    public void setMovedWater(boolean mW)
    {
    	movedWater = mW;
    }

    /**
     * Egy komponens megszerelésére szolgáló függvény. Alapból egy komponens
     * nem romlik el, de az elromlásra képes komponensek felüldefiniálják.
     * @return igaz vagy hamis, attól függően sikeres volt-e a művelet.
     */
    public boolean Fix(){ return false; }

    /**
     * A víz mozgatását kezelő függvény. Alapból absztrakt, minden specifikus
     * komponens felüldefiniálja a saját vízmozgatási szabályainak megfelelően.
     * @param c A komponens amibe a vizet átadja.
     */
    public  abstract void MoveWater(SComponent c);

    /**
     * Ha egy játékos rálépne a komponensre, akkor ezt a cselekvést ez
     * a függvény hagyja jóvá (alapesetben mindig engedélyezi).
     * @return igaz
     */
    public boolean AddPlayer(Player p)
    {
        p.setPosition(this);
        return true;
    }

    /**
     * Egy komponens meghibásodására szolgáló függvény. Alapból egy komponens nem romlik el,
     * de az elromlásra képes leszármazott(ak) felüldefiniálják a függvényt.
     * @return igaz vagy hamis, a művelet sikerességétől függően.
     */
    public boolean Explode(){ return false; }

    /**
     * Egy pumpa lehelyezésére szolgáló függvény egy adott komponensre. Alapból egy komponensre
     * nem helyezhető pumpa, de a passzív komponensek felüldefiniálják a függvényt.
     * @param c A komponens amire a pumpát helyezzük rá.
     * @return igaz vagy hamis a művelet sikerességétől függően.
     */
    public boolean PumpPlacement(SComponent c){ return false; }

    /**
     * Egy pumpa végpontjainak beállítására szolgáló függvény. Alapból egy komponens nem pumpaként
     * funkcionál, de a pumpánál felüldefiniálásra kerül a függvény.
     * @param newIn Az új bemenet.
     * @param newOut Az új kimenet.
     * @return igaz vagy hamis attól függően, hogy sikeres volt-e.
     */
    public boolean SetPump(int newIn,int newOut){ return false; }

    /**
     * Egy ciszterna által generált pumpa összeszedésére szolgáló függvény. Alapból egy komponens
     * nem generál pumpát, de a ciszternánál felüldefiniálásra kerül a függvény.
     * @return A létrehozott pumpa.
     */
    public SComponent CollectPump()
    {
        return null;
    }

    /**
     * Egy cső egyik végpontjának átkötésére szolgáló függvény. Alapból csőátkötést csak aktív
     * komponenseken lehet elvégezni, így a függvény ott kerül felüldefiniálásra.
     * @return igaz vagy hamis attól függően, sikerült-e az átkötés
     */
    public boolean PipeRelocation(int endPoint, Active to, int toEndPoint){ return false; }

    /**
     * Ha egy játékos lelép egy komponensről, akkor ezt a cselekvést ez a függvény dokumentálja.
     */
    public void RemovePlayer() {}

    /**
     * Pumpa pályára való lehelyezése, így a pumpánál kerül megvalósításra. Ezt a függvényt hívja
     * meg a passzív elem a pumplehelyezés elvégzéséhez.
     * @param p A cső amire rá akarja tenni a szerelő a pumpát.
     * @return igaz vagy hamis, attól függően sikerült e a művelet.
     */
    public boolean Insert (Passive p){ return false; }

    /**
     * Egy cső mindkét végpontjának lecsatolására szolgáló függvény. Alapból csak aktív elemeken
     * végezhetünk el csőlecsatolást, így a függvény ott kerül felüldefiniálásra.
     * @param select A lecsatolni kívánt cső sorszáma.
     * @return igaz vagy hamis attól függően, hogy sikeres volt-e
     */
    public boolean Detach(int select) { return false; }

    /**
     * Egy cső állapotát tudjuk vele megváltoztatni. Alapból csak a csövön végezhetünk el
     * állapotváltoztatást, így a függvény ott kerül felüldefiniálásra.
     * @param newState A cső új állapota.
     * @return igaz vagy hamis, a művelet sikerétől függően
     */
    public boolean ChangeState(PipeState newState){ return false; }

    /**
     * Jelzi, hogy adható-e pont valamelyik csapatnak. Csak azok a komponensek írják felül, melyeknél
     * adható pontszám.
     * @return -1, 0 vagy 1 sorban: szabotőrnek pont, nem adható pont, szerelőknek adható pont
     */
    public int AddPoint() { return 0; }
}
