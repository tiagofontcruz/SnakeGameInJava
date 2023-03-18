package snake;

import javax.swing.JFrame;

public final class Main extends JFrame {

	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		new Main();
	}
	
	Main(){
		add(new Game());
		setTitle("Snake");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		pack();
		setVisible(true);
		setLocationRelativeTo(null);
	}
}
