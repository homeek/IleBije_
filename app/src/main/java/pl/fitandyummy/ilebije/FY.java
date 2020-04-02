package pl.fitandyummy.ilebije;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class FY extends AppCompatActivity {

    RelativeLayout relativeLayout;

    TextView txt1, txt2;
    ImageView intro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fy);

        relativeLayout = (RelativeLayout) findViewById(R.id.glownyrelative);
        txt1 = (TextView) findViewById(R.id.introText);
        txt2 = (TextView) findViewById(R.id.introText2);
        intro = (ImageView) findViewById(R.id.introImage);




        ImageView back = (ImageView) findViewById(R.id.toolbarArrowbackBtn);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent doMainIntent = new Intent(getApplicationContext(),PreMainActivity.class);
                startActivity(doMainIntent);
            }
        });



       /* relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent facebookIntent = openFacebook(getApplicationContext());
                startActivity(facebookIntent);
            }
        });*/

        Typeface text1 = Typeface.createFromAsset(getAssets(),"fonts/KO.ttf");
        txt1.setTypeface(text1);
        txt2.setTypeface(text1);



    }




   public static Intent openFacebook(Context context) {

        try {
            context.getPackageManager()
                    .getPackageInfo("com.facebook.katana", 0);
            return new Intent(Intent.ACTION_VIEW,
                    Uri.parse("fb://page/798759363541064"));
        } catch (Exception e){

            return new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://www.facebook.com/FitandYummy71"));
        }


    }


    public void doFY(View view) {

        Intent facebookIntent = openFacebook(getApplicationContext());
        startActivity(facebookIntent);


    }
}
