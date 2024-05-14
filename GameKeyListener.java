import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.util.ArrayList;

import javax.swing.JOptionPane;

public class GameKeyListener implements KeyListener {

	/**
	 * a pályán található grafikus elemek listája referenciaként átadva (ezen
	 * keresztül hívja meg a GraphicsMap osztály UpdateAll() függvényét egy akció
	 * lefutása esetén, illetve bizonyos esetekben frissíti is a gMap tartalmát)
	 */
	private GraphicsMap gMap;
	/**
	 * tárolja az aktív játékost (az a játékos, aki jelenleg cselekszik)
	 */
	private Player activePlayer;

	public void setActivePlayer(Player activePlayer) {
		this.activePlayer = activePlayer;
	}
	public GameKeyListener(GraphicsMap gm)
	{
		gMap = gm;
	}
	/**
	 * A KeyListener interfész egyik kötelezően
	 * implementálandó függvénye. A gombnyomások ellenőrzését mi a
	 * keyReleased()-ben ellenőrizzük, tehát ez a függvény üresen marad.
	 */
	@Override
	public void keyTyped(KeyEvent e) {}
	/**
	 * A KeyListener interfész egyik kötelezően
	 * implementálandó függvénye. Egy billentyű megnyomását és felengedését
	 * követően ellenőrzi, hogy az adott billentyűhöz tartozik-e felhasználói akció. Ha
	 * igen, akkor az adott akció végrehajtását megpróbálja elindítani. Hogyha a
	 * művelet végrehajtásához szükség van további felhasználói paraméterekre,
	 * akkor megjeleníti a szükséges almenüket. A művelet végrehajtását követően
	 * meghívja a gMap UpdateAll() függvényét, hogy a grafikus elemek frissüljenek,
	 * illetve szükség esetén frissíti a gMap tartalmát.
	 */

	/**
	 * A KeyListener interfész egyik kötelezően
	 * implementálandó függvénye. Egy billentyű megnyomását és felengedését
	 * követően ellenőrzi, hogy az adott billentyűhöz tartozik-e felhasználói akció. Ha
	 * igen, akkor az adott akció végrehajtását megpróbálja elindítani. Hogyha a
	 * művelet végrehajtásához szükség van további felhasználói paraméterekre,
	 * akkor megjeleníti a szükséges almenüket. A művelet végrehajtását követően
	 * meghívja a gMap UpdateAll() függvényét, hogy a grafikus elemek frissüljenek,
	 * illetve szükség esetén frissíti a gMap tartalmát.
	 */
	@Override
	public void keyPressed(KeyEvent e) {}

