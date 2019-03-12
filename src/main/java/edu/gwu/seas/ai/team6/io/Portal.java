package edu.gwu.seas.ai.team6.io;

import edu.gwu.seas.ai.team6.game.board.interfaces.Coordinate;
import edu.gwu.seas.ai.team6.io.util.Move;

import java.util.List;

/**
 * A {@link Portal} object is used to communicate with a game host.
 *
 * @author qijiuzhi
 */
public interface Portal {

    /**
     * Creates a new game
     *
     * @param opponentTeamId the team id of the the opponent
     * @return the game id, or null if failed to create the game
     */
    String createGame(String opponentTeamId);

    /**
     * Makes a move
     *
     * @param coordinate the coordinate of the move
     * @param gameId     game id
     * @return move id, or null if failed
     */
    String moveAt(Coordinate coordinate, String gameId);

    /**
     * Makes a move
     *
     * @param x      x-coordinate
     * @param y      y-coordinate
     * @param gameId game id
     * @return move id, or null if failed
     */
    String moveAt(int x, int y, String gameId);

    /**
     * Gets moves. Gets a list containing a certain amount of most recent of moves,
     * which contains both our and opponent's moves.
     *
     * @param gameId game id
     * @param count  count of most recent of moves
     * @return list of moves
     */
    List<Move> getMoves(String gameId, int count);

    /**
     * Gets the last move on the board. The move could be either ours or the opponent's.
     *
     * @param gameId game id
     * @return a string representing the board
     */
    Move getLastMove(String gameId);

    /**
     * Gets a String representing the board.
     * e.g.
     * "OO---------O\nOO-------X--\n--O---------\n---O-X---X--\n----O--X----\n-----O-X-X--\n-------X-X--\n------------\n------------\n---------X--\n------------\n------------\n"
     *
     * @param gameId game id
     * @return String representing the board
     */
    String getBoardString(String gameId);

    /**
     * Gets a json String representing the board.
     * e.g.
     * "{\"11,15\":\"O\",\"11,16\":\"X\",\"11,17\":\"O\"}"
     *
     * @param gameId game id
     * @return a string representing the board
     */
    String getBoardMap(String gameId);
}
