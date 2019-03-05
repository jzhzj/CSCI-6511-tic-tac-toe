package edu.gwu.seas.ai.team6.game.board;

public class DefaultCoordinate extends AbstractCoordinate {

    public DefaultCoordinate(int x, int y) {
        super(x, y);
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }
}
