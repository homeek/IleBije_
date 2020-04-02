package pl.fitandyummy.ilebije;

import android.content.Intent;
import android.graphics.Typeface;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

public class Intro extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        final ImageView introImage = (ImageView) findViewById(R.id.introImage);
        final TextView introText = (TextView) findViewById(R.id.introText);
        final TextView introText2 = (TextView) findViewById(R.id.introText2);


        introImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent doMainIntent = new Intent(getApplicationContext(),PreMainActivity.class);
                startActivity(doMainIntent);
            }
        });



        Typeface text1 = Typeface.createFromAsset(getAssets(),"fonts/SO.ttf");
        introText.setTypeface(text1);

        Typeface text2 = Typeface.createFromAsset(getAssets(),"fonts/SO.ttf");
        introText2.setTypeface(text2);


        final Animation fadeInAnimation = AnimationUtils.loadAnimation(this,R.anim.fade_in);
        introImage.startAnimation(fadeInAnimation);
        fadeInAnimation.setFillAfter(true);


       /*final RotateAnimation rotateAnimation = new RotateAnimation(

                0,360,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
        );
        rotateAnimation.setDuration(2000);
        rotateAnimation.setFillAfter(true);*/

        final ScaleAnimation scaleAnimation2 = new ScaleAnimation(
                1.5f,0.5f,
                1.5f,0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f


        );
        scaleAnimation2.setDuration(2000);
        scaleAnimation2.setFillAfter(true);

        final ScaleAnimation scaleAnimation = new ScaleAnimation(
                0.5f,1f,
                0.5f,1f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
        );

        scaleAnimation.setDuration(3000);
        scaleAnimation.setFillAfter(true);

        scaleAnimation2.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

                introText.startAnimation(fadeInAnimation);
                //introText.startAnimation(rotateAnimation);
                introText2.startAnimation(fadeInAnimation);


            }

            @Override
            public void onAnimationEnd(Animation animation) {
                introImage.startAnimation(scaleAnimation);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        introImage.startAnimation(scaleAnimation2);

        scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Intent intent = new Intent(getApplicationContext(), PreMainActivity.class);
                startActivity(intent);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }
}