	/**
	 * A KeyListener interfész egyik kötelezően
	 * implementálandó függvénye. A gombnyomások ellenőrzését mi a
	 * keyReleased()-ben ellenőrizzük, tehát ez a függvény üresen marad.
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		JOptionPane errorMsg = new JOptionPane();
		JOptionPane selector = new JOptionPane();
		SComponent pos = activePlayer.getPosition();
		switch(e.getKeyChar())
		{
			default: return;
			case 'w':
				if(pos.getNeighbours().length == 2)
				{
					errorMsg.showMessageDialog(null, "Ebben az iranyban nem talalhato szomszedos komponens!", "HIBA!", JOptionPane.ERROR_MESSAGE);
				}
				else if(pos.getNeighbours().length == 4)
				{
					if(pos.getNeighbours()[0] == null)
					{
						errorMsg.showMessageDialog(null, "Ebben az iranyban nem talalhato szomszedos komponens!", "HIBA!", JOptionPane.ERROR_MESSAGE);
					}
					else if(activePlayer.getAP() < 1)
					{
						errorMsg.showMessageDialog(null, activePlayer.getId() + "-nek nincs eleg AP-je a muvelet elvegzesehez!", "HIBA!", JOptionPane.ERROR_MESSAGE);
					}
					else if(pos.getState()!=null && pos.getState().equals(PipeState.STICKY) && activePlayer.getRooted())
					{
						errorMsg.showMessageDialog(null, activePlayer.getId() + " nem tud lepni, mivel be van ragadva!", "HIBA!", JOptionPane.ERROR_MESSAGE);
					}
					else if(!activePlayer.Move(pos.getNeighbours()[0]))
					{
						errorMsg.showMessageDialog(null, activePlayer.getId() + " nem lephet at a(z) " + pos.getNeighbours()[0].getId() + " komponensre, mivel az mar foglalt!", "HIBA!", JOptionPane.ERROR_MESSAGE);
					}
				}
				break; //moveUp
			case 'd': 
				if(pos.getNeighbours().length == 2)
				{
					if(pos.getNeighbours()[0] == null)
					{
						errorMsg.showMessageDialog(null, "Ebben az iranyban nem talalhato szomszedos komponens!", "HIBA!", JOptionPane.ERROR_MESSAGE);
					}
					else if(activePlayer.getAP() < 1)
					{
						errorMsg.showMessageDialog(null, activePlayer.getId() + "-nek nincs eleg AP-je a muvelet elvegzesehez!", "HIBA!", JOptionPane.ERROR_MESSAGE);
					}
					else if(pos.getState()!=null && pos.getState().equals(PipeState.STICKY)&& activePlayer.getRooted())
					{
						errorMsg.showMessageDialog(null, activePlayer.getId() + " nem tud lepni, mivel be van ragadva!", "HIBA!", JOptionPane.ERROR_MESSAGE);
					}
					else if(!activePlayer.Move(pos.getNeighbours()[0]))
					{
						errorMsg.showMessageDialog(null, activePlayer.getId() + " nem lephet at a(z) " + pos.getNeighbours()[0].getId() + " komponensre, mivel az mar foglalt!", "HIBA!", JOptionPane.ERROR_MESSAGE);
					}
				}
				else if(pos.getNeighbours().length == 4)
				{
					if(pos.getNeighbours()[1] == null)
					{
						errorMsg.showMessageDialog(null, "Ebben az iranyban nem talalhato szomszedos komponens!", "HIBA!", JOptionPane.ERROR_MESSAGE);
					}
					else if(activePlayer.getAP() < 1)
					{
						errorMsg.showMessageDialog(null, activePlayer.getId() + "-nek nincs eleg AP-je a muvelet elvegzesehez!", "HIBA!", JOptionPane.ERROR_MESSAGE);
					}
					else if(pos.getState()!=null && pos.getState().equals(PipeState.STICKY)&& activePlayer.getRooted())
					{
						errorMsg.showMessageDialog(null, activePlayer.getId() + " nem tud lepni, mivel be van ragadva!", "HIBA!", JOptionPane.ERROR_MESSAGE);
					}
					else if(!activePlayer.Move(pos.getNeighbours()[1]))
					{
						errorMsg.showMessageDialog(null, activePlayer.getId() + " nem lephet at a(z) " + pos.getNeighbours()[1].getId() + " komponensre, mivel az mar foglalt!", "HIBA!", JOptionPane.ERROR_MESSAGE);
					}
				}
				break; //moveRight
			case 's':
				if(pos.getNeighbours().length == 2)
				{
					errorMsg.showMessageDialog(null, "Ebben az iranyban nem talalhato szomszedos komponens!", "HIBA!", JOptionPane.ERROR_MESSAGE);
				}
				else if(pos.getNeighbours().length == 4)
				{
					if(pos.getNeighbours()[2] == null)
					{
						errorMsg.showMessageDialog(null, "Ebben az iranyban nem talalhato szomszedos komponens!", "HIBA!", JOptionPane.ERROR_MESSAGE);
					}
					else if(activePlayer.getAP() < 1)
					{
						errorMsg.showMessageDialog(null, activePlayer.getId() + "-nek nincs eleg AP-je a muvelet elvegzesehez!", "HIBA!", JOptionPane.ERROR_MESSAGE);
					}
					else if(pos.getState()!=null && pos.getState().equals(PipeState.STICKY)&& activePlayer.getRooted())
					{
						errorMsg.showMessageDialog(null, activePlayer.getId() + " nem tud lepni, mivel be van ragadva!", "HIBA!", JOptionPane.ERROR_MESSAGE);
					}
					else if(!activePlayer.Move(pos.getNeighbours()[2]))
					{
						errorMsg.showMessageDialog(null, activePlayer.getId() + " nem lephet at a(z) " + pos.getNeighbours()[2].getId() + " komponensre, mivel az mar foglalt!", "HIBA!", JOptionPane.ERROR_MESSAGE);
					}
				}
				break; //moveDown
			case 'a': 
				if(pos.getNeighbours().length == 2)
				{
					if(pos.getNeighbours()[1] == null)
					{
						errorMsg.showMessageDialog(null, "Ebben az iranyban nem talalhato szomszedos komponens!", "HIBA!", JOptionPane.ERROR_MESSAGE);
					}
					else if(activePlayer.getAP() < 1)
					{
						errorMsg.showMessageDialog(null, activePlayer.getId() + "-nek nincs eleg AP-je a muvelet elvegzesehez!", "HIBA!", JOptionPane.ERROR_MESSAGE);
					}
					else if(pos.getState()!=null && pos.getState().equals(PipeState.STICKY)&& activePlayer.getRooted())
					{
						errorMsg.showMessageDialog(null, activePlayer.getId() + " nem tud lepni, mivel be van ragadva!", "HIBA!", JOptionPane.ERROR_MESSAGE);
					}
					else if(!activePlayer.Move(pos.getNeighbours()[1]))
					{
						errorMsg.showMessageDialog(null, activePlayer.getId() + " nem lephet at a(z) " + pos.getNeighbours()[1].getId() + " komponensre, mivel az mar foglalt!", "HIBA!", JOptionPane.ERROR_MESSAGE);
					}
				}
				else if(pos.getNeighbours().length == 4)
				{
					if(pos.getNeighbours()[3] == null)
					{
						errorMsg.showMessageDialog(null, "Ebben az iranyban nem talalhato szomszedos komponens!", "HIBA!", JOptionPane.ERROR_MESSAGE);
					}
					else if(activePlayer.getAP() < 1)
					{
						errorMsg.showMessageDialog(null, activePlayer.getId() + "-nek nincs eleg AP-je a muvelet elvegzesehez!", "HIBA!", JOptionPane.ERROR_MESSAGE);
					}
					else if(pos.getState()!=null && pos.getState().equals(PipeState.STICKY)&& activePlayer.getRooted())
					{
						errorMsg.showMessageDialog(null, activePlayer.getId() + " nem tud lepni, mivel be van ragadva!", "HIBA!", JOptionPane.ERROR_MESSAGE);
					}
					else if(!activePlayer.Move(pos.getNeighbours()[3]))
					{
						errorMsg.showMessageDialog(null, activePlayer.getId() + " nem lephet at a(z) " + pos.getNeighbours()[3].getId() + " komponensre, mivel az mar foglalt!", "HIBA!", JOptionPane.ERROR_MESSAGE);
					}
				}
				break; //moveLeft
			case 'f':
				if(!Main.mechanics.contains((activePlayer)))
				{
					errorMsg.showMessageDialog(null, "Ezt a muveletet csak szerelok hajthatjak vegre!", "HIBA!", JOptionPane.ERROR_MESSAGE);
					break;
				}
				int idxP = 0;
				for(int i = 0; i < Main.mechanics.size(); i++)
				{
					if(Main.mechanics.get(i).getId().equals(activePlayer.getId()))
					{
						idxP = i;
						i = Main.mechanics.size();
					}
				}
				Mechanic activeMechanicF = Main.mechanics.get(idxP);
				if(!pos.getBroken())
				{
					errorMsg.showMessageDialog(null, "A komponenst nem lehet megszerelni, mivel nem torott!", "HIBA!", JOptionPane.ERROR_MESSAGE);
					break;
				}
				if(activePlayer.getAP() < 1)
				{
					errorMsg.showMessageDialog(null, activePlayer.getId() + "-nek nincs eleg AP-je a muvelet elvegzesehez!", "HIBA!", JOptionPane.ERROR_MESSAGE);
					break;
				}
				activeMechanicF.FixComponent();
				break; //fix
			case 'p': 
				if(activePlayer.getAP() < 1)
				{
					errorMsg.showMessageDialog(null, activePlayer.getId() + "-nek nincs eleg AP-je a muvelet elvegzesehez!", "HIBA!", JOptionPane.ERROR_MESSAGE);
					break;
				}
				else if(!activePlayer.Puncture())
				{
					errorMsg.showMessageDialog(null, "A komponenst nem lehet kiszurni (vagy mar ki van szurva)!", "HIBA!", JOptionPane.ERROR_MESSAGE);
				}
				break; //puncture
			case 'c': 
				if(!Main.mechanics.contains((activePlayer)))
				{
					errorMsg.showMessageDialog(null, "Ezt a muveletet csak szerelok hajthatjak vegre!", "HIBA!", JOptionPane.ERROR_MESSAGE);
					break;
				}
				idxP = 0;
				for(int i = 0; i < Main.mechanics.size(); i++)
				{
					if(Main.mechanics.get(i).getId().equals(activePlayer.getId()))
					{
						idxP = i;
						i = Main.mechanics.size();
					}
				}
				Mechanic activeMechanicP = Main.mechanics.get(idxP);
				if(pos.getItem() == null)
				{
					errorMsg.showMessageDialog(null, pos.getId() + "-nel jelenleg nincsen generalt pumpa!", "HIBA!", JOptionPane.ERROR_MESSAGE);
					break;
				}
				if(activeMechanicP.getItem() != null)
				{
					errorMsg.showMessageDialog(null, activePlayer.getId() + "-nel mar van pumpa!", "HIBA!", JOptionPane.ERROR_MESSAGE);
					break;
				}
				if(activePlayer.getAP() < 1)
				{
					errorMsg.showMessageDialog(null, activePlayer.getId() + "-nek nincs eleg AP-je a muvelet elvegzesehez!", "HIBA!", JOptionPane.ERROR_MESSAGE);
					break;
				}
				activeMechanicP.CollectPump();
				break; //collect
			case 'g':
				if(!Main.mechanics.contains((activePlayer)))
				{
					errorMsg.showMessageDialog(null, "Ezt a muveletet csak szerelok hajthatjak vegre!", "HIBA!", JOptionPane.ERROR_MESSAGE);
					break;
				}
				idxP = 0;
				for(int i = 0; i < Main.mechanics.size(); i++)
				{
					if(Main.mechanics.get(i).getId().equals(activePlayer.getId()))
					{
						idxP = i;
						i = Main.mechanics.size();
					}
				}
				Mechanic activeMechanicP2 = Main.mechanics.get(idxP);
				if(activeMechanicP2.getItem() == null)
				{
					errorMsg.showMessageDialog(null, activePlayer.getId() + "-nel nincs pumpa!", "HIBA!", JOptionPane.ERROR_MESSAGE);
					break;
				}
				if(activePlayer.getAP() < 1)
				{
					errorMsg.showMessageDialog(null, activePlayer.getId() + "-nek nincs eleg AP-je a muvelet elvegzesehez!", "HIBA!", JOptionPane.ERROR_MESSAGE);
					break;
				}
				if(!activeMechanicP2.PlacePump()){
					errorMsg.showMessageDialog(null, "A jatekos jelenlegi poziciojara nem helyezheto pumpa!", "HIBA!", JOptionPane.ERROR_MESSAGE);
					break;
				}
				Pipe pii =(Pipe)Main.map.getComponents().get(Main.map.getComponents().size()-2);
				Main.notifiableList.add(pii);
				Main.gmap.addItem(new GraphicsPipe(pii));
				Main.passiveComponents.put(pii.getId(),pii);
				Pump pu =(Pump)Main.map.getComponents().get(Main.map.getComponents().size()-1);
				Main.notifiableList.add(pu);
				Main.gmap.addItem(new GraphicsPump(pu));
				Main.activeComponents.put(pu.getId(),pu);
				break; //place
			case 'l':
				if(!Main.saboteurs.contains((activePlayer)))
				{
					errorMsg.showMessageDialog(null, "Ezt a muveletet csak szabotorok hajthatjak vegre!", "HIBA!", JOptionPane.ERROR_MESSAGE);
					break;
				}
				idxP = 0;
				for(int i = 0; i < Main.saboteurs.size(); i++)
				{
					if(Main.saboteurs.get(i).getId().equals(activePlayer.getId()))
					{
						idxP = i;
						i = Main.saboteurs.size();
					}
				}
				Saboteur activeSaboteurP = Main.saboteurs.get(idxP);
				if(activePlayer.getAP() < 1)
				{
					errorMsg.showMessageDialog(null, activePlayer.getId() + "-nek nincs eleg AP-je a muvelet elvegzesehez!", "HIBA!", JOptionPane.ERROR_MESSAGE);
					break;
				}
				try{
					Pipe p = (Pipe) activePlayer.position;
				}
				catch(Exception ex) {
					errorMsg.showMessageDialog(null, "Ez a tipusu komponens nem teheto csuszossa!", "HIBA!", JOptionPane.ERROR_MESSAGE);
					break;
				}
				if(!activeSaboteurP.MakePipeSlippery()){
					errorMsg.showMessageDialog(null, "A cso nem teheto csuszossa!", "HIBA!", JOptionPane.ERROR_MESSAGE);
					break;
				}
				break; //slippery
			case 't':
				if(activePlayer.getAP() < 1)
				{
					errorMsg.showMessageDialog(null, activePlayer.getId() + "-nek nincs eleg AP-je a muvelet elvegzesehez!", "HIBA!", JOptionPane.ERROR_MESSAGE);
					break;
				}
				else if(!activePlayer.MakePipeSticky())
				{
					errorMsg.showMessageDialog(null, "A komponenst nem lehet ragadossa tenni (vagy mar ragad)!", "HIBA!", JOptionPane.ERROR_MESSAGE);
					break;
				}
				break; //sticky
			case 'e':
				if(activePlayer.getAP() != activePlayer.maxAP)
				{
					errorMsg.showMessageDialog(null, activePlayer.getId() + "-nek nincs eleg AP-je a muvelet elvegzesehez!", "HIBA!", JOptionPane.ERROR_MESSAGE);
					break;
				}
				ArrayList<String> options = new ArrayList<String>();
				for (SComponent g: Main.map.getComponents()) {
					String str= g.getId();
					options.add(str);
				}
				String activeElem="";
				try
				{
					activeElem = (String)selector.showInputDialog(null, "Adja meg melyik komponensre szeretne elszokni!", "CÍM", JOptionPane.QUESTION_MESSAGE, null, options.toArray(), options.get(0));
				}
				catch(Exception ex) {}
				SComponent newPostion = null;
				for (SComponent g: Main.map.getComponents()) {
					if(g.getId().equals(activeElem))
						newPostion=g;
				}
				if(newPostion!=null)
				{
					if(!activePlayer.Escape(newPostion))
					{
						errorMsg.showMessageDialog(null, activePlayer.getId() + " nem menekulhet at a(z) " +newPostion.getId() + " komponensre!", "HIBA!", JOptionPane.ERROR_MESSAGE);
					}
				}
				break; //escape
			case 'q':
				activePlayer.Pass();
				break; //pass
			case 'v':
				if(activePlayer.getAP() < 1)
				{
					errorMsg.showMessageDialog(null, activePlayer.getId() + "-nek nincs eleg AP-je a muvelet elvegzesehez!", "HIBA!", JOptionPane.ERROR_MESSAGE);
					break;
				}
				String elsoVegStr="";
				String masodikVegStr="";
				ArrayList<String> felsoAlsoOtions = new ArrayList<String>();
				felsoAlsoOtions.add("felso");
				felsoAlsoOtions.add("jobb");
				felsoAlsoOtions.add("also");
				felsoAlsoOtions.add("bal");
				try
				{
					elsoVegStr= (String)selector.showInputDialog(null, "Adja meg a pumpa bemenetet!", "CÍM", JOptionPane.QUESTION_MESSAGE, null, felsoAlsoOtions.toArray(), felsoAlsoOtions.get(0));
				}
				catch(Exception ex) {}
				try
				{
					masodikVegStr= (String)selector.showInputDialog(null, "Adja meg a pumpa kimenetet!", "CÍM", JOptionPane.QUESTION_MESSAGE, null, felsoAlsoOtions.toArray(), felsoAlsoOtions.get(0));
				}
				catch(Exception ex) {}

				if(elsoVegStr.equals("") || masodikVegStr.equals(""))
				{
					errorMsg.showMessageDialog(null, "Nem megfeleloek a pumpa bemenete es kimenete!", "HIBA!", JOptionPane.ERROR_MESSAGE);
					break;
				}
				int elsoVeg=-1;
				int masodikVeg=-1;

				switch (elsoVegStr)
				{
					case "felso":elsoVeg=0; break;
					case "jobb":elsoVeg=1; break;
					case "also":elsoVeg=2; break;
					case "bal":elsoVeg=3; break;
				}
				switch (masodikVegStr)
				{
					case "felso":masodikVeg=0; break;
					case "jobb":masodikVeg=1; break;
					case "also":masodikVeg=2; break;
					case "bal":masodikVeg=3; break;
				}
				if(!activePlayer.SetPump(elsoVeg,masodikVeg))
				{
					errorMsg.showMessageDialog(null, "A komponens nem pumpa!", "HIBA!", JOptionPane.ERROR_MESSAGE);
					break;
				}
				break; //set pump
			case 'r':
				if(!Main.mechanics.contains((activePlayer)))
				{
					errorMsg.showMessageDialog(null, "Ezt a muveletet csak szerelok hajthatjak vegre!", "HIBA!", JOptionPane.ERROR_MESSAGE);
					break;
				}
				idxP = 0;
				for(int i = 0; i < Main.mechanics.size(); i++)
				{
					if(Main.mechanics.get(i).getId().equals(activePlayer.getId()))
					{
						idxP = i;
						i = Main.mechanics.size();
					}
				}
				Mechanic activeMechanicP3 = Main.mechanics.get(idxP);
				if(activePlayer.getAP() < 1)
				{
					errorMsg.showMessageDialog(null, activePlayer.getId() + "-nek nincs eleg AP-je a muvelet elvegzesehez!", "HIBA!", JOptionPane.ERROR_MESSAGE);
					break;
				}
				elsoVegStr="";
				masodikVegStr="";
				felsoAlsoOtions = new ArrayList<String>();
				felsoAlsoOtions.add("felso");
				felsoAlsoOtions.add("jobb");
				felsoAlsoOtions.add("also");
				felsoAlsoOtions.add("bal");
				try
				{
					elsoVegStr= (String)selector.showInputDialog(null, "Adja meg a melyik csovet szeretne athelyezni!", "CÍM", JOptionPane.QUESTION_MESSAGE, null, felsoAlsoOtions.toArray(), felsoAlsoOtions.get(0));
				}
				catch(Exception ex) {}
				options = new ArrayList<String>();
				for (SComponent s: Main.map.getComponents()) {
					try{
						options.add(s.getId());
					}
					catch(Exception ex) {}
				}
				activeElem="";
				try
				{
					activeElem = (String)selector.showInputDialog(null, "Adja meg a melyik aktiv elemhez akarja csatlakoztatni a csovet!", "CÍM", JOptionPane.QUESTION_MESSAGE, null, options.toArray(), options.get(0));
				}
				catch(Exception ex) {}
				try
				{
					masodikVegStr= (String)selector.showInputDialog(null, "Adja meg a melyik vegponthoz szeretne athelyezni a csovet!", "CÍM", JOptionPane.QUESTION_MESSAGE, null, felsoAlsoOtions.toArray(), felsoAlsoOtions.get(0));
				}
				catch(Exception ex) {}

				if(elsoVegStr.equals("") || masodikVegStr.equals(""))
				{
					errorMsg.showMessageDialog(null, "Nem megfeleloek a pumpa bemenete es kimenete!", "HIBA!", JOptionPane.ERROR_MESSAGE);
					break;
				}
				elsoVeg=-1;
				masodikVeg=-1;

				switch (elsoVegStr)
				{
					case "felso":elsoVeg=0; break;
					case "jobb":elsoVeg=1; break;
					case "also":elsoVeg=2; break;
					case "bal":elsoVeg=3; break;
				}
				switch (masodikVegStr)
				{
					case "felso":masodikVeg=0; break;
					case "jobb":masodikVeg=1; break;
					case "also":masodikVeg=2; break;
					case "bal":masodikVeg=3; break;
				}

				Active activeComponent = Main.activeComponents.get(activeElem);
				if(!activeMechanicP3.RelocatePipe(elsoVeg,activeComponent,masodikVeg)){
					errorMsg.showMessageDialog(null, "Nem lehet athelyezni a csovet", "HIBA!", JOptionPane.ERROR_MESSAGE);
					break;
				}
				break; //relocate pipe
			case 'h':
				if(!Main.mechanics.contains((activePlayer)))
				{
					errorMsg.showMessageDialog(null, "Ezt a muveletet csak szerelok hajthatjak vegre!", "HIBA!", JOptionPane.ERROR_MESSAGE);
					break;
				}
				idxP = 0;
				for(int i = 0; i < Main.mechanics.size(); i++)
				{
					if(Main.mechanics.get(i).getId().equals(activePlayer.getId()))
					{
						idxP = i;
						i = Main.mechanics.size();
					}
				}
				Mechanic activeMechanicP4 = Main.mechanics.get(idxP);
				if(activePlayer.getAP() < 1)
				{
					errorMsg.showMessageDialog(null, activePlayer.getId() + "-nek nincs eleg AP-je a muvelet elvegzesehez!", "HIBA!", JOptionPane.ERROR_MESSAGE);
					break;
				}
				String VegStr="";
				felsoAlsoOtions = new ArrayList<String>();
				felsoAlsoOtions.add("felso");
				felsoAlsoOtions.add("jobb");
				felsoAlsoOtions.add("also");
				felsoAlsoOtions.add("bal");
				try
				{
					VegStr= (String)selector.showInputDialog(null, "Adja meg a melyik csovet szeretne lecsatlakoztatni!", "CÍM", JOptionPane.QUESTION_MESSAGE, null, felsoAlsoOtions.toArray(), felsoAlsoOtions.get(0));
				}
				catch(Exception ex) {}
				if(VegStr.equals(""))
				{
					errorMsg.showMessageDialog(null, "Ervenytelen vegpont!", "HIBA!", JOptionPane.ERROR_MESSAGE);
					break;
				}
				int activeVegPont2=-1;
				switch (VegStr)
				{
					case "felso":activeVegPont2=0; break;
					case "jobb":activeVegPont2=1; break;
					case "also":activeVegPont2=2; break;
					case "bal":activeVegPont2=3; break;
				}
				if(!activeMechanicP4.Detach(activeVegPont2)){
					errorMsg.showMessageDialog(null, "A komponens nem aktiv!", "HIBA!", JOptionPane.ERROR_MESSAGE);
				}
				break; //detach pipe
		}
		Main.gameLoop();
		gMap.updateAll();
	}

	public void SetActivePlayer(Player p)
	{
		activePlayer = p;
	}
	
}
