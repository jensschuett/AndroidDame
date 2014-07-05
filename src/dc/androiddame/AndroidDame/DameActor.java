package dc.androiddame.AndroidDame;

import game.Token;
import ch.aplu.android.Actor;

public class DameActor extends Actor  {

    private Token token;

    public DameActor(Token token) {
        super("token" + (token.getPlayer().getColor()+1));
        this.token = token;
    }

    public Token getToken(){
        return token;
    }
}
