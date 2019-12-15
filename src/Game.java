import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Game extends JPanel implements MouseListener {
	
	Random rand = new Random(); // obj untuk random
	public static final int IS_BOMB=-1;  // tanda kalau dia bomb itu -1
	private final int SIZE_X=9; //ukuran board secara X
	private final int SIZE_Y=9; // ukuran board secara Y
	private int counter = 81; // Jumlah seluruh tiles yang ada
	private final int BOMBS = 9; // jumlah bomb
	private MyButton buttons[][]; // array of button kita
	
	public Game(){
		startgame();
	}
	
	public void startgame(){ //kalau game over/ win, panggil fungsi ini biar nge restart.
		removeAll(); // hapus seluruh komponen pada JPanel
		setLayout(new GridLayout(9,9)); // lakuin set layout ulang
		initMap(); // kita init map ulang
		initBoard(); // kita init board ulang
		revalidate(); // melakukan validate apakah ada komponen baru / ada komponen yang dihapus. jika ada maka lakukan repaint.
		counter = 81; // kita set lagi jumlah counter jadi awal lagi yaitu 81
	}
	
	public void initMap(){
		//fill our map with 0
		buttons = new MyButton[9][]; // init array 2D of button
		for(int i=0;i<SIZE_X;i++) {
			buttons[i] = new MyButton[9];
			for(int j=0;j<SIZE_Y;j++)
				buttons[i][j] = new MyButton(i, j); // array nya kita isi dengan button kita.
		}
		
		//random our bombs
		for (int i = 0; i < BOMBS; i++) { // random sebanyak jumlah bom.
			int bomb_x, bomb_y;
			
			do {
				bomb_x = rand.nextInt(9);
				bomb_y = rand.nextInt(9);
			} while (isBomb(bomb_x, bomb_y)); // jika hasil random = bomb, maka random ulang.
			
			buttons[bomb_x][bomb_y].value = IS_BOMB; // tandain bahwa button ke x dan y itu bom.
		}
	}
	
	public boolean isBomb(int x , int y){ // fungsi untuk nge cek apakah button pada koordinat x dan y itu bom atau bukan
		if (0 <= x && x < SIZE_X && 0 <= y && y < SIZE_Y) // validate supaya tidak out of bound
			return buttons[x][y].isBomb(); // return value apakah dia bomb atau bukan.
		return false; // jika out of bound, maka bukan bomb.
	}
	
	// nge cek posisi dari kiri atas ke kiri tengah, dst 
	int dir_x[] = {-1, -1, -1, +0, +0, +1, +1, +1};
	int dir_y[] = {+1, +0, -1, +1, -1, +1, +0, -1};
	
	public void initBoard(){
		for(int i=0;i<SIZE_X;i++){
			for(int j=0;j<SIZE_Y;j++){
				MyButton button = buttons[i][j];
				if (!isBomb(i, j)) { //Jika tiles nya itu bukan bom
					// count surrounding bombs;
					button.value = 0;
					for(int k=0; k<dir_x.length; k++)
						if(isBomb(i+dir_x[k], j+dir_y[k]))
							button.value++;
				}
				button.addMouseListener(this);
				add(button);
			}
		}
	}

	void floodFill(int x, int y) {
		if (0 <= x && x < SIZE_X && 0 <= y && y < SIZE_Y) { // agar tidak out of bound
			if (!isBomb(x, y)) { // kalau bukan bomb
				MyButton button = buttons[x][y];
				if (button.isEnabled()) { // dan button belum kelihatan isinya (blm di klik)
					button.setEnabled(false); // button nya kita disable
					counter--; // jumlah tiles yang masih availablenya kita kurangin
					if (button.value == 0) {
						floodFill(x-1, y); // cek ke arah kiri
						floodFill(x+1, y); // cek ke arah kanan
						floodFill(x, y-1); // cek ke arah atas
						floodFill(x, y+1); // cek ke arah bawah
					}
					else {
						button.setText("" + button.value); // kasi tau berapa jumlah bom disekitar koordinat ini.
					}
				}
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() instanceof MyButton) {
			MyButton button = (MyButton) e.getSource();
			switch (e.getButton()) {
				case MouseEvent.BUTTON1: // Klik kiri
					if (!button.isFlagged) { // jika ga di kasi flag
						if(button.value == IS_BOMB) { // jika klik kiri dan dia bom
							JOptionPane.showMessageDialog(this, "You Lose !");
							startgame();
						}
						else { //jika klik kiri dan dia bukan bom
							floodFill(button.x, button.y);
							// cek win
							if(counter == BOMBS){
								JOptionPane.showMessageDialog(this, "You Win !");
								startgame();
							}
						}
					}
					break;
				case MouseEvent.BUTTON3: // Klik kanan
					if (button.isEnabled()) { // cek jika button ini masih bisa di klik
						button.isFlagged = !button.isFlagged; // ubah state nya menjadi reverse nya
						if (button.isFlagged) { // jika terflag, kasi icon
							 try {
								 Image img = ImageIO.read(getClass().getResource("flagg.png"));
								 button.setIcon(new ImageIcon(img));
							 } catch (Exception ex) {
								 System.out.println(ex);
							 }
						}
						else { // jika flag hilangin, remove icon
							button.setIcon(null);
						}
					}
					break;
			}
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
