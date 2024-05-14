
public class Active extends SComponent{
	protected Passive[] PassiveComponents;
	public Active(){
	}
	/**
	 * Ez a függvény kicseréli az első paraméterként kapott elemet a második paraméterként kapottra
	 * @param p1 Az az elem amit ki kell cserélnie a függvénynek.
	 * @param p2 Az elem amire kicseréljük a másik elemet. Ezt kell eltárolni a függvény után.
	 */
	public void Swap(Passive p1,Passive p2)
	{
		int chaningIndex= getNeighbourIndex(p1);
		if (chaningIndex>=0)
		{
			Passive old = PassiveComponents[chaningIndex];
			if(old.ActiveComponents[0]!=null && old.ActiveComponents[0].equals(this)){
				old.ActiveComponents[0] = null;
			}else if (old.ActiveComponents[1]!=null && old.ActiveComponents[1].equals(this)){
				old.ActiveComponents[1] = null;
			}
			PassiveComponents[chaningIndex]=p2;
		}
	}

	/**
	 * A paraméterként kapott komponensbe áthelyezi a cső végét
	 * @param endPoint az aktív elem hányadik kimenetén elhelyezkedő passzív komponenst kösse el
	 * @param to melyik komponens
	 * @param toEndPoint hányadik kimenetére
	 * A komponens amelybe be kell kötni a csövet.
	 */
	public boolean PipeRelocation(int endPoint,Active to,int toEndPoint)
	{
		if(to !=null) {

			if (PassiveComponents[endPoint].ActiveComponents[0]!= null && PassiveComponents[endPoint].ActiveComponents[0].equals(this)) //Megnezzuk hogy ez az aktiv a keresett passzivnak hanyadik eleme.
			{

				if(PassiveComponents[endPoint].ActiveComponents[1]!=null){
					try{

						PassiveComponents[endPoint].ActiveComponents[1].Swap(PassiveComponents[endPoint], null);

					}
					catch (Exception e ){
						e.printStackTrace();
					}
				}

				PassiveComponents[endPoint].ActiveComponents[1] = to;//0

			}

			else if (PassiveComponents[endPoint].ActiveComponents[1]!= null&& PassiveComponents[endPoint].ActiveComponents[1].equals(this))
			{

				if(PassiveComponents[endPoint].ActiveComponents[0]!=null){

					PassiveComponents[endPoint].ActiveComponents[0].Swap(PassiveComponents[endPoint], null);

				}

				PassiveComponents[endPoint].ActiveComponents[0] = to;//0

			}
			if(to.PassiveComponents[toEndPoint]==null)
				to.PassiveComponents[toEndPoint]= PassiveComponents[endPoint];
			else {
				to.Swap(to.PassiveComponents[toEndPoint], PassiveComponents[endPoint]);
			}
			return true;
		}
		return  false;
	}

	/**
	 * Egy cső mind két végének lecsatolására szolgáló függvény. Passzív elem teljes lekötését végzi el. Paraméterként
	 * megkapja, hogy az aktív komponens hányadik passzív elemét csatlakoztassa le.
	 * @param select A lecsatolni kívánt cső sorszáma.
	 * @return igaz vagy hamis a sikerességtől függően.
	 */
	public boolean Detach(int select) {
		if(PassiveComponents[select] == null) return false;
		Passive idex= PassiveComponents[select];
		if(idex.ActiveComponents[0]!=null) idex.ActiveComponents[0].Swap(idex, null);
		if(idex.ActiveComponents[1]!=null) idex.ActiveComponents[1].Swap(idex, null);


		idex.ActiveComponents[0]=null;
		idex.ActiveComponents[1]=null;
		PassiveComponents[select] = null;

		return true;

	}
	//Setterek és getterek
	public Passive[] getNeighbours(){
		return PassiveComponents;
	}

	public int getNeighbourIndex(Passive p)
	{
		for(int i = 0;i<PassiveComponents.length;i++)
		{
			if(PassiveComponents[i]==p)
				return i;
		}
		return -1;
	}

	public void setNeighbours(int i, Passive p){
		PassiveComponents[i]=p;
	}
	public  void MoveWater(SComponent c){}
}
