package testing.gps_location;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private String[] missionsArr = {
            "Type the guard's name",
            "Find how many lecturers named Sarah there are in Afeka",
            "How old is Ami Moyal?",
            "Enter the lecturer's name whose cell is at the top left corner",
            "How many microwaves are in the Fikus Cafeteria?"
    };

    private String[] locationText = {
            "Go to the Kirya's guard at bney efraim",
            "Go to place 2",
            "Go to place 3",
            "Go to the Fikus building",
            "Go to the Fikus Cafeteria"
    };

    private String[] answersArr = {
            "Moshe",
            "7",
            "49",
            "Amit Gadol",
            "13"
    };

    private List<Pair<Double, Double>> locationArr = new ArrayList<Pair<Double, Double>>();


    private static int level;

    boolean isAbleToAnswer = false;
    TextView missionTextView, locationTargetTextView;
    Button getMyLocation;
    LocationManager locationManager;
    private double latitude, longtitude;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        level = 0;

        locationArr.add(new Pair<Double, Double>(32.1151, 34.8178));
        locationArr.add(new Pair<Double, Double>(32.1151, 34.8178));
        locationArr.add(new Pair<Double, Double>(32.1151, 34.8178));
        locationArr.add(new Pair<Double, Double>(32.1151, 34.8178));
        locationArr.add(new Pair<Double, Double>(32.1151, 34.8178));

        getMyLocation = (Button) findViewById(R.id.button);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        configure_location_button();

        locationTargetTextView = (TextView) findViewById(R.id.locatoinTargeTextView);

        missionTextView = (TextView) findViewById(R.id.missionTextView);

        Button checkButton = (Button) findViewById(R.id.checkButton);

        missionTextView.setText(missionsArr[level].toString());
        missionTextView.setVisibility(View.INVISIBLE);
        locationTargetTextView.setText(locationText[level].toString());
        locationTargetTextView.setVisibility(View.VISIBLE);

        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText answerEditText = (EditText) findViewById(R.id.answerEditText);
                if (isAbleToAnswer) {
                    if (answerEditText.getText().toString().equals(answersArr[level])) {
                        if (level < 1) {
                            Toast.makeText(MainActivity.this, "Good job!", Toast.LENGTH_LONG).show();
                            missionTextView.setText(missionsArr[level+1].toString());
                            locationTargetTextView.setText(locationText[level+1].toString());
                            answerEditText.setText("");
                            missionTextView.setVisibility(View.INVISIBLE);
                            locationTargetTextView.setVisibility(View.VISIBLE);
                            isAbleToAnswer = false;
                            level++;
                        } else {
//                            new AlertDialog.Builder(MainActivity.this)
//                                    .setTitle("You win!")
//                                    .setMessage("Congratulations")
//                                    .setCancelable(false)
//                                    .setPositiveButton("Yaaay", new DialogInterface.OnClickListener() {
//                                        @Override
//                                        public void onClick(DialogInterface dialog, int which) {
//                                            Intent gameIntent = new Intent(MainActivity.this, EndActivity.class);
//                                            startActivity(gameIntent);
//                                        }
//                                    }).show();

                            Intent gameIntent = new Intent(MainActivity.this, EndActivity.class);
                            //gameIntent.putExtra(GameConfiguration.GameIntentLevelKey, level.name());
                            startActivity(gameIntent);
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "Wrong answer", Toast.LENGTH_LONG).show();
                        answerEditText.setText("");
                    }
                }
                else{
                    Toast.makeText(MainActivity.this, "Get to your location first",Toast.LENGTH_LONG).show();
                    answerEditText.setText("");
                }
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 10:
                configure_location_button();
                break;
            default:
                break;
        }
    }

    void configure_location_button(){
        // first check for permissions
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.INTERNET}
                        ,10);
            }
            return;
        }
        // this code won't execute IF permissions are not allowed, because in the line above there is return statement.
        getMyLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  Intent gameIntent = new Intent(MainActivity.this, MapsActivity.class);
                //  startActivity(gameIntent);
                //noinspection MissingPermission
                Location location = null;
                while (location == null) {
                    location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                }

                longtitude = location.getLongitude();
                latitude = location.getLatitude();
                double myLongtitude = locationArr.get(level).second;
                double myLatitude = locationArr.get(level).first;
                if (longtitude - 1 < myLongtitude && myLongtitude < longtitude + 1
                        && latitude - 1 < myLatitude && myLatitude < latitude + 1) {
                    missionTextView.setVisibility(View.VISIBLE);
                    locationTargetTextView.setVisibility(View.INVISIBLE);
                    isAbleToAnswer = true;
                    Toast.makeText(MainActivity.this, "Got there! \n Now answer the question :-)", Toast.LENGTH_LONG).show();
                    // gameIntent.putExtra("long", longtitude);
                }
            }
        });
    }
}


