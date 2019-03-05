package edu.gwu.seas.ai.team6.game.board.interfaces;

public interface Board {
    /**
     * mark a space with a piece
     *
     * @param x       the x coordinate of the space
     * @param y       the y-coordinate of the space
     * @param ourMove indicating if this is our move
     *                true: our move
     *                false: opponent's move
     */
    void moveAt(int x, int y, boolean ourMove);

    /**
     * mark a space with a piece
     *
     * @param coordinate the coordinate of the move
     * @param ourMove    indicating if this is our move
     *                   true: our move
     *                   false: opponent's move
     */
    void moveAt(Coordinate coordinate, boolean ourMove);

    /**
     * get last move in the board
     *
     * @return the last piece placed on the board
     */
    Piece getLastPiece();
}
