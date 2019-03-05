package edu.gwu.seas.ai.team6.game.board;

import edu.gwu.seas.ai.team6.game.board.interfaces.Coordinate;
import edu.gwu.seas.ai.team6.game.board.interfaces.Piece;

public abstract class AbstractPiece implements Piece {
    Coordinate coordinate;
    PieceType type;

    public enum PieceType {
        /**
         * stands for Crosses
         */
        X,

        /**
         * stands for Noughts
         */
        O
    }

    AbstractPiece(Coordinate coordinate, PieceType type) {
        this.coordinate = coordinate;
        this.type = type;
    }

    AbstractPiece(int x, int y, PieceType type) {
        this(new DefaultCoordinate(x, y), type);
    }
}
