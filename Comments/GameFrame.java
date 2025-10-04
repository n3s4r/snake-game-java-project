import javax.swing.JFrame; //import

public class GameFrame extends JFrame{ //extends the jframe class
	
	GameFrame(){ //constructor
		
		this.add(new GamePanel()); //GamePanel panel = new GamePanel(); #shortcut
		this.setTitle("Snake"); //adding a title
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 
		this.setResizable(false);
		this.pack(); //adding components to jframe
		this.setVisible(true);
		this.setLocationRelativeTo(null); //screen in the middle
		
	}

}
