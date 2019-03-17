package edu.gwu.seas.ai.team6.game;

import edu.gwu.seas.ai.team6.game.alg.Algorithm;
import edu.gwu.seas.ai.team6.game.alg.MiniMax;
import edu.gwu.seas.ai.team6.game.board.DefaultBoard;
import edu.gwu.seas.ai.team6.game.board.interfaces.Piece;
import edu.gwu.seas.ai.team6.io.Portal;

/**
 * Default implementation of {@link Game}.
 *
 * @author qijiuzhi
 * @date 2019-03-05
 */
public class DefaultGame extends AbstractGame {

    /**
     * make constructor private so that {@link DefaultGame} object can only be constructed by createGame method
     */
    private DefaultGame(String opponentId, String gameId, Portal portal, int n, int m, Piece.PieceType ourPieceType) {
        super(opponentId, gameId, portal, n, m, ourPieceType);
    }

    /**
     * Creates a new game if we are the initializer of the game.
     *
     * @param opponentId   the id of the opponent team
     * @param portal       the portal used for communication
     * @param n            the size of the board
     * @param m            the goal
     * @param ourPieceType the type of our piece
     * @return the new game or null if failed to create a game
     */
    public static DefaultGame createGame(String opponentId, Portal portal, int n, int m, Piece.PieceType ourPieceType) {
        String gameId = portal.createGame(opponentId);
        return createGame(opponentId, portal, n, m, ourPieceType, gameId);
    }

    /**
     * Creates a new game if other team has already post a game to the game server.
     *
     * @param opponentId   the id of the opponent team
     * @param portal       the portal used for communication
     * @param n            the size of the board
     * @param m            the goal
     * @param ourPieceType the type of our piece
     * @param gameId       the id of the game
     * @return the new game or null if failed to create a game
     */
    public static DefaultGame createGame(String opponentId, Portal portal, int n, int m, Piece.PieceType ourPieceType, String gameId) {
        if (gameId == null) {
            return null;
        }
        return new DefaultGame(opponentId, gameId, portal, n, m, ourPieceType);
    }

    @Override
    public void run() {
        portal.createGame(opponentId);
        portal.getBoardString(gameId);
        DefaultBoard board = new DefaultBoard(n,m,ourPieceType);
        Algorithm.miniMax(board,7);
        portal.moveAt(board.getLastPiece().getCoordinate(),gameId);
    }
}
