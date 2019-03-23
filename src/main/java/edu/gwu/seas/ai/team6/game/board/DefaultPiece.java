package edu.gwu.seas.ai.team6.game.board;

import edu.gwu.seas.ai.team6.game.board.interfaces.Coordinate;
import edu.gwu.seas.ai.team6.game.board.interfaces.Piece;

/**
 * Default implementation of {@link Piece}
 *
 * @author qijiuzhi
 * @date 2019-03-04
 */
public class DefaultPiece extends AbstractPiece {
    public DefaultPiece(Coordinate coordinate, Piece.PieceType type) {
        super(coordinate, type);
    }

    public DefaultPiece(int x, int y, Piece.PieceType type) {
        super(x, y, type);
    }

    /**
     * @see Piece#getCoordinate()
     */
    @Override
    public Coordinate getCoordinate() {
        return this.coordinate;
    }

    /**
     * @see Piece#getType()
     */
    @Override
    public PieceType getType() {
        return type;
    }
}
