import java.util.Random;

public class Pump extends Active implements Notifiable {

	//Attribútumok
	private int inputIndex; //Tárolja annak a kimenetének az indexét amelyikből folyik a víz a pumpán keresztül
	private int outputIndex; //Tárolja annak a kimenetének az indexét amibe folyik a víz a pumpán keresztül
	private boolean randomizable = true;

	//Konstruktor
	public Pump(String s,int X, int Y)
	{
		hasWater = false; //Alapból nincs vize a pumpának
		PassiveComponents= new Passive[4];
		broken = false; //Alapból a pumpa működőképes
		inputIndex = 0; //Alapból nincs input választva (mivel a pumpa nem kapcsolódik semmihez)
		outputIndex = 0; //Alapból nincs input választva (mivel a pumpa nem kapcsolódik semmihez)
		posX= X;
		posY= Y;
		id=s;
	}

	//Ez a függvény javítja meg a pumpát
	public boolean Fix()
	{
		if(broken == true) {
			broken = false;
			return true;
		}
		return false;

	}
	public boolean setBroken(boolean b)
	{
		broken = b;
		return true;

	}
	public boolean isInput(Passive p){
		if(PassiveComponents[inputIndex]==p){
			return true;
		}
		return false;
	}

	//A Pumpa a paraméterként kapott komponensből vizet vesz át, és a kimeneti komponensének vizet ad át
	public void MoveWater(SComponent from)
	{
		if(hasWater && !broken && !movedWater) {
			if(PassiveComponents[outputIndex]!=null) {
				PassiveComponents[outputIndex].MoveWater(this);
				hasWater = false;
			}
		}
		else if( from == PassiveComponents[inputIndex])
		{
			hasWater = true;
			movedWater = true;
		}
	}

	//Állítja a víz folyásának irányát a pumpán; az első paraméter lesz a víz bemenete, a második a kimenete
	public boolean SetPump(int newIn, int newOut)
	{
		if(newIn>=0 && newOut>=0 && newIn <4 && newOut <4 ) {
			//belső működés
			inputIndex = newIn;
			outputIndex = newOut;
			return true;
		}
		return false;

	}

