package pl.fitandyummy.ilebije.broadcast;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import pl.fitandyummy.ilebije.R;
import pl.fitandyummy.ilebije.srodki.activity_srodek3_reload;

import static pl.fitandyummy.ilebije.broadcast.App.CHANEL_3_ID;

public class srodek_trzy_Notyfication_reciver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {



        context.getApplicationContext();



        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context.getApplicationContext());

        Intent repeating_intent = new Intent(context, activity_srodek3_reload.class);

        repeating_intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);


        PendingIntent pendingIntent = PendingIntent.getActivity(context, 300, repeating_intent, PendingIntent.FLAG_UPDATE_CURRENT);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String value = preferences.getString("keytrzy", "defaultValue");
        Integer godziny = preferences.getInt("godzinatrzy", 99);
        Integer minuty = preferences.getInt("minutatrzy", 99);


        NotificationCompat.Builder notification = new NotificationCompat.Builder(context.getApplicationContext(), CHANEL_3_ID)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ico_omcia_lajt)
                .setColor(Color.RED)
                .setContentTitle("ILE BIJE przypomina o strzale.")
                .setContentText("Kolego. Bijesz dziś  " + value + " około " + godziny + ":" + minuty)
                .setAutoCancel(true)
                .setDeleteIntent(pendingIntent);


        notificationManager.notify(300, notification.build());









    }

}

