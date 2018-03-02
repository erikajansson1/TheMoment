package com.moment.themoment;

public interface WaitForClaimCallback {

    void updateWaitForClaim(String response);

    void updateCurrentClaim(Claim claim);

}
