package edu.gwu.seas.ai.team6.game.board;

import edu.gwu.seas.ai.team6.game.board.interfaces.Coordinate;

public class DefaultPiece extends AbstractPiece {
    public DefaultPiece(Coordinate coordinate, AbstractPiece.PieceType type) {
        super(coordinate, type);
    }

    public DefaultPiece(int x, int y, AbstractPiece.PieceType type) {
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
