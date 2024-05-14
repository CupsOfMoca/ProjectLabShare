public class Spring extends Active {
	
	//Konstruktor
	public Spring(String s,int X, int Y)
	{
		hasWater = true; //Springnek mindig van vize
		PassiveComponents= new Passive[4];
		posX= X;
		posY= Y;
		id=s;
	}
	
	//A Spring a rákötött komponenseknek vizet ad át
	public void MoveWater(SComponent from) //from = null
	{
		
		for(int i = 0; i < PassiveComponents.length; i++)
		{
			if(PassiveComponents[i] != null)
			{
				PassiveComponents[i].MoveWater(this);
			}
		}
		
	}
}
