import java.util.Random;

public class Pipe extends Passive implements Notifiable{
    private boolean hasPlayer = false;
    private PipeState state = PipeState.NORMAL;

    public PipeState getState() {
        return state;
    }

    public Pipe(String s,int X, int Y){
        ActiveComponents= new Active[2];
        breakable = true;
        broken = false;
        posX= X;
        posY= Y;
        id=s;
    }

    /**
     * A cső megjavítására szolgál. Meghívásakor beállítja a leaking változót hamisra.
     * @return igaz vagy hamis attól függően, hogy meg lehetett-e foltozni.
     */
    @Override
    public boolean Fix(){
        if(!broken){
            return false;
        }
        broken = false;
        breakable=false;
        hasWater = false;
        return true;
    }

    /**
     * Ha a cső nem lyukas, továbbítja a benne lévő vizet a kimenetén lévő komponensbe, amennyiben van ilyen.
     * @param from A komponens, akitől a cső a vizet kapja.
     */
    @Override
    public void MoveWater(SComponent from){

        if(ActiveComponents[0]!=null && ActiveComponents[1]!=null){
            if(hasWater && !broken && !movedWater)
            {
                for (int i = 0; i < 2; ++i) {
                    if (!ActiveComponents[1-i].equals(from) && !ActiveComponents[1-i].getBroken() && ActiveComponents[1-i].isInput(this)) {
                        ActiveComponents[1-i].MoveWater(this);
                        hasWater = false;
                    }
                }
            }
            else if(from != null)
            {
                hasWater = true;
            }
        }
        else if(from !=null){
            hasWater=true;
        }
        movedWater=true;
    }

    /**
     * A cső megjegyzi, hogy áll-e rajta játékos. Amikor rá akar lépni egy játékos a csőre,
     * visszatérési értékében jelzi, hogy állnak-e már rajta vagy sem. Ettől függően beállítja
     * a hasPlayer változót igazra vagy hamisra.
     * @return igaz vagy hamis attól függően, hogy tartózkodik-e játékos a csövön.
     */
    @Override
    public boolean AddPlayer(Player p){
        if(!hasPlayer){
            //A cső ragad
            if(state == PipeState.STICKY){
                p.setRooted(true);
                hasPlayer = true;
                p.setPosition(this);
                return true;
            }
            //A játékos elcsúszik a csőről
            else if(state == PipeState.SLIPPERY){
                Random random = new Random();
                int tmpIdx = random.nextInt(2);
                if(ActiveComponents[tmpIdx] == null){
                    if(tmpIdx == 0){
                        ActiveComponents[1].AddPlayer(p);
                    }
                    else{
                        ActiveComponents[0].AddPlayer(p);
                    }
                }
                else{
                    ActiveComponents[tmpIdx].AddPlayer(p);
                }
                hasPlayer = false;
                return true;
            }
            //Normál eset
            else{
                hasPlayer = true;
                p.setPosition(this);
                return true;
            }
        }
        return false;
    }

    /**
     * Mikor a csövön lévő játékos továbblép a csőről, a cső felszabadul.
     * Beállítja a hasPlayer attribútumot hamisra.
     */
    @Override
    public void RemovePlayer(){
        hasPlayer = false;
    }

    /**
     * Egy cső ki tud lyukadni, ilyenkor kifolyik belőle a víz.
     * Beállítja a leaking attribútum értékét igazra.
     * @return igaz vagy hamis attól függően, hogy sikerült-e kilyukasztani a csövet.
     */
    @Override
    public boolean Explode(){
        if(breakable) {
            if (broken) {
                return false;
            }
            broken = true;
            return true;
        }
        return false;
    }

    /**
     * Ezzel a függvénnyel állítható be a cső állapota, amit paraméterként vár. Beállítja a
     * state attribútumának értékét a paraméterként kapott értékre.
     * @param newState A cső új állapota.
     * @return igaz vagy hamis annak függvényében, hogy ikerült-e.
     */
    @Override
    public boolean ChangeState(PipeState newState){
        if (state==PipeState.NORMAL)
        {
            state = newState;
            return true;
        }
        else if(newState==PipeState.NORMAL){
            state = newState;
            return true;
        }
        else{
            return false;
        }
    }

    /** Létrehoz az eredetivel (azzal amire meghívták) megegyező tulajdonságokkal
     * rendelkező passzív elemet, beállítja a koordinátáit az eredetihez kicsivel
     * eltérő értékekre és beállítja a kimenetét az eredeti kimenetére. Visszatérési
     * értékében megadja a létrehozott passzív komponenst.
     * @return a létrehozott passzív komponens
     */
    @Override
    public Passive CreateCopy() {
        String gID = "g_pi";
        int max=0;
        for (SComponent s: getMap().getComponents()) {
            if(s.getId().contains("g_pi")){
                String str = s.getId().substring(4);
                if(max<Integer.parseInt(str)){
                    max=Integer.parseInt(str);
                }
            }
        }
        gID += (max+1);
        if(!gID.equals("g_pi"))
        {
            Pipe p2 = new Pipe(gID,this.posX,this.posY);
            this.getMap().addComponents(p2);
            p2.setMap(this.getMap());
            p2.breakable = this.breakable;
            p2.broken = this.broken;
            p2.hasPlayer = false;
            p2.state = this.state;
            p2.hasWater = false;
            p2.ActiveComponents[0] = this.ActiveComponents[this.outputIndex];
            p2.setoutputIndex(0);
            this.ActiveComponents[this.outputIndex] = null;
            p2.setPos(this.posX+1, this.posY+1);
            return p2;
        }
        return null;
    }

    /**
     * Ez a függvény végzi egy pumpa lehelyezését a passzív elemre. Paraméterként egy
     * pumpát vár, amit aztán lehelyez a pályára. Meghívja a paraméterként kapott pumpán
     * az Insert(Passive p) függvényt és megadja saját magát paraméternek.
     * @param pump A pumpa amit le akar helyezni.
     * @return igaz vagy hamis attól függően, hogy sikerült-e
     */
    @Override
    public boolean PumpPlacement(SComponent pump){
        pump.setPos(this.posX, this.posY);
        pump.Insert(this);
        return true;
    }

    /**
     * Ezt a függvényt hívja a controller bizonyos időközönként. Beállítja a breakable
     * attribútum értékét igazra.
     */
    @Override
    public void Notify(){
        Random rnd = new Random();
        if(rnd.nextInt(10)<3){
            state= PipeState.NORMAL;
            breakable = true;
        }
    }

    @Override
    public int AddPoint(){
        if(hasWater && broken) {
            hasWater = false;
            return -1;
        }
        else if(hasWater && !(ActiveComponents[0]!=null && ActiveComponents[1]!=null)){
            hasWater = false;
            return -1;
        }
        return 0;

    }

    //Setters
    @Override
    public boolean setBroken(boolean param){ broken = param; return true; }

    @Override
    public boolean setBreakable(boolean param){ breakable = param; return true; }
}
