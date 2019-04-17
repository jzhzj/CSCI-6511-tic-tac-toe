package edu.gwu.seas.ai.team6;

import edu.gwu.seas.ai.team6.game.DefaultGame;
import edu.gwu.seas.ai.team6.game.board.interfaces.Piece;
import edu.gwu.seas.ai.team6.io.GameServerPortal;
import edu.gwu.seas.ai.team6.io.Portal;
import edu.gwu.seas.ai.team6.io.util.BoardInfo;

import java.util.Scanner;

public class Main {
    private static String gameId;
    private static String opponentId;

    public static void main(String[] args) {
        Portal portal = new GameServerPortal();
        Piece.PieceType pieceType;
        if (checkArgs(args)) {
            // If this game is initiated by us

            System.out.println("opppnentId: " + opponentId);
            // create a new game on the server via the portal
            gameId = createGame(portal);
            System.out.println("gameId: " + gameId);
            pieceType = Piece.PieceType.O;
        } else {
            // If this game is initiated by the opponent

            pieceType = Piece.PieceType.X;
        }
        BoardInfo info = portal.getBoardString(gameId);
        DefaultGame game = DefaultGame.createGame(opponentId, portal, info.getBoardSize(), info.getTarget(), pieceType, gameId);
        game.run();
    }

    /**
     * Creates a new game on the server.
     * This methods asks users the board size they want and the target size.
     * Users can either use default values or input new values.
     */
    private static String createGame(Portal portal) {
        int boardSize;
        int target;
        String gameId;

        System.out.println("Do you wanna use default boardSize(12) and default target(6)?");
        System.out.println("[Y]es  or  [N]o ?");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        if ("N".equalsIgnoreCase(input) || "No".equalsIgnoreCase(input)) {
            // Users want to input values

            System.out.print("Please input boardSize=");
            boardSize = Integer.parseInt(scanner.nextLine());
            System.out.print("Please input target=");
            target = Integer.parseInt(scanner.nextLine());
            gameId = portal.createGame(opponentId, boardSize, target);
        } else {
            // Users want to use the default values

            gameId = portal.createGame(opponentId);
        }
        return gameId;
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
