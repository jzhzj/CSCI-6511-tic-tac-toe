package edu.gwu.seas.ai.team6.game.board;

import edu.gwu.seas.ai.team6.game.board.interfaces.Coordinate;

/**
 * Default implementation of {@link Coordinate}
 *
 * @author qijiuzhi
 * @date 2019-03-04
 */
public class DefaultCoordinate extends AbstractCoordinate {

    public DefaultCoordinate(int x, int y) {
        super(x, y);
    }

    /**
     * @see Coordinate#getX()
     */
    @Override
    public int getX() {
        return x;
    }

    /**
     * @see Coordinate#getY()
     */
    @Override
    public int getY() {
        return y;
    }
}
