package edu.gwu.seas.ai.team6.io;

import com.google.gson.Gson;
import edu.gwu.seas.ai.team6.game.board.DefaultCoordinate;
import edu.gwu.seas.ai.team6.game.board.interfaces.Coordinate;
import edu.gwu.seas.ai.team6.io.util.BoardInfo;
import edu.gwu.seas.ai.team6.io.util.Move;
import edu.gwu.seas.ai.team6.io.util.MoveList;
import okhttp3.*;

import java.io.IOException;
import java.util.List;

import static edu.gwu.seas.ai.team6.util.ProjProps.*;

/**
 * An implementation of {@link Portal} which is used to communicate with the game server.
 *
 * @author qijiuzhi
 * @date 2019-03-06
 */
public class GameServerPortal extends AbstractPortal {
    private final OkHttpClient client = new OkHttpClient();
    private final Gson gson = new Gson();

    /**
     * A ParamEntry object stands for a pair of key and value
     * which is used as the params in a HTTP GET request.
     */
    private class ParamEntry {
        String key;
        String value;

        private ParamEntry(String key, String value) {
            this.key = key;
            this.value = value;
        }

        private String getKey() {
            return key;
        }

        private String getValue() {
            return value;
        }
    }

    @FunctionalInterface
    private interface Indexer {
        /**
         * get the index of the goal string from the origin string
         *
         * @param string the origin string
         * @param goal   the substring that you want to get index of
         * @return the index of the goal in the origin string
         */
        int indexOf(String string, String goal);
    }

    /**
     * @see Portal#createGame(String)
     */
    @Override
    public String createGame(String opponentTeamId) {
        log("Sending create game request to the game server...");

        // create a form body
        FormBody body = new FormBody.Builder().
                add("teamId1", TEAM_ID).
                add("teamId2", opponentTeamId).
                add("type", BODY_TYPE_GAME).
                add("gameType", BODY_GAME_TYPE_TTT).build();

        // generate a new POST request
        Request postRequest = createPostRequest(body);

        String gameId = sendRequest(postRequest, ":", 1, String::lastIndexOf, "}", 1, String::lastIndexOf);

        log("Returned game id: " + gameId);

        return gameId;
    }

    /**
     * @see Portal#moveAt(Coordinate, String)
     */
    @Override
    public String moveAt(Coordinate coordinate, String gameId) {
        log("Sending move request to the game server...");

        // create a form body
        FormBody body = new FormBody.Builder().
                add("teamId", TEAM_ID).
                add("move", coordinate.toString()).
                add("type", BODY_TYPE_MOVE).
                add("gameId", gameId).build();

        // send the request
        Request request = createPostRequest(body);

        String moveId = sendRequest(request, ":", 1, String::indexOf, ",", 1, String::indexOf);

        log("Returned move id: " + moveId);

        return moveId;
    }

    /**
     * @see Portal#moveAt(int, int, String)
     */
    @Override
    public String moveAt(int x, int y, String gameId) {
        return moveAt(new DefaultCoordinate(x, y), gameId);
    }

    /**
     * @see Portal#getMoves(String, int)
     */
    @Override
    public List<Move> getMoves(String gameId, int count) {

        if (count < 1) {
            log("The number of moves you want to retrieve from the server is less than 1. Request was not sent.");
            return null;
        }

        log("Retrieving recent " + count + " move(s) from the game server...");

        Request getRequest = createGetRequest(
                new ParamEntry("type", PARAMS_TYPE_MOVES),
                new ParamEntry("gameId", gameId),
                new ParamEntry("count", count + ""));

        String json = sendRequest(getRequest, "{", 0, String::indexOf, "}", 0, String::lastIndexOf);

        if (json == null) {
            return null;
        }

        MoveList moveList = gson.fromJson(json, MoveList.class);

        return moveList.getMoves();
    }

    /**
     * @see Portal#getLastMove(String)
     */
    @Override
    public Move getLastMove(String gameId) {
        List<Move> list = getMoves(gameId, 1);
        if (list == null || list.size() == 0) {
            log("No moves on the board yet");
            return null;
        }
        return list.get(0);
    }

    /**
     * @see Portal#getBoardString(String)
     */
    @Override
    public String getBoardString(String gameId) {
        log("Requesting the board string...");

        return getBoardInfo(PARAMS_TYPE_BOARD_STRING, gameId);
    }

    /**
     * @see Portal#getBoardMap(String)
     */
    @Override
    public String getBoardMap(String gameId) {
        log("Requesting the board map...");

        return getBoardInfo(PARAMS_TYPE_BOARD_MAP, gameId);
    }

    private String getBoardInfo(String type, String gameId) {
        Request request = createGetRequest(new ParamEntry("type", type), new ParamEntry("gameId", gameId));
        String json = sendRequest(request, "{", 0, String::indexOf, "}", 0, String::lastIndexOf);

        if (json == null) {
            return null;
        }

        BoardInfo boardInfo = gson.fromJson(json, BoardInfo.class);

        return boardInfo.getOutput();
    }

    private Request createPostRequest(FormBody body) {
        return new Request.Builder().
                post(body).
                addHeader("x-api-key", X_API_KEY).
                addHeader("userId", USER_ID).
                addHeader("Content-Type", CONTENT_TYPE).
                url(SERVER_ADDR).build();
    }

    private Request createGetRequest(ParamEntry... entries) {
        return new Request.Builder().get().
                addHeader("x-api-key", X_API_KEY).
                addHeader("userId", USER_ID).
                addHeader("Content-Type", CONTENT_TYPE).
                url(getURL(entries)).build();
    }

    private String getURL(ParamEntry[] entries) {
        StringBuilder sb = new StringBuilder(SERVER_ADDR);
        if (entries.length == 0) {
            return sb.toString();
        }
        sb.append("?");
        for (int i = 0; i < entries.length; i++) {
            sb.append(entries[i].getKey());
            sb.append("=");
            sb.append(entries[i].getValue());
            if (i == entries.length - 1) {
                break;
            }
            sb.append("&");
        }
        return sb.toString();
    }

    /**
     * sends request to the server.
     * <p>
     * This is what this method does:
     * 1. call the request via the http client
     * 2. get the string encapsulated in the http body
     * 3. get the first anchor index by using the separator1 and indexer1
     * 4. get the second anchor index by using the separator2 and indexer2
     * 5. get the start index of the interested string which equals (first anchor + prior)
     * 6. get the end index of the interested string which equals (second anchor - behind + 1)
     * 7. get the interested string by the indices we got from step 5 and 6
     * 8. return the interested string
     *
     * @param request    request
     * @param separator1 the first separator
     * @param prior      the difference between the indices of the separator1 and of the interested string
     * @param indexer1   the indexer for the first separator
     * @param separator2 the second separator
     * @param behind     the difference between the indices of the last char of the interested string and of the separator2
     * @param indexer2   the indexer for the second separator
     * @return the interested substring of the response from the game server
     */
    private String sendRequest(Request request, String separator1, int prior, Indexer indexer1, String separator2, int behind, Indexer indexer2) {
        Call call = client.newCall(request);

        String interestedStr = null;

        String msg = null;

        try {
            // get the response from the game server
            Response response = call.execute();

            if (response.isSuccessful()) {

                // get the String encapsulated in the body
                msg = new String(response.body().bytes());

                // if the server returns a FAIL code
                if (msg.contains(CODE_FAIL)) {
                    throw new IOException();
                }

                log("The json string returned by the server: " + msg);

                // get the index of separator1
                int index1 = indexer1.indexOf(msg, separator1);
                // get the index of separator2
                int index2 = indexer2.indexOf(msg, separator2);

                // if cannot find the index of either separator1 or 2, throw an IOException
                if (index1 == -1 || index2 == -1) {
                    throw new IOException();
                }

                // get the substring of the interested str
                interestedStr = msg.substring(index1 + prior, index2 - behind + 1);

            }
        } catch (IOException e) {
            String info = "Couldn't resolve the json returned by the server." +
                    System.lineSeparator() + "\t\tResult: " + msg;
            log(info);
            System.out.println(info);
            e.printStackTrace();
        }
        return interestedStr;
    }
}
