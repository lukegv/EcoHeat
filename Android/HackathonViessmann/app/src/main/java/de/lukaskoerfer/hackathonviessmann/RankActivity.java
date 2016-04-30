package de.lukaskoerfer.hackathonviessmann;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import de.lukaskoerfer.hackathonviessmann.model.UserData;

public class RankActivity extends AppCompatActivity {

    ImageView image;
    TextView text;

    int[] treePics  = {R.mipmap.tree_leaves_0, R.mipmap.tree_leaves_1, R.mipmap.tree_leaves_2,
            R.mipmap.tree_leaves_3,R.mipmap.tree_leaves_4,R.mipmap.tree_leaves_5,
            R.mipmap.tree_leaves_6,R.mipmap.tree_leaves_7, R.mipmap.tree_leaves_8,
            R.mipmap.tree_leaves_9, R.mipmap.tree_leaves_10, R.mipmap.tree_leaves_11,
            R.mipmap.tree_leaves_12, R.mipmap.tree_leaves_13,  R.mipmap.tree_leaves_14,
            R.mipmap.tree_leaves_15, R.mipmap.tree_leaves_16, R.mipmap.tree_leaves_17,
            R.mipmap.tree_leaves_18, R.mipmap.tree_leaves_19,  R.mipmap.tree_leaves_20,
            R.mipmap.tree_leaves_21, R.mipmap.tree_leaves_22};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_rank);

        image = (ImageView) findViewById(R.id.treeImageView);
        text = (TextView)  findViewById(R.id.rankScoreTextView);

    }

    @Override
    protected void onResume() {
        super.onResume();
        int level = UserData.FromSettings(this).getScore();
        image.setImageResource(treePics[level]);
        text.setText("Score: " + Integer.toString(level));
    }
}
