package edu.gwu.seas.ai.team6;

import edu.gwu.seas.ai.team6.game.DefaultGame;
import edu.gwu.seas.ai.team6.game.board.interfaces.Piece;
import edu.gwu.seas.ai.team6.io.GameServerPortal;
import edu.gwu.seas.ai.team6.io.Portal;
import edu.gwu.seas.ai.team6.io.util.BoardInfo;

public class Main {
    private static String gameId;
    private static String opponentId;

    public static void main(String[] args) {
        Portal portal = new GameServerPortal();
        Piece.PieceType pieceType;
        if (checkArgs(args)) {
            System.out.println("opppnentId: " + opponentId);
            gameId = portal.createGame(opponentId);
            System.out.println("gameId: " + gameId);
            pieceType = Piece.PieceType.O;
        } else {
            pieceType = Piece.PieceType.X;
        }
        BoardInfo info = portal.getBoardString(gameId);
        DefaultGame game = DefaultGame.createGame(opponentId, portal, info.getBoardSize(), info.getTarget(), pieceType, gameId);
        game.run();
    }

    private static boolean checkArgs(String[] args) {
        if (args.length == 1) {
            if (args[0].contains("opponentId") && args[0].contains("=")) {
                opponentId = getValue(args[0]);
            } else {
                showUsage();
            }
        } else if (args.length == 2) {
            String tmp = args[0] + args[1];
            if (tmp.contains("gameId") && tmp.contains("opponentId") && args[0].contains("=") && args[1].contains("=")) {
                if (args[0].contains("gameId")) {
                    gameId = getValue(args[0]);
                    opponentId = getValue(args[1]);
                } else {
                    opponentId = getValue(args[0]);
                    gameId = getValue(args[1]);
                }
            } else {
                showUsage();
            }
        } else {
            showUsage();
        }

        return args.length == 1;
    }

    private static String getValue(String line) {
        return line.split("=")[1];
    }

    private static void showUsage() {
        System.out.println("You can only input 1 or 2 parameters. \n\nIf you input 1 parameter, it must be opponent id, \nwhich indicating your team is the initializer of a new game. \n\nIf you input 2 parameters, one of them must be game id and the other must be opponent id, \nwhich suggesting the opponent team has already created the game on the server.\n\n");
        System.out.println("i.e. \njava -jar <your jar file> --gameId=1024 --opponentId=10086\n\nCase sensitive");
        System.exit(1);
    }
}