	//Ezt a függvényt hívja meg egy passzív elem a pumpalehelyezés elvégzéséhez
	public boolean Insert(Passive oldPassive)
	{
		if(oldPassive == null) return false;
		Passive NewPassive;
		if(oldPassive.getMap() == null) System.out.println("No map?");
		try{
			NewPassive = oldPassive.CreateCopy();
			oldPassive.getMap().addComponents(NewPassive);
			oldPassive.getMap().addComponents(this);
			this.Swap(NewPassive,null);
			this.Swap(oldPassive, null);
			NewPassive.ActiveComponents[NewPassive.inputIndex] = this;
			oldPassive.ActiveComponents[oldPassive.outputIndex] = this;

			//Hozzáadjuk a pumpához a csöveket
			this.PassiveComponents[3] = oldPassive;
			this.PassiveComponents[1] = NewPassive;


			// Ha a cső mindkét végén van komponens, akkor a cső pozíciója a két komponens közé esik pont félútra. Összeadjuk a cső végein lévő komponensek koordinátáit és elosztjuk kettővel.
			if(oldPassive.ActiveComponents[0] != null && oldPassive.ActiveComponents[1] != null) {
				oldPassive.setPos((oldPassive.ActiveComponents[0].getPosX() + oldPassive.ActiveComponents[1].getPosX()) / 2, (oldPassive.ActiveComponents[0].getPosY() + oldPassive.ActiveComponents[1].getPosY()) / 2);
			}
			if(NewPassive.ActiveComponents[0] != null && NewPassive.ActiveComponents[1] != null){
				NewPassive.setPos((NewPassive.ActiveComponents[0].getPosX() + NewPassive.ActiveComponents[1].getPosX()) / 2, (NewPassive.ActiveComponents[0].getPosY() + NewPassive.ActiveComponents[1].getPosY()) / 2);
			}

			// Ha pedig a cső egyik végén van csak komponens, akkor értelemszerűen nem lehet két komponens közé tenni félútra. Ezért úgy oldottam meg, hogy először megnézem melyik vége "szabad" és a
			// másik végén lévő kopmonenshez igazítom a csövet. Mivel óramutató járása szerint indexelünk (tehát 0;2 - y tengely, 1;3 - x tengely), így megnézem, hogy az aktív komponens amihez a cső
			// csatlakozik, annak melyik indexére van kötve a cső és aszerint tolom el egy kicsit a megfelelő tengely mentén. Pl.: ha a cső a komponens 0. indexére van kötve, akkor a komponens y koordi-
			// nátájából veszek el egy keveset, az x pedig megegyezik a komponens x koordinátájával.

			if(oldPassive.ActiveComponents[0] == null && oldPassive.ActiveComponents[1] != null){
				if(oldPassive.ActiveComponents[1].PassiveComponents[0] != null && oldPassive.ActiveComponents[1].PassiveComponents[0].equals(oldPassive))	oldPassive.setPos(oldPassive.ActiveComponents[1].getPosX(), oldPassive.ActiveComponents[1].getPosY() - 180);
				else if(oldPassive.ActiveComponents[1].PassiveComponents[1] != null && oldPassive.ActiveComponents[1].PassiveComponents[1].equals(oldPassive))	oldPassive.setPos(oldPassive.ActiveComponents[1].getPosX() + 180, oldPassive.ActiveComponents[1].getPosY());
				else if(oldPassive.ActiveComponents[1].PassiveComponents[2] != null && oldPassive.ActiveComponents[1].PassiveComponents[2].equals(oldPassive))	oldPassive.setPos(oldPassive.ActiveComponents[1].getPosX(), oldPassive.ActiveComponents[1].getPosY() + 180);
				else if(oldPassive.ActiveComponents[1].PassiveComponents[3] != null)	oldPassive.setPos(oldPassive.ActiveComponents[1].getPosX() - 180, oldPassive.ActiveComponents[1].getPosY());
			}
			if(oldPassive.ActiveComponents[1] == null && oldPassive.ActiveComponents[0] != null){
				if(oldPassive.ActiveComponents[0].PassiveComponents[0] != null && oldPassive.ActiveComponents[0].PassiveComponents[0].equals(oldPassive))	oldPassive.setPos(oldPassive.ActiveComponents[0].getPosX(), oldPassive.ActiveComponents[0].getPosY() - 180);
				else if(oldPassive.ActiveComponents[0].PassiveComponents[1] != null && oldPassive.ActiveComponents[0].PassiveComponents[1].equals(oldPassive))	oldPassive.setPos(oldPassive.ActiveComponents[0].getPosX() + 180, oldPassive.ActiveComponents[0].getPosY());
				else if(oldPassive.ActiveComponents[0].PassiveComponents[2] != null && oldPassive.ActiveComponents[0].PassiveComponents[2].equals(oldPassive))	oldPassive.setPos(oldPassive.ActiveComponents[0].getPosX(), oldPassive.ActiveComponents[0].getPosY() + 180);
				else if(oldPassive.ActiveComponents[0].PassiveComponents[3] != null)	oldPassive.setPos(oldPassive.ActiveComponents[0].getPosX() - 180, oldPassive.ActiveComponents[0].getPosY());
			}

			// Az előzőt az új csőre is megcsinálom.
			if(NewPassive.ActiveComponents[0] == null && NewPassive.ActiveComponents[1] != null){
				if(NewPassive.ActiveComponents[1].PassiveComponents[0] != null && NewPassive.ActiveComponents[1].PassiveComponents[0].equals(NewPassive))	NewPassive.setPos(NewPassive.ActiveComponents[1].getPosX(), NewPassive.ActiveComponents[1].getPosY() - 180);
				else if(NewPassive.ActiveComponents[1].PassiveComponents[1] != null && NewPassive.ActiveComponents[1].PassiveComponents[1].equals(NewPassive))	NewPassive.setPos(NewPassive.ActiveComponents[1].getPosX() + 180, NewPassive.ActiveComponents[1].getPosY());
				else if(NewPassive.ActiveComponents[1].PassiveComponents[2] != null && NewPassive.ActiveComponents[1].PassiveComponents[2].equals(NewPassive))	NewPassive.setPos(NewPassive.ActiveComponents[1].getPosX(), NewPassive.ActiveComponents[1].getPosY() + 180);
				else if(NewPassive.ActiveComponents[1].PassiveComponents[3] != null)	NewPassive.setPos(NewPassive.ActiveComponents[1].getPosX() - 180, NewPassive.ActiveComponents[1].getPosY());
			}
			if(NewPassive.ActiveComponents[1] == null && NewPassive.ActiveComponents[0] != null){
				if(NewPassive.ActiveComponents[0].PassiveComponents[0] != null && NewPassive.ActiveComponents[0].PassiveComponents[0].equals(NewPassive))	NewPassive.setPos(NewPassive.ActiveComponents[0].getPosX(), NewPassive.ActiveComponents[0].getPosY() - 180);
				else if(NewPassive.ActiveComponents[0].PassiveComponents[1] != null && NewPassive.ActiveComponents[0].PassiveComponents[1].equals(NewPassive))	NewPassive.setPos(NewPassive.ActiveComponents[0].getPosX() + 180, NewPassive.ActiveComponents[0].getPosY());
				else if(NewPassive.ActiveComponents[0].PassiveComponents[2] != null && NewPassive.ActiveComponents[0].PassiveComponents[2].equals(NewPassive))	NewPassive.setPos(NewPassive.ActiveComponents[0].getPosX(), NewPassive.ActiveComponents[0].getPosY() + 180);
				else if(NewPassive.ActiveComponents[0].PassiveComponents[3] != null)	NewPassive.setPos(NewPassive.ActiveComponents[0].getPosX() - 180, NewPassive.ActiveComponents[0].getPosY());
			}

			//Kicseréljük a régi csövet az újra azon az aktív komponensen, amihez mostantól az új cső csatlakozik
			if(NewPassive.ActiveComponents[NewPassive.outputIndex] != null)
				NewPassive.ActiveComponents[NewPassive.outputIndex].Swap(oldPassive,NewPassive);
		}catch(Exception e){
			e.printStackTrace();
		}



		return true;
	}

	//Ez a függvény teszi tönkre a pumpát ténylegesen
	public void Failure()
	{
		broken = true;
	}

	// Ez függvény rontja el a pumpát bizonyos időnként (ha a pumpa működik)
	public void Notify()
	{
		if(randomizable) {
			Random r = new Random();
			int ran =r.nextInt(50);
			if(ran == 0)
				if(!broken){
					Failure();
				}
		}
	}

}
