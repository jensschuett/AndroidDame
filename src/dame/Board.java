package dame;

import game.Field;
import game.Playboard;
import game.Player;
import game.Position;

public class Board extends Playboard {

    public Board(Player white, Player black) {
	min = 0;
	max = 7;
	buildBoard();
	spawnTokens(white, black);
    }

    private void buildBoard() {
	fields = new Field[max+1][max+1];
	for (int x = min; x <= max; x++) {
	    for (int y = min; y <= max; y++) {
		Position pos = new Position(x, y);
		if ((x + y) % 2 == 1) {
		    // Wei�e Felder
		    fields[x][y] = new Field(pos, 0, this);
		} else {
		    // Schwarze Felder
		    fields[x][y] = new Field(pos, 1, this);
		}
	    }
	}
    }

    private void spawnTokens(Player white, Player black) {
	for (int x = min; x <= max; x++) {
	    for (int y = min; y <= max; y++) {
		if ((x + y) % 2 == 0) { // Schwarze Felder
		    if (y < 3) { // Wei�er Spieler
			fields[x][y].setToken(new Man(white, fields[x][y]));
		    }
		    if (y >= 5) { // Schwarzer Spieler
			fields[x][y].setToken(new Man(black, fields[x][y]));
		    }
		}
	    }
	}
    }
}
