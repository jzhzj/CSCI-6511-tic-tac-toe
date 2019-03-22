package edu.gwu.seas.ai.team6.game.alg;

import edu.gwu.seas.ai.team6.game.board.DefaultBoard;

public class Algorithm {
    private Algorithm() {
    }

    public static void miniMax(DefaultBoard board, int MaxDepth) {
        MiniMax.run(board, MaxDepth);
    }

    public static void alphaBetaPruning(DefaultBoard board, int MaxDepth) {
        AlphaBetaPruning.run(board, MaxDepth);
    }

    public static void alphaBetaPruningAdvanced(DefaultBoard board, int MaxDepth) {
        AlphaBetaPruningAdvanced.run(board, MaxDepth);
    }
}

