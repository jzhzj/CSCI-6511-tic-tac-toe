package edu.gwu.seas.ai.team6.game.board;

import edu.gwu.seas.ai.team6.game.board.interfaces.Coordinate;
import edu.gwu.seas.ai.team6.game.board.interfaces.Piece;

public class DefaultPiece extends AbstractPiece {
    public DefaultPiece(Coordinate coordinate, Piece.PieceType type) {
        super(coordinate, type);
    }

    public DefaultPiece(int x, int y, Piece.PieceType type) {
        super(x, y, type);
    }

    @Override
    public Coordinate getCoordinate() {
        return this.coordinate;
    }

    @Override
    public PieceType getType() {
        return type;
    }
}
