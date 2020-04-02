package pl.fitandyummy.ilebije.broadcast;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class App extends Application {

    public static final String CHANEL_1_ID = "chanel1";
    public static final String CHANEL_2_ID = "chanel2";
    public static final String CHANEL_3_ID = "chanel3";
    public static final String CHANEL_4_ID = "chanel4";
    public static final String CHANEL_5_ID = "chanel5";
    public static final String CHANEL_6_ID = "chanel6";




    public static final String CHANEL_X_ID = "chanelX";
    public static final String CHANEL_CHUJ_ID = "chanelCHUJ";
    public static final String CHANEL_CHUJX_ID = "chanelCHUJX";
    public static final String CHANEL_1_ID_RELOAD ="chanel1reload" ;

    @Override
    public void onCreate() {
        super.onCreate();

        createNotificationChannels();

    }

    private void createNotificationChannels() {

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){


            NotificationChannel channel1 = new NotificationChannel(
                    CHANEL_1_ID,
                    "chanel 1",
                    NotificationManager.IMPORTANCE_HIGH
            );

            NotificationChannel channel1reload = new NotificationChannel(
                    CHANEL_1_ID_RELOAD,
                    "chanel 1",
                    NotificationManager.IMPORTANCE_HIGH
            );

            NotificationChannel channel2 = new NotificationChannel(
                    CHANEL_2_ID,
                    "chanel 2",
                    NotificationManager.IMPORTANCE_HIGH
            );
            NotificationChannel channel3 = new NotificationChannel(
                    CHANEL_3_ID,
                    "chanel 3",
                    NotificationManager.IMPORTANCE_HIGH
            );
            NotificationChannel channel4 = new NotificationChannel(
                    CHANEL_4_ID,
                    "chanel 4",
                    NotificationManager.IMPORTANCE_HIGH
            );
            NotificationChannel channel5 = new NotificationChannel(
                    CHANEL_5_ID,
                    "chanel 5",
                    NotificationManager.IMPORTANCE_HIGH
            );
            NotificationChannel channel6 = new NotificationChannel(
                    CHANEL_6_ID,
                    "chanel 6",
                    NotificationManager.IMPORTANCE_HIGH
            );



            NotificationChannel channelX = new NotificationChannel(
                    CHANEL_X_ID,
                    "chanel X",
                    NotificationManager.IMPORTANCE_HIGH
            );

            NotificationChannel channelCHUJ = new NotificationChannel(
                    CHANEL_CHUJ_ID,
                    "chanel CHUJ",
                    NotificationManager.IMPORTANCE_HIGH
            );

            NotificationChannel channelCHUJX = new NotificationChannel(
                    CHANEL_CHUJX_ID,
                    "chanel CHUJX",
                    NotificationManager.IMPORTANCE_HIGH
            );

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel1);
            manager.createNotificationChannel(channel2);
            manager.createNotificationChannel(channel3);
            manager.createNotificationChannel(channel4);
            manager.createNotificationChannel(channel5);
            manager.createNotificationChannel(channel6);

            manager.createNotificationChannel(channelX);
            manager.createNotificationChannel(channelCHUJ);
            manager.createNotificationChannel(channelCHUJX);

        }
    }


}
