package edu.gwu.seas.ai.team6.game.board;

import edu.gwu.seas.ai.team6.game.board.interfaces.Coordinate;
import edu.gwu.seas.ai.team6.game.board.interfaces.Piece;

abstract class AbstractPiece implements Piece {
    Coordinate coordinate;
    PieceType type;

    AbstractPiece(Coordinate coordinate, PieceType type) {
        this.coordinate = coordinate;
        this.type = type;
    }

    AbstractPiece(int x, int y, PieceType type) {
        this(new DefaultCoordinate(x, y), type);
    }
}
