
public class Saboteur extends Player {

	
	/**
	 * Saboteur Constructor, parameterkent egy helyet kap ahol letrejon
	 * @param pos:A kezdeti pozicio
	 * @param ap : A jatekos maximalis ActionPont-jainak szama
	 */
	Saboteur(SComponent pos,int ap, String idx){
		position = pos;
		maxAP = ap;
		AP=ap;
		id= idx;
	}

	public String getId(){
		return id;
	}
	
	/**
	 * Saboteur csuszossa teszi a csovet amin all.
	 */
	public boolean MakePipeSlippery() {
		if(AP > 0 && this.position.ChangeState(PipeState.SLIPPERY))
		{
			SpendAP(1);
			return true;
		}
		return false;

	}
}
