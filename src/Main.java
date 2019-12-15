import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private JPanel game;
//	private JLabel title;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Main();
	}
	
	public Main(){
		setLayout(new BorderLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(400,400);
		setLocationRelativeTo(null);
		setTitle("RMineSweeper");
		game = new Game();
		add(game, BorderLayout.CENTER);
		setVisible(true);
	}

}
