package edu.gwu.seas.ai.team6.game.board;

import edu.gwu.seas.ai.team6.game.board.interfaces.Coordinate;
import edu.gwu.seas.ai.team6.game.board.interfaces.Piece;

public class DefaultBoard extends AbstractBoard {

    public DefaultBoard(int n, Piece.PieceType ourPieceType) {
        super(n, ourPieceType);
    }

    @Override
    public void moveAt(int x, int y, boolean isOurMove) {
        moveAt(new DefaultCoordinate(x, y), isOurMove);
    }

    @Override
    public void moveAt(Coordinate coordinate, boolean isOurMove) {
        Piece.PieceType type = isOurMove ? ourPieceType : opponentsPieceType;
        int x = coordinate.getX();
        int y = coordinate.getY();
        DefaultPiece piece = new DefaultPiece(coordinate, type);
        this.board[x][y] = piece;
        lastPiece = piece;
    }

    @Override
    public Piece getLastPiece() {
        return lastPiece;
    }
}
