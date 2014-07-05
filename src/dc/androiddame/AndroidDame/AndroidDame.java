package dc.androiddame.AndroidDame;

import ch.aplu.android.*;
import android.app.AlertDialog;
import java.util.ArrayList;

import game.*;
import dame.*;

public class AndroidDame extends GameGrid implements GGTouchListener, EventListener {
    Dame dame;
    DameActor currentActor;
    Token currentToken;
    ArrayList<Field> movableFields;
    AlertDialog dialog;

    public AndroidDame() {
        super(8, 8, cellZoom(50), 0, "schachbrett", true);
        dame = new Dame();
        EventManager.get().addEventListener(EventType.TokenCreate, this);
        EventManager.get().addEventListener(EventType.TokenMove, this);
        EventManager.get().addEventListener(EventType.TokenDestroy, this);
        EventManager.get().addEventListener(EventType.NextPlayer, this);
    }

    @Override
    public void main() {
        dame.start();
        dame.end();

        addTouchListener(this, GGTouch.press | GGTouch.drag | GGTouch.release);

        doRun();
    }

    @Override
    public boolean touchEvent(GGTouch touch) {
        Location location = toLocationInGrid(touch.getX(), touch.getY());
        switch (touch.getEvent()) {
            case GGTouch.press:
                currentActor = (DameActor) getOneActorAt(location);
                if (currentActor != null) {
                    if (currentActor.getToken().getPlayer() == dame
                            .getCurrentPlayer()) {
                        movableFields = currentActor.getToken().pick();
                    } else {
                        currentActor = null;
                    }
                }
                break;
            case GGTouch.release:
                if (movableFields == null) {
                    break;
                }
                for (Field field : movableFields) {
                    int x = field.getPosition().x;
                    int y = 7 - field.getPosition().y;
                    if (x == location.x && y == location.y) {
                        currentActor.getToken().move(field);
                    }
                }
                movableFields = null;
                break;
        }

        return false;
    }

    @Override
    public void handleEvent(EventType eventType, Object source) {
        DameActor actor;
        Token token = (Token) source;
        Position position = token.getField().getPosition();

        switch (eventType) {
            case TokenCreate:
                actor = new DameActor(token);
                addActor(actor, new Location(position.x, 7 - position.y));
                break;

            case TokenMove:
                actor = getActor(token);
                actor.setLocation(new Location(position.x, 7 - position.y));
                break;

            case TokenDestroy:
                actor = getActor((Token) source);
                removeActor(actor);
                break;

            case NextPlayer:
                new AlertDialog.Builder(this)
                        .setTitle("Next Player")
                        .setMessage(
                                "Spieler " + dame.getCurrentPlayer().getColorName()
                                        + " ist am Zug").show();
                break;

            default:
                break;
        }
    }

    private DameActor getActor(Token token) {
        for (Actor actor : getActors()) {
            DameActor dActor = (DameActor) actor;
            if (dActor.getToken() == token) {
                return dActor;
            }
        }
        return null;
    }

}
