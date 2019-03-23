package edu.gwu.seas.ai.team6.io.util;

import edu.gwu.seas.ai.team6.game.board.DefaultCoordinate;
import edu.gwu.seas.ai.team6.game.board.interfaces.Coordinate;
import edu.gwu.seas.ai.team6.game.board.interfaces.Piece;

/**
 * An object representing moves. This class is mainly used by Gson,
 * so there is no public access to the constructor of {@link Move} class.
 *
 * @author qijiuzhi
 * @date 2019-03-05
 */
public class Move {
    private String moveId;
    private String gameId;
    private String teamId;
    private String move;
    private String symbol;
    private String moveX;
    private String moveY;

    private Move() {

    }

    public int getMoveId() {
        return Integer.parseInt(moveId);
    }

    public int getGameId() {
        return Integer.parseInt(gameId);
    }

    public int getTeamId() {
        return Integer.parseInt(teamId);
    }

    public Coordinate getCoordinate() {
        int x = Integer.parseInt(moveX);
        int y = Integer.parseInt(moveY);
        return new DefaultCoordinate(x, y);
    }

    public int getX() {
        return Integer.parseInt(moveX);
    }

    public int getY() {
        return Integer.parseInt(moveY);
    }

    public Piece.PieceType getPieceType() {
        if (symbol == null) {
            return null;
        }
        return Piece.PieceType.O.toString().equals(symbol) ? Piece.PieceType.O : Piece.PieceType.X;
    }
}
