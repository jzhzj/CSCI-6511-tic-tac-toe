package edu.gwu.seas.ai.team6.game.board.interfaces;

/**
 * An object representing the board of the game.
 *
 * @author qijiuzhi
 */
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
     * Gets the last move in the board.
     * This move is not synchronized with the remote game host,
     * it's only the last move that our local board stores.
     *
     * @return the last piece placed on the board or null if no piece is on the board
     */
    Piece getLastPiece();

    /**
     * Gets the piece at a certain coordination on the board
     *
     * @return the piece
     */
    Piece getPiece(int x, int y);
}
