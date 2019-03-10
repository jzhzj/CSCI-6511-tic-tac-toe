package edu.gwu.seas.ai.team6.util;

import static edu.gwu.seas.ai.team6.util.ProjProps.TEST_FLAG;
import static edu.gwu.seas.ai.team6.util.ProjProps.TEST_STR;

/**
 * A {@link Tester} object can be used during test, it can print information to the standard output by default.
 *
 * @author qijiuzhi
 * @date 2019-03-10
 */
public class Tester {
    /**
     * Logs the info.
     *
     * @param info the information you want to print to the standard output
     */
    protected void log(String info) {
        if (TEST_FLAG) {
            System.out.println(TEST_STR + info);
            System.out.println();
            System.out.println();
        }
    }
}
