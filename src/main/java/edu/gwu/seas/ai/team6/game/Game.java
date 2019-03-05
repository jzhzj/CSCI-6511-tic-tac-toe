package edu.gwu.seas.ai.team6.game;

import edu.gwu.seas.ai.team6.game.board.AbstractPiece;

public interface Game {
    /**
     * Creates a new game.
     *
     * @param otherTeamId  the id of the opponent team
     * @param n            the size of the board
     * @param m            the goal
     * @param ourPieceType the type of our piece
     * @return the new game
     */
    Game createGame(int otherTeamId, int n, int m, AbstractPiece.PieceType ourPieceType);
}
