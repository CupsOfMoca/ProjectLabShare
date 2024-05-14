import java.util.Random;

public class Cistern extends Active implements Notifiable {

	//Attribútumok
	private Pump Pump; //Tárolja a ciszternánál lévő generált pumpát
	private int generate; //Ez majd egy random lesz a gyakorlatban, és csak a Notify-on belül fog létezni [TESZTELÉSHEZ]
	private boolean randomizable = true;
	private  int waterCounter;
	//Konstruktor
	public Cistern(String s,int X, int Y)
	{
		hasWater = false; //Alapból a ciszternának nincs vize
		PassiveComponents= new Passive[4];
		Pump = null; //Alapból a ciszternának nincs pumpája
		posX= X;
		posY= Y;
		id=s;
	}

	public int getWaterCounter(){
		int c=waterCounter;
		waterCounter=0;
		return c;
	}

	//A Ciszterna a paraméterként kapott komponensből vizet vesz át
	public void MoveWater(SComponent from)
	{
		if(from != null)
		{
			waterCounter++;
			hasWater=true;	
		}
	}

	//Ez a függvény létrehoz egy pumpát (ha még nincs), amit a ciszterna eltárol és a játékosok fel tudnak venni
	public void GeneratePump()
	{
		if(Pump == null)
		{
			String gID = "g_pu";
			int max=0;
			for (SComponent s: getMap().getComponents()) {
				if(s.getId().contains("g_pu")){
					String str = s.getId().substring(4);
					if(max<Integer.parseInt(str)){
						max=Integer.parseInt(str);
					}
				}
			}
			gID += (max+1);
			if(!gID.equals("g_pu"))
			{
				Pump = new Pump(gID,this.posX, this.posY);
			}
		}

	}

	//Ez a függvény generál egy csövet a ciszterna egyik szabad kimenetére (ha van)
	public boolean GeneratePipe(int input)
	{
		if(PassiveComponents[input] == null)
		{
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
				Pipe newPipe = new Pipe(gID, this.posX, this.posY);
				this.getMap().addComponents(newPipe);
				newPipe.setMap(this.getMap());
				PassiveComponents[input] = newPipe;
				switch(input){
					case 0:
						PassiveComponents[input].setPos(this.posX,this.posY-100);
						newPipe.ActiveComponents[0] = this;
						break;
					case 1:
						PassiveComponents[input].setPos(this.posX+100,this.posY);
						newPipe.ActiveComponents[1] = this;
						break;
					case 2:
						PassiveComponents[input].setPos(this.posX,this.posY+100);
						newPipe.ActiveComponents[0] = this;
						break;
					case 3:
						PassiveComponents[input].setPos(this.posX-100,this.posY);
						newPipe.ActiveComponents[0] = this;
						break;
				}
			return true;
		}
		return false;

	}

	//Ezen függvény segítségével veszi magához a ciszternánál a játékos a generált pumpát
	public Pump CollectPump()
	{

		Pump CollectedPump = Pump;
		Pump = null;

		return CollectedPump;
	}
	public SComponent getItem()
	{
		return Pump;
	}
	public int AddPoint(){
		if(hasWater) {
			hasWater= false;
			return 1;
		}
		else return 0;
	}

	//Ez a függvény indítja meg a cső, illetve a pumpa generálási folyamatot
	public void Notify()
	{
		if(randomizable) {
			Random r = new Random();
			generate=r.nextInt(50);


			if(generate == 1)
			{
				GeneratePump();
			}
			else if(generate == 2)
			{
				boolean generated = false;
				int i = 0;
				while(generated != true && i < 4)
				{
					generated = GeneratePipe(i);
					i++;
				}
			}
			generate = 0;

		}

	}

}
