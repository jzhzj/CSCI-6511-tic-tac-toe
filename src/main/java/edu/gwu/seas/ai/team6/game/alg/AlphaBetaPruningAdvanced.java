package edu.gwu.seas.ai.team6.game.alg;

import edu.gwu.seas.ai.team6.game.board.DefaultBoard;
import edu.gwu.seas.ai.team6.game.board.interfaces.Piece;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class AlphaBetaPruningAdvanced {
    private static double MaxDepth;

    /**
     * MiniMax cannot be instantiated.
     */
    private AlphaBetaPruningAdvanced() {
    }

    static private ArrayList<Piece> movePath;

    /**
     * Execute the algorithm
     *
     * @param board    the board to play on
     * @param MaxDepth the maximum depth
     */
    static void run(DefaultBoard board, int MaxDepth) {
        if (MaxDepth < 1) {
            throw new IllegalArgumentException("MaxDepth must be greater than 0.");
        }

        AlphaBetaPruningAdvanced.MaxDepth = MaxDepth;
        movePath = new ArrayList<>(MaxDepth);
        alphaBeta(board.getTurn(), board, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, 0);
        System.out.println("AI plays on (" + board.getLastPiece().getCoordinate().getX() + "," + board.getLastPiece().getCoordinate().getY() + ")");
    }

    /**
     * minimax algorithm
     *
     * @param player       the player that moves (Type that AI identifies as)
     * @param board        the board to play on
     * @param currentDepth the current depth
     * @return the score of board
     */
    private static int alphaBeta(Piece.PieceType player, DefaultBoard board, double alpha, double beta, int currentDepth) {
        if ((currentDepth++ == MaxDepth) || board.isGameOver()) {
            for (int i = movePath.size() - 1; i >= currentDepth - 1; i--) { //stop not in the final depth, remove node after the stop node
                movePath.remove(i);// stop at i, remove i+1 (index in array = i) and later layer
            }
            return finalHeuristic(player, board, currentDepth);
            //return heuristicFun2(player,board);
        }
        if (board.getTurn() == player) {
            return getMax(player, board, alpha, beta, currentDepth);
        } else {
            return getMin(player, board, alpha, beta, currentDepth);
        }
    }

static class posEval {
    posEval(int index, int evalValue) {
        this.index = index;
        this.evalValue = evalValue;
    }

    int index;
    int evalValue;
}

    private static int getMax(Piece.PieceType player, DefaultBoard board, double alpha, double beta, int currentDepth) {
        int indexOfBestMove = -1;

        ArrayList<posEval> list = new ArrayList<>();
        for (Integer move : board.getAvailableCells()) {
            DefaultBoard copy = board.copyBoard();
            Piece[][] boardInfo = copy.getBoard();
            if (checkMoveAdjacent(move, boardInfo) != 0) {
                copy.moveAt(move, true);
                int eval = heuristicScore(player, copy, currentDepth);
                posEval cur = new posEval(move, eval);
                list.add(cur);
            }
        }
        Collections.sort(list, new Comparator<posEval>() {
            @Override
            public int compare(posEval o1, posEval o2) {
                return o2.evalValue - o1.evalValue;
            }
        });

        for (int i = 0; i < list.size(); i++) {
            int theMove = list.get(i).index;
            DefaultBoard modifiedBoard = board.copyBoard();
            Piece[][] boardInfo = modifiedBoard.getBoard();
            modifiedBoard.moveAt(theMove, true);
            if (movePath.size() == currentDepth - 1) {
                movePath.add(currentDepth - 1, modifiedBoard.getLastPiece());
            } else {
                movePath.set(currentDepth - 1, modifiedBoard.getLastPiece());
            }
            double score = alphaBeta(player, modifiedBoard, alpha, beta, currentDepth);

            if (score > alpha) {
                alpha = score;
                indexOfBestMove = theMove;
            }
            if (alpha >= beta) {
                break;
            }

        }

        if (indexOfBestMove != -1) {
            board.moveAt(indexOfBestMove, true);
        }
        return (int) alpha;
    }

    private static int getMin(Piece.PieceType player, DefaultBoard board, double alpha, double beta, int currentDepth) {
        int indexOfBestMove = -1;

        ArrayList<posEval> list = new ArrayList<>();
        for (Integer move : board.getAvailableCells()) {
            DefaultBoard copy = board.copyBoard();
            Piece[][] boardInfo = copy.getBoard();
            if (checkMoveAdjacent(move, boardInfo) != 0) {
                copy.moveAt(move, false);
                int eval = heuristicScore(player, copy, currentDepth);
                posEval cur = new posEval(move, eval);
                list.add(cur);
            }
        }
        Collections.sort(list, new Comparator<posEval>() {
            @Override
            public int compare(posEval o1, posEval o2) {
                return o1.evalValue - o2.evalValue;
            }
        });

        for (int i = 0; i < list.size(); i++) {
            int theMove = list.get(i).index;
            DefaultBoard modifiedBoard = board.copyBoard();
            Piece[][] boardInfo = modifiedBoard.getBoard();
            modifiedBoard.moveAt(theMove, false);
            if (movePath.size() == currentDepth - 1) {
                movePath.add(currentDepth - 1, modifiedBoard.getLastPiece());
            } else {
                movePath.set(currentDepth - 1, modifiedBoard.getLastPiece());
            }
            double score = alphaBeta(player, modifiedBoard, alpha, beta, currentDepth);

            if (score < beta) {
                beta = score;
                indexOfBestMove = theMove;
            }
            if (alpha >= beta) {
                break;
            }
        }

        if (indexOfBestMove != -1) {
            board.moveAt(indexOfBestMove, false);
        }
        return (int) beta;
    }

    private static int checkMoveAdjacent(int index, Piece[][] board) {
        int width = board.length - 1;
        int x = index % board.length;
        int y = index / board.length;
        int occpupiedCount = 0;
        int finalLayer = 3; //depth of explore layer
        int curLayer = finalLayer;
        int xL = x - curLayer;
        int xU = x + curLayer;
        int yL = y - curLayer;
        int yU = y + curLayer;

        if (xL < 0) {
            xL = 0;
        }
        if (xU > width) {
            xU = width;
        }
        if (yU > width) {
            yU = width;
        }
        if (yL < 0) {
            yL = 0;
        }
        for (int i = xL; i <= xU; i++) {
            for (int j = yL; j <= yU; j++) {
                if (isOccupied(i, j, board)) {
                    occpupiedCount++;
                }
            }
        }
        return occpupiedCount;
    }

    private static boolean isOccupied(int x, int y, Piece[][] board) {
        if (board[x][y].getType() != Piece.PieceType.Blank) {
            return true;
        } else {
            return false;
        }
    }


    private static double CheckCol(int x, int y, DefaultBoard board) {
        int i, j;
        i = j = y;
        Piece[][] boardinfo = board.getBoard();
        Piece.PieceType current = board.getLastPiece().getType();
        Piece.PieceType curOpponent = (current == Piece.PieceType.X) ? Piece.PieceType.O : Piece.PieceType.X;
        while (i > -1 && boardinfo[x][i].getType() == current) {
            i--;
        }
        while (j < board.getWidth() && boardinfo[x][j].getType() == current) {
            j++;
        }
        int continuous = j - i - 1;
        int left1 = i;
        int right1 = j;
        while (i > -1 && boardinfo[x][i].getType() != curOpponent) {
            i--;
        }
        while (j < board.getWidth() && boardinfo[x][j].getType() != curOpponent) {
            j++;
        }
        if (j - right1 + continuous >= board.getGoal() && left1 - i + continuous >= board.getGoal()) {
            return Math.pow(4, continuous);
        } else if (j - i - 1 < board.getGoal()) {
            return 0;
        } else {
            return Math.pow(4, continuous - 1);
        }
    }

    private static double CheckRow(int x, int y, DefaultBoard board) {
        int i, j;
        i = j = x;
        Piece[][] boardinfo = board.getBoard();
        Piece.PieceType current = board.getLastPiece().getType();
        Piece.PieceType curOpponent = (current == Piece.PieceType.X) ? Piece.PieceType.O : Piece.PieceType.X;
        while (i > -1 && boardinfo[i][y].getType() == current) {
            i--;
        }
        while (j < board.getWidth() && boardinfo[j][y].getType() == current) {
            j++;
        }
        int continuous = j - i - 1;
        int left1 = i;
        int right1 = j;
        while (i > -1 && boardinfo[i][y].getType() != curOpponent) {
            i--;
        }
        while (j < board.getWidth() && boardinfo[j][y].getType() != curOpponent) {
            j++;
        }
        if (j - right1 + continuous >= board.getGoal() && left1 - i + continuous >= board.getGoal()) {
            return Math.pow(4, continuous);
        } else if (j - i - 1 < board.getGoal()) {
            return 0;
        } else {
            return Math.pow(4, continuous - 1);
        }
    }

    private static double CheckDiagonalFromTopLeft(int x, int y, DefaultBoard board) {
        int ix, jx, iy, jy;
        ix = jx = x;
        iy = jy = y;
        Piece[][] boardinfo = board.getBoard();
        Piece.PieceType current = board.getLastPiece().getType();
        Piece.PieceType curOpponent = (current == Piece.PieceType.X) ? Piece.PieceType.O : Piece.PieceType.X;
        while (ix > -1 && iy < board.getWidth() && boardinfo[ix][iy].getType() == current) {
            ix--;
            iy++;
        }
        while (jx < board.getWidth() && jy > -1 && boardinfo[jx][jy].getType() == current) {
            jx++;
            jy--;
        }
        int continuous = jx - ix - 1;
        int left1 = ix;
        int right1 = jx;
        while (ix > -1 && iy < board.getWidth() && boardinfo[ix][iy].getType() != curOpponent) {
            ix--;
            iy++;
        }
        while (jx < board.getWidth() && jy > -1 && boardinfo[jx][jy].getType() != curOpponent) {
            jx++;
            jy--;
        }
        if (jx - right1 + continuous >= board.getGoal() && left1 - ix + continuous >= board.getGoal()) {
            return Math.pow(4, continuous);
        } else if (jx - ix - 1 < board.getGoal()) {
            return 0;
        } else {
            return Math.pow(4, continuous - 1);
        }
    }

    private static double CheckDiagonalFromTopRight(int x, int y, DefaultBoard board) {
        int ix, jx, iy, jy;
        ix = jx = x;
        iy = jy = y;
        Piece[][] boardinfo = board.getBoard();
        Piece.PieceType current = board.getLastPiece().getType();
        Piece.PieceType curOpponent = (current == Piece.PieceType.X) ? Piece.PieceType.O : Piece.PieceType.X;
        while (ix > -1 && iy > -1 && boardinfo[ix][iy].getType() == current) {
            ix--;
            iy--;
        }
        while (jx < board.getWidth() && jy < board.getWidth() && boardinfo[jx][jy].getType() == current) {
            jx++;
            jy++;
        }
        int continuous = jx - ix - 1;
        int left1 = ix;
        int right1 = jx;
        while (ix > -1 && iy > -1 && boardinfo[ix][iy].getType() != curOpponent) {
            ix--;
            iy--;
        }
        while (jx < board.getWidth() && jy < board.getWidth() && boardinfo[jx][jy].getType() == current) {
            jx++;
            jy++;
        }
        if (jx - right1 + continuous >= board.getGoal() && left1 - ix + continuous >= board.getGoal()) {
            return Math.pow(4, continuous);
        } else if (jx - ix - 1 < board.getGoal()) {
            return 0;
        } else {
            return Math.pow(4, continuous - 1);
        }
    }

    private static int heuristicScore(Piece.PieceType player, DefaultBoard board, int currentDepth) {
        if (player == Piece.PieceType.Blank) {
            throw new IllegalArgumentException("player must be X or O");
        }
        Piece current = board.getLastPiece();
        int x = current.getCoordinate().getX();
        int y = current.getCoordinate().getY();
        double score = 0;
        if (board.isGameOver()) {
            if (board.getWinner() == null) {
                score = 0;   //draw
            } else {
                score = Double.POSITIVE_INFINITY;
            }
        } else {
            score = CheckCol(x, y, board) + CheckRow(x, y, board) + CheckDiagonalFromTopLeft(x, y, board) + CheckDiagonalFromTopRight(x, y, board);
        }
        if (current.getType() == board.getOurtype()) {
            //return (int)score - currentDepth;
            return (int) score;
        } else {
            //return currentDepth - (int)score;
            return -(int) score;
        }
    }

    private static int finalHeuristic(Piece.PieceType player, DefaultBoard board, int currentDepth) {
        if (player == Piece.PieceType.Blank) {
            throw new IllegalArgumentException("player must be X or O");
        }
        Piece current = board.getLastPiece();
        int x = current.getCoordinate().getX();
        int y = current.getCoordinate().getY();
        double score = 0;
        if (board.isGameOver()) {
            if (board.getWinner() == null) {
                score = 0;   //draw
            } else {
                score = Double.POSITIVE_INFINITY;
            }
        } else {
            for (int i = 0; i < movePath.size(); i++) {
                Piece move = movePath.get(i);
                int xi = move.getCoordinate().getX();
                int yi = move.getCoordinate().getY();
                if (move.getType() == board.getOurtype()) {
                    score += CheckCol(xi, yi, board) + CheckRow(xi, yi, board) + CheckDiagonalFromTopLeft(xi, yi, board) + CheckDiagonalFromTopRight(xi, yi, board);
                } else {
                    score -= (CheckCol(xi, yi, board) + CheckRow(xi, yi, board) + CheckDiagonalFromTopLeft(xi, yi, board) + CheckDiagonalFromTopRight(xi, yi, board));
                }
            }
        }
        if (current.getType() == board.getOurtype()) {
            //return (int)score - currentDepth;
            return (int) score;
        } else {
            //return currentDepth - (int)score;
            return -(int) score;
        }
    }

    private static double heuristicFun2(Piece.PieceType player, DefaultBoard board) {
        if (player == Piece.PieceType.Blank) {
            throw new IllegalArgumentException("Player must be X or O.");
        }

        Piece.PieceType opponent = (player == Piece.PieceType.X) ? Piece.PieceType.O : Piece.PieceType.X;
        if (board.isGameOver() && board.getWinner() == player) {
            return 10;
        } else if (board.isGameOver() && board.getWinner() == opponent) {
            return -10;
        } else {
            return 0;
        }
    }
}