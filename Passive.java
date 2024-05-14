public class Passive extends SComponent{

    protected Active[] ActiveComponents;
    protected int inputIndex, outputIndex;
    public Passive()
    {

    }

    /**
     * Egy passzív elem kettévágását végző függvény. A leszármazottak felülírják.
     * @return A létrehozott passzív elem.
     */
    public Passive CreateCopy()
    {
        return new Passive();
    }

    public  void MoveWater(SComponent c){}

    public void setoutputIndex(int param){
        if(param == 0){
            outputIndex = param;
            inputIndex = 1;
        }
        else{
            outputIndex = 1;
            inputIndex = param;
        }
    }

    @Override
    public Active[] getNeighbours(){ return ActiveComponents; }

    public void setNeighbours(int i, Active p){
        ActiveComponents[i]=p;
    }


}