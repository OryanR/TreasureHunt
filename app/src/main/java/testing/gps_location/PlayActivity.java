package testing.gps_location;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class PlayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        ImageButton playButton = (ImageButton)findViewById(R.id.playButton);

        playButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gameIntent = new Intent(PlayActivity.this, MainActivity.class);
                //gameIntent.putExtra(GameConfiguration.GameIntentLevelKey, level.name());
                startActivity(gameIntent);
            }
        });


    }
}
