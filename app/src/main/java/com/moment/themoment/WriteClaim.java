package com.moment.themoment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


public class WriteClaim extends AppCompatActivity implements WriteClaimCallback{
    Player clientPlayer;
    Room currentRoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_claim);

        this.clientPlayer = (Player) getIntent().getSerializableExtra("playerData");
        this.currentRoom = (Room) getIntent().getSerializableExtra("roomData");

    }

    public void saveClaim(View view) {
        EditText claim = findViewById(R.id.theClaim);
        String message = claim.getText().toString();

        RadioGroup theGroup = findViewById(R.id.TrueFalseGroup);

        if(theGroup.getCheckedRadioButtonId()==-1)
        {
            Toast.makeText(getApplicationContext(), "Please pick a correct answer", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(message)){
            Toast.makeText(getApplicationContext(), "Please write a claim", Toast.LENGTH_SHORT).show();
        }
            else
            {
                int selectedId = theGroup.getCheckedRadioButtonId();
                RadioButton selectedRadioButton = findViewById(selectedId);
                String answer = selectedRadioButton.getText().toString();
                Boolean boolansw = setBool(answer);
                clientPlayer.setClaim(new Claim(message, boolansw));
                sendToServer(clientPlayer);
            }
    }


    private void sendToServer(Player clientPlayer){
        ServerCommunication serverCom = new ServerCommunication(this);
        serverCom.saveClaimAndAnswer(clientPlayer, this);
    }


    private Boolean setBool(String message){
        return message.equals("true");
    }

    public void goToWaitForClaim(){
        Intent intent = new Intent(this, WaitForClaim.class);
        intent.putExtra("playerData", clientPlayer);
        intent.putExtra("roomData", currentRoom);
        startActivity(intent);
    }
}


