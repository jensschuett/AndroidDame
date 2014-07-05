package dame;

import game.Player;

import java.util.ArrayList;

public class Dame extends game.Boardgame {

    public Dame() {
	players = new ArrayList<Player>();
    }

    @Override
    public void start() {
	players.add(currentPlayer = new Player(this, 0));
	players.add(new Player(this, 1));
	board = new Board(players.get(0), players.get(1));
    }

    // public static void main(String[] args) {
    // Dame dame = new Dame();
    // dame.start();
    // }

    @Override
    public void end() {
	// TODO Auto-generated method stub

    }

}
