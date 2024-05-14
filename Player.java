import java.util.Map;
import java.util.Scanner;

public class Player {
	
	/**
	 * Action Point, megszabja a jatekos cselekedeteinek szamat.
	 */
	protected int AP;
	protected int maxAP;

	protected String id;

	public String getId(){
		return "";
	}
	
	/**
	 * Jatekos pozicioja, egy adott komponens amin all.
	 */
	protected SComponent position;

	/**
	 * Egy komponens amit a jatekos felvett. Pl: Pumpa.
	 */
	protected SComponent item;

	/**
	 * Eltarolja hogy a jatekos csohoz van-e ragadva
	 */
	protected boolean rooted = false;

	
	//----------------------------------------------------------------------------------------
	
	
	/*
	 * Player Default Constructor
	 */
	Player(SComponent pos,int ap){
		position = pos;
		maxAP = ap;
		AP= ap;
	}
	Player(){}
	/**
	 * A jatekos atlep egy parameterkent kapott komponensre. 
	 * @param c : SComponent Hova lepjen a jatekos
	 */
	public boolean Move(SComponent c) {

		if(position.getState()!=null && !position.getState().equals(PipeState.STICKY))
			rooted=false;
		SComponent oldPos= position;
		if(AP > 0 && !rooted && c.AddPlayer(this))
		{
			oldPos.RemovePlayer();
			SpendAP(1);
			return true;
		}
		return false;
	}
	/**
	 * A jatekos atallitja a pumpanak amin all a ki es bemenetet.
	 * @param pumpin A cso bemenetelenek indexe
	 * @param pumpout A cso kimenetenek indexe
	 */
	public boolean SetPump(int pumpin,int pumpout) {
		if(AP > 0 && position.SetPump(pumpin,pumpout))
		{
			SpendAP(1);
			return true;
		};
		return false;
	}
	/**
	 *  A jatekos passzol.
	 */
	public void Pass() {
		SpendAP(this.AP);
	}
	/**
	 *  A jatekos elmenekul a poziciojarol.
	 * @param newPos : SComponent, hova ugorjon a jatekos
	 */
	public boolean Escape(SComponent newPos) {
		SComponent oldPos= position;
		if(AP > 0 && !rooted && newPos.AddPlayer(this))
		{
			oldPos.RemovePlayer();
			SpendAP(this.AP);
			return true;
		}
		return false;
	}
	
	//----------------------------------------------------------------

	/**
	 * Visszaadja a jatekosnal levo item-et
	 * @return item : SComponent
	 */

	public SComponent getItem(){
		return item;
	}
	
	/**
	 * Beallitja a jatekos item-et
	 * az adott parameterkent kapott komponensre.
	 * @param sc
	 */
	public void setItem(Pump sc){
		item = sc;
	}

	public void setAP(int szam){
		AP=szam;
	}

	/**
	 * Kilyukasztja a csovet amin a játékos álla
	 */
	public boolean Puncture()
	{
		if(AP > 0 && position.Explode())
		{
			SpendAP(1);
			return true;
		}
		return false;
	}

	/**
	 * Ragadossa tesz egy csovet
	 */
	public boolean MakePipeSticky()
	{
		if(AP>0 && this.position.ChangeState(PipeState.STICKY))
		{
			SpendAP(1);
			return true;
		}
		return false;
	}

	/**
	 * Levon a jatekos Action Point-jaibol p-t
	 * @param p
	 */

	public void SpendAP(int p)
	{
		if(AP > 0)
			this.AP-=p;
	}

	/**
	 * Visszaallitja a jatekos Action Point-jait a maximumra
	 */
	public void resetAP()
	{
		AP = maxAP;
	}

	/**
	 * Beallitja a jatekos ragados allapotat
	 * @param r boolean ertek, megadja mi legyen az uj statusz
	 */
	public void setRooted(boolean r)
	{
		rooted = r;
	}

	public void setPosition(SComponent c)
	{
		position = c;
	}
	public SComponent getPosition()
	{
		return position;
	}
	public boolean getRooted()
	{
		return rooted;
	}
	public int getAP()
	{
		return AP;
	}
}
