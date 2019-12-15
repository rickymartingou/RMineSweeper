import javax.swing.JButton;

public class MyButton extends JButton {

	private static final long serialVersionUID = 1L;
	
	public int x;
	public int y;
	public int value;
	public boolean isFlagged = false;
	
	public MyButton() {
		super();
	}

	public MyButton(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}
	
	public boolean isBomb() {
		return value == Game.IS_BOMB;
	}
	
}
