package com.moment.themoment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ResultPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_page);
        View nameLayout =  findViewById(R.id.NameList);
        View scoreLayout =  findViewById(R.id.ScoreList);
        addPlayerResult(nameLayout,scoreLayout);
    }

    private void addPlayerResult(View nameLayout, View scoreLayout) {
        //TODO input should be player class object
        String playerName = "TestDude";
        Integer score = 100;

        //TODO will we need name ID again, change in this function
        TextView nameTV = createPlayerName(playerName, View.generateViewId());
        //TODO will we need score ID again, change in this function
        TextView scoreTV = createPlayerScore(score, View.generateViewId());
        ((LinearLayout) nameLayout).addView(nameTV);
        ((LinearLayout) scoreLayout).addView(scoreTV);
    }

    private TextView createPlayerName(String name, Integer id) {
        TextView playerNameTV = new TextView(this);
        playerNameTV.setText(String.valueOf(name));
        playerNameTV.setId(id);
        playerNameTV.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
        playerNameTV.setGravity(Gravity.CENTER);
        return playerNameTV;
    }

    private TextView createPlayerScore(Integer score, Integer id) {
        TextView playerScoreTV = new TextView(this);
        playerScoreTV.setText(String.valueOf(score+" pts"));
        playerScoreTV.setId(id);
        playerScoreTV.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
        playerScoreTV.setGravity(Gravity.CENTER);
        return playerScoreTV;
    }
    //TODO Create playerclass object internally, if need arises make global
}
