public class Scoreboard {
    private int MechPoint = 0 ; //mechanic points
    private int SabPoint = 0; // saboteur points

    /**
     * Kezeli a csapatok pontszerzését.
     * @param m A map.
     */
    public void TallyPoints(Map m){
        for(SComponent c : m.getComponents()){
            int p = c.AddPoint();
            switch(p) {
                case 0:
                    break;
                case 1:
                    MechPoint+=c.getWaterCounter();
                    break;
                case -1:
                    SabPoint++;
                    break;
                default:
                    break;
            }

        }
    }
    //returns the mech points
    public int getMechPoints(){
        return MechPoint;
    }
    //returns the sab points
    public int getSabPoints(){
        return SabPoint;
    }

    //resets the scoreboard
    public void resetpoints(){
        MechPoint = 0;
        SabPoint = 0;
    }
}
