package com.moment.themoment;

/**
 * Created by Nhunter on 2018-02-14.
 */

public interface ServerCommunicationCallback {
    /**
     * Basically a generic callback function signature
     * @param function
     * @param output
     */
    void processFinish(String function, String output);
}
