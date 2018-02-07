package com.moment.themoment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;



public class WriteClaim extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.TheMoment.MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_claim);
    }

    /* public void saveClaim(View view) {
        Intent intent = new Intent(this, VoteOnClaim.class);
        EditText claim = (EditText) findViewById(R.id.theClaim);
        String message = claim.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }
    */
}
