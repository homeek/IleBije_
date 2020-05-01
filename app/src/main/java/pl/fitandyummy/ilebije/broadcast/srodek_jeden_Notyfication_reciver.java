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
import pl.fitandyummy.ilebije.srodki.activity_srodek1_reload;

import static pl.fitandyummy.ilebije.broadcast.App.CHANEL_1_ID;

public class srodek_jeden_Notyfication_reciver extends BroadcastReceiver {

    // public AlarmManager alarmManagerRaz;

    @Override
    public void onReceive(Context context, Intent intent) {

        context.getApplicationContext();

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context.getApplicationContext());

        Intent repeating_intent = new Intent(context, activity_srodek1_reload.class);
        repeating_intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 100, repeating_intent, PendingIntent.FLAG_UPDATE_CURRENT);

        /*
        Intent intent2 = new Intent(context,activity_srodek1_reload.class);
        PendingIntent pendingIntent2 = PendingIntent.getActivity(context, 321,intent2, PendingIntent.FLAG_UPDATE_CURRENT);
        */

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String value = preferences.getString("key", "defaultValue");
        Integer godziny = preferences.getInt("godzinaZakonczenia", 99);
        Integer minuty = preferences.getInt("minuta", 99);

        NotificationCompat.Builder notification = new NotificationCompat.Builder(context.getApplicationContext(), CHANEL_1_ID)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ico_fiolka_lajt)
                .setColor(Color.RED)
                .setContentTitle("ILE BIJE przypomina o strzale.")
                .setContentText("Kolego. Bijesz dziś  " + value + " około " + godziny + ":" + minuty)
                .setAutoCancel(true)
                .setDeleteIntent(pendingIntent);
        // .addAction(int R.drawable.ico_fiolka_lajt, CharSequence title, PendingIntent pendingIntentTrzy);
        notificationManager.notify(100, notification.build());
    }
}