package edu.gwu.seas.ai.team6.io;

import edu.gwu.seas.ai.team6.game.board.interfaces.Coordinate;
import edu.gwu.seas.ai.team6.io.util.Move;
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

    /**
     * @see Portal#createGame(String)
     */
    @Override
    public String createGame(String opponentTeamId) {

        // create a form body
        FormBody body = new FormBody.Builder().
                add("teamId1", TEAM_ID).
                add("teamId2", opponentTeamId).
                add("type", BODY_TYPE_GAME).
                add("gameType", BODY_GAME_TYPE_TTT).build();

        // generate a new POST request
        Request postRequest = createPostRequest(body);
        // send the request
        Call call = client.newCall(postRequest);

        String gameId = null;

        try {
            // get the response from the game server
            Response response = call.execute();

            if (response.isSuccessful()) {

                // get the String encapsulated in the body
                String msg = new String(response.body().bytes());

                // get the last index of ":", which plus one equals to the start index of the game id
                int index = msg.lastIndexOf(":");

                // if there is no ":" in the String, throw an IOException
                if (index == -1) {
                    System.out.println(
                            "Couldn't resolve the json returned by the server." +
                                    System.lineSeparator() + "Result: " + msg);
                    throw new IOException();
                }

                // get the substring of the game id
                gameId = msg.substring(index + 1, msg.length() - 1);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return gameId;
    }

    /**
     * @see Portal#moveAt(Coordinate, String)
     */
    @Override
    public String moveAt(Coordinate coordinate, String gameId) {
        return null;
    }

    /**
     * @see Portal#moveAt(int, int, String)
     */
    @Override
    public String moveAt(int x, int y, String gameId) {
        return null;
    }

    /**
     * @see Portal#getMoves(String, int)
     */
    @Override
    public List<Move> getMoves(String gameId, int count) {
        return null;
    }

    /**
     * @see Portal#getLastMove(String)
     */
    @Override
    public Move getLastMove(String gameId) {
        return null;
    }

    /**
     * @see Portal#getBoardString(String)
     */
    @Override
    public String getBoardString(String gameId) {
        return null;
    }

    /**
     * @see Portal#getBoardMap(String)
     */
    @Override
    public String getBoardMap(String gameId) {
        return null;
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
}
