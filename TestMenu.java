import javax.swing.*;
import java.awt.*;
import Control.*;
import View.*;
import Model.*;

public class TestMenu implements Runnable {
	public void run() {
		JFrame frame = new JFrame("Fanorona");
		frame.setResizable(false);
		LoadGame LoadGame_init = new LoadGame(frame, null, null);
		Jeu game = new Jeu();
		Coup stroke = new Coup();
		GameBoard gameboard_init = new GameBoard(game, stroke);

		rules rules_init = new rules(frame, null);
		settings settings_init = new settings(frame, null, game);
		Menu_board Menu_init = new Menu_board(frame, rules_init, gameboard_init, LoadGame_init, settings_init, game,
				stroke);
		settings_init.updateSettings(Menu_init);
		rules_init.updaterules(Menu_init);
		LoadGame_init.updateLoadGame(Menu_init,  gameboard_init );
		gameboard_init.updategameboard(frame, Menu_init);

		frame.add(Menu_init);
		frame.setSize(800, 720);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new TestMenu());
	}
}