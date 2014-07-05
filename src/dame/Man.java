package dame;

import game.Field;
import game.Player;
import game.Position;
import game.Token;

import java.util.ArrayList;

public class Man extends Token {

    public Man(Player player, Field field) {
	super(player, field);
    }

    @Override
    public void move(Field field) {
	if (!isNeighbor(field)) {
	    getTokenBetween(field).delete();
	}
	this.field.removeToken();
	field.setToken(this);
	super.move(field);
    }

    /**
     * Return Felder, auf die man ziehen kann/muss
     */
    @Override
    public ArrayList<Field> pick() {
	ArrayList<Field> movableFields = new ArrayList<Field>();

	Position direction = new Position(-1, getYDirection());
	Field movable = getMovableField(direction);
	if (movable != null) {
	    movableFields.add(movable);
	}

	direction.x = 1;
	movable = getMovableField(direction);
	if (movable != null) {
	    movableFields.add(movable);
	}

	return movableFields;
    }

    private Field getMovableField(Position direction) {
	Field fieldNeighbor;
	if (field.isDirectionValid(direction)) {
	    fieldNeighbor = getFieldNeighbor(direction);
	    if (fieldNeighbor.getToken() == null) {
		return fieldNeighbor;
	    } else {
		if (fieldNeighbor.getToken().getPlayer() != getPlayer()) {
		    if (fieldNeighbor.isDirectionValid(direction)) {
			fieldNeighbor = fieldNeighbor.getNeighbor(direction);
			if (fieldNeighbor.getToken() == null) {
			    return fieldNeighbor;
			}
		    }
		}
	    }
	}
	return null;
    }

    private boolean isNeighbor(Field field) {
	int difference = field.getPosition().x - getField().getPosition().x;
	if (difference == 1 || difference == -1) {
	    return true;
	}
	return false;
    }

    private int getYDirection() {
	if (player.getColor() == 0) {
	    return 1;
	} else {
	    return -1;
	}
    }

    private Token getTokenBetween(Field field) {
	Position direction = getField().getPosition().getDirection(
		field.getPosition());
	return getFieldNeighbor(direction).getToken();
    }
}
