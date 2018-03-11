package com.moment.themoment;

public interface WriteClaimCallback {

    void goToWaitForClaim();

    void claimCallback(boolean result);
    void claimCallback(int id);
}
