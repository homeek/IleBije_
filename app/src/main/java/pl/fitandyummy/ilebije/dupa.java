package pl.fitandyummy.ilebije;

import android.graphics.Typeface;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

public class dupa extends AppCompatActivity {

    TextView textView, telebim, textView2;
    public Typeface text1;

    public Chronometer chronometerWork;
    public Chronometer chronometerRest;
    public ScrollView mSvlaps2, mSvlaps;

    private ImageView TheButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dupa);

        chronometerWork = (Chronometer) findViewById(R.id.chronoWork);
        chronometerRest = (Chronometer) findViewById(R.id.godzinaTextV);

        TheButton = (ImageView) findViewById(R.id.button2);

        textView2 = (TextView) findViewById(R.id.mEtLaps);
        textView = (TextView) findViewById(R.id.mEtLaps2);
        mSvlaps = (ScrollView) findViewById(R.id.mSvLaps);
        mSvlaps2 = (ScrollView) findViewById(R.id.mSvLaps2);

        telebim = (TextView) findViewById(R.id.telebim);
        text1 = Typeface.createFromAsset(getAssets(), "fonts/KO.ttf");

        chronometerWork.setTypeface(text1);
        chronometerRest.setTypeface(text1);

        textView2.setTypeface(text1);
        textView.setTypeface(text1);

        telebim.setTypeface(text1);
    }
}
