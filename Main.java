import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import javax.swing.*;

public class Main {

    static public Map map = new Map();
    static public GraphicsMap gmap = new GraphicsMap();
    static public Scoreboard sb = new Scoreboard();
    static public GameKeyListener gameKeyListener;
    static ArrayList<Notifiable> notifiableList = new ArrayList<Notifiable>();
    static MyFrame frame = new MyFrame();
    static HashMap<String,Passive> passiveComponents = new HashMap<String,Passive>();
    static HashMap<String,Active> activeComponents = new HashMap<String,Active>();
    static ArrayList<Mechanic> mechanics = new ArrayList<Mechanic>();
    static ArrayList<Saboteur> saboteurs = new ArrayList<Saboteur>();
    static int roundNums = 30;
    static int activePlayerIndex = 0;
    static Player activePlayer;
    public static void main(String[] args) {
        gameKeyListener= new GameKeyListener(gmap);
        frame.setSize(1280,720);
        frame.setLayout(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        setupMainMenu();
    }

    private static void setupMainMenu()
    {
        JLabel picLabel;
        Image IMG;
        ImageIcon icon ;
        String filePath = "RESOURCES/IMAGES/Texture2D/";
        icon = new ImageIcon(filePath+"BG_desert.png");
        IMG = icon.getImage();
        picLabel = new JLabel(new ImageIcon(IMG));
        picLabel.setOpaque(false);
        picLabel.setBounds(0, 0, 1280, 720);
        JButton newGameButton=new JButton("Start");
        newGameButton.setBounds(1280/2-100,200,200, 70);
        newGameButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    setupGame();
                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
                gameLoop();
            }
        } );

        frame.getContentPane().add(newGameButton);
        JButton exitGameButton=new JButton("Exit");
        exitGameButton.setBounds(1280/2-100,400,200, 70);
        exitGameButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        } );
        frame.getContentPane().add(exitGameButton);
        frame.getContentPane().add(picLabel);
        frame.setLayout(null);
        frame.setVisible( true );
        frame.repaint();
    }

    private static void setupGame() throws FileNotFoundException {
        File loadFile = new File("map.txt");
        Scanner fr = new Scanner(loadFile);
        String line;
        while(fr.hasNextLine())
        {
            line = fr.nextLine();
            if(!line.equals("") && line.charAt(0) != '>')
            {
                handleInput(line);
            }
        }
        fr.close();
        frame.setPumps(passiveComponents);
        activePlayer= mechanics.get(0);
        gameKeyListener.setActivePlayer(activePlayer);
        frame.addKeyListener(gameKeyListener);
        frame.requestFocus();
        drawScene();
    }

    private static void endScreen()
    {
        passiveComponents.clear();
        frame.removeKeyListener(gameKeyListener);
        frame.getContentPane().removeAll();
        frame.getContentPane().setBackground(Color.BLACK);
        JLabel winTeam;
        if(sb.getMechPoints()>sb.getSabPoints())
        {
            winTeam = new JLabel("Mechanics won");
            winTeam.setBounds(1280/2-150,100,500,200);
        }
        else if (sb.getMechPoints()<sb.getSabPoints())
        {
            winTeam = new JLabel("Saboteurs won");
            winTeam.setBounds(1280/2-150,100,500,200);
        }
        else
        {
            winTeam = new JLabel("Draw");
            winTeam.setBounds(1280/2-60,100,500,200);
        }
        winTeam.setForeground(Color.WHITE);
        winTeam.setFont(new Font("Comic Sans MS",Font.BOLD,40));

        JLabel mechScore = new JLabel("Mechanics: "+sb.getMechPoints());
        mechScore.setForeground(Color.WHITE);
        mechScore.setFont(new Font("Comic Sans MS",Font.BOLD,40));
        mechScore.setBounds(300,300,500,200);
        JLabel sabScore = new JLabel("Saboteurs: "+sb.getSabPoints());
        sabScore.setForeground(Color.WHITE);
        sabScore.setFont(new Font("Comic Sans MS",Font.BOLD,40));
        sabScore.setBounds(700,300,500,200);
        frame.getContentPane().add(winTeam);
        frame.getContentPane().add(mechScore);
        frame.getContentPane().add(sabScore);
        JButton exitGameButton=new JButton("Exit");
        exitGameButton.setBounds(1280/2-100,600,200, 70);
        exitGameButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        } );
        frame.add(exitGameButton);
        frame.setLayout(null);
        frame.setVisible( true );
        frame.repaint();

        
    }
    public static void gameLoop()
    {
            if(roundNums>0)
            {
                if (activePlayer.getAP() <= 0) {
                    activePlayerIndex += 1;
                if (activePlayerIndex == 4) {
                    roundNums--;
                    activePlayerIndex %= 4;
                }
                if (activePlayerIndex==0||activePlayerIndex==2) {
                    activePlayer = mechanics.get(activePlayerIndex/2);
                    MechanicKeyBinds();
                } else {
                    activePlayer = saboteurs.get((activePlayerIndex/2));
                    SaboteurKeyBinds();
                }
                activePlayer.resetAP();
                gameKeyListener.setActivePlayer(activePlayer);
                for(SComponent c : map.getComponents())
                {
                    c.MoveWater(null);	
                }
                map.resetMoveWater();
                sb.TallyPoints(map);
                int mapSize=map.getComponents().size();
                for (Notifiable n:notifiableList) 
                {
                    n.Notify();
                }
                if(mapSize<map.getComponents().size()){
                    for (int i=mapSize;i<map.getComponents().size();i++){
                        Pipe pii =(Pipe)Main.map.getComponents().get(i);
                        Main.notifiableList.add(pii);
                        Main.gmap.addItem(new GraphicsPipe(pii));
                        Main.passiveComponents.put(pii.getId(),pii);
                    }
                }
                drawScene();
                new JOptionPane().showMessageDialog(null, "Uj aktiv jatekos: " + activePlayer.getId(), "JATEKOS VALTAS!", JOptionPane.WARNING_MESSAGE);
                }
            drawScene();
            if(roundNums <= 0)
            {
                endScreen();
            }
        }
    }

    private static void handleInput(String line)
    {
        String command = line.split(":")[0];
        switch (command) {
            case "CreateComponent":
                CreateComponent(line);
                break;
            case "CreatePlayer":
                CreatePlayer(line);
                break;
            case "SetPump":
                SetPump(line);
                break;
            case "ConnectComponents":
                ConnectComponents(line);
                break;
        }
    }

    private static void CreateComponent(String cmd){
        String[] args = cmd.split(":")[1].split(",");
        int xpos;
        int ypos;
        xpos = Integer.parseInt(args[2]);
        ypos = Integer.parseInt(args[3]);
        switch (args[1].toUpperCase()) {
            case "PUMP":
                Pump pu = new Pump(args[0],xpos,ypos);
                GraphicsPump gpu = new GraphicsPump(pu);
                gmap.addItem(gpu);
                activeComponents.put(args[0],pu);
                Main.notifiableList.add(pu);
                Main.map.addComponents(pu);
                pu.setMap(Main.map);
                break;
            case "PIPE":
                Pipe pi = new Pipe(args[0],xpos,ypos);
                GraphicsPipe gpi = new GraphicsPipe(pi);
                passiveComponents.put(args[0],pi);
                gmap.addItem(gpi);
                Main.notifiableList.add(pi);
                Main.map.addComponents(pi);
                pi.setMap(Main.map);
                break;
            case "CISTERN":
                Cistern c = new Cistern(args[0],xpos,ypos);
                GraphicsCistern gc = new GraphicsCistern(c);
                activeComponents.put(args[0],c);
                gmap.addItem(gc);
                Main.notifiableList.add(c);
                Main.map.addComponents(c);
                c.setMap(Main.map);
                break;
            case "SPRING":
                Spring s = new Spring(args[0],xpos,ypos);
                GraphicsSpring gs = new GraphicsSpring(s);
                activeComponents.put(args[0],s);
                gmap.addItem(gs);
                Main.map.addComponents(s);
                s.setMap(Main.map);
                break;
        }
    }
    private static void CreatePlayer(String cmd)
    {
        String[] args = cmd.split(":")[1].split(",");
        SComponent pos;
        if(activeComponents.containsKey(args[2]))
            pos = activeComponents.get(args[2]);
        else
            pos = passiveComponents.get(args[2]);
        if(args[1].equals("Mechanic"))
        {
            Mechanic m = new Mechanic(pos, 10, args[0]);
            String fileName = "player_eng_";
            if(m.getId().contains("1"))
            {
            	fileName += 0;
            }
            else if(m.getId().contains("2"))
            {
            	fileName += 3;
            }
            GraphicsPlayer gm = new GraphicsPlayer(fileName,m);
            gmap.addItem(gm);
            mechanics.add(m);
        }
        else {
            Saboteur s = new Saboteur(pos, 10, args[0]);
            String fileName = "player_sab_";
            if(s.getId().contains("1"))
            {
            	fileName += 0;
            }
            else if(s.getId().contains("2"))
            {
            	fileName += 3;
            }
            GraphicsPlayer gm = new GraphicsPlayer(fileName,s);
            gmap.addItem(gm);
            saboteurs.add(s);
        }

    }
    private static void ConnectComponents(String cmd){
        String[] args = cmd.split(":")[1].split(",");
        int endp1 = Integer.parseInt(args[1]);
        int endp2 = Integer.parseInt(args[3]);
        if(activeComponents.containsKey(args[0]))
        {
            activeComponents.get(args[0]).setNeighbours(endp1-1,passiveComponents.get(args[2]));
            passiveComponents.get(args[2]).setNeighbours(endp2-1,activeComponents.get(args[0]));
        }
        else
        {
            activeComponents.get(args[2]).setNeighbours(endp2-1,passiveComponents.get(args[0]));
            passiveComponents.get(args[0]).setNeighbours(endp1-1,activeComponents.get(args[2]));
        }

    }
    private static void SetPump(String cmd)
    {
        String[] args = cmd.split(":")[1].split(",");
        Active c = activeComponents.get(args[0]);
        int newIn = Integer.parseInt(args[1]);
        int newOut = Integer.parseInt(args[2]);
        c.SetPump(newIn-1, newOut-1);
    }

    private static void drawScene()
    {
        frame.getContentPane().removeAll();
        if (mechanics.contains(activePlayer))
            MechanicKeyBinds();
        else
            SaboteurKeyBinds();
            for (int i =gmap.getComponents().size()-1; i>-1;i--){
                frame.add(gmap.getComponents().get(i).getLabel());
                frame.add(gmap.getComponents().get(i).getIdLabel());
            }


        JLabel scoreLabel = new JLabel("<html><font color='white'>SAB POINTS: "+sb.getSabPoints()+"<br>MEC POINTS: "+sb.getMechPoints()+"<br><br>ROUND: "+(30-roundNums)+"/30<br><br>AP: "+activePlayer.getAP()+"<br>"+activePlayer.getId()+"</font></html>");
        scoreLabel.setFont(new Font("Arial",1,20));
        scoreLabel.setBounds(frame.getWidth()-215,0,198, 220);
        scoreLabel.setOpaque(true);
        scoreLabel.setBackground(Color.GRAY);
        frame.add(scoreLabel);
        SwingUtilities.updateComponentTreeUI(frame);

        JLabel picLabel;
        Image IMG;
        ImageIcon icon ;
        String filePath = "RESOURCES/IMAGES/Texture2D/";
        icon = new ImageIcon(filePath+"BG_desert.png");
        IMG = icon.getImage();
        picLabel = new JLabel(new ImageIcon(IMG));
        picLabel.setOpaque(false);
        picLabel.setBounds(0, 0, 1280, 720);
        frame.getContentPane().add(picLabel);
        frame.setLayout(null);
        frame.setVisible( true );
        frame.repaint();
    }

    private static void MechanicKeyBinds(){
        JLabel label2 = new JLabel("<html><font color='white'>UP:..........................w<br>" +
                "RIGHT:....................d<br>" +
                "DOWN:....................s<br>" +
                "LEFT:......................a<br>" +
                "ESCAPE:................e<br>" +
                "PASS:.....................q<br>" +
                "SET PUMP:.............v<br>"+
                "PUNCTURE:............p<br>"+
                "FIX:..........................f<br>"+
                "STICKY:..................t<br>"+
                "COLLECT PUMP:...c<br>"+
                "PLACE PUMP:........g<br>"+
                "RELOCATE PIPE:...r<br>"+
                "DETACH PIPE:.......h</font></html>");
        label2.setFont(new Font("Arial",1,18));
        label2.setBounds(frame.getWidth()-215,210,198, frame.getHeight()-210);
        label2.setOpaque(true);
        label2.setBackground(Color.BLACK);
        frame.add(label2);
    }

    private static void SaboteurKeyBinds(){
        JLabel label = new JLabel("<html><font color='white'>UP:..........................w<br>" +
                "RIGHT:....................d<br>" +
                "DOWN:....................s<br>" +
                "LEFT:.......................a<br>" +
                "ESCAPE:.................e<br>" +
                "PASS:......................q<br>" +
                "SET PUMP:..............v<br>"+
                "PUNCTURE:............p<br>"+
                "SLIPPERY:...............l<br>"+
                "STICKY:...................t</font></html>");
        label.setFont(new Font("Arial",1,18));
        label.setBounds(frame.getWidth()-215,210,198, frame.getHeight()-210);
        label.setOpaque(true);
        label.setBackground(Color.BLACK);
        frame.add(label);
    }
}
