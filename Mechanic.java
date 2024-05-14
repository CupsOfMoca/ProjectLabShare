import java.util.ArrayList;

public class Mechanic extends Player {

	
	/**
	 * Mechanic Constructor, parameterkent egy helyet kap ahol letrejon
	 * @param pos :A kezdeti pozicio
	 * @param ap : A jatekos maximalis ActionPont-jainak szama
	 */
	Mechanic(SComponent pos,int ap, String idx){
		position = pos;
		maxAP = ap;
		AP=ap;
		id=idx;
	}
	public String getId(){
		return id;
	}
	
	/**
	 *  Mechanic megjavitja a komponenst amin all.
	 */
	public boolean FixComponent() {
		if(AP > 0 && position.Fix())
		{
			SpendAP(1);
			return true;
		}
		return false;
	}
	
	/**
	 * Mechanic osszeszed egy pumpat a komponensrol amin all.
	 */
	public boolean CollectPump() {
		if(AP > 0 && item == null)
		{
			item = position.CollectPump();
			if(item != null)
			{
				SpendAP(1);
				return true;
			}

		}
		return false;
	}
	
	/**
	 * Mechanic lerakja a felvett pumpat.
	 */
	public boolean PlacePump() {
		if(AP > 0 && item != null)
		{
			if(position.PumpPlacement(item))
			{
				item = null;
				SpendAP(1);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Mechanic atkoti egy cso masik veget egy masik pumpaba. A cso abba a pumpaba van kotve amin all.
	 * @param endPoint Melyik veget kotjuk at
	 * @param to Melyik komponensbe akarjuk atkotni
	 * @param newEndPoint Az uj komponens melyik oldalaba kotjuk
	 */
	public boolean RelocatePipe(int endPoint,Active to,int newEndPoint){
		if(AP > 0 && position.PipeRelocation(endPoint,to,newEndPoint))
		{
			SpendAP(1);
			return true;
		}
		return false;
	}

	/**
	 *Lekapcsolja ecs cso mindket veget
	 * @param pi Lecsatolando cso indexe
	 */
	public boolean Detach(int pi)
	{
		if(AP>0 && position.Detach(pi))
		{
			SpendAP(1);
			return true;
		}
		return false;
	}
}
