package pl.fitandyummy.ilebije.broadcast;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.io.Serializable;

import pl.fitandyummy.ilebije.PudelkoActivity;
import pl.fitandyummy.ilebije.R;

import static pl.fitandyummy.ilebije.broadcast.App.CHANEL_X_ID;

/**
 * Created by kabi on 2018-09-25.
 */

public class KoniecNotyfication_reciver extends BroadcastReceiver implements Serializable {

    @Override
    public void onReceive(Context context, Intent intent) {

        context.getApplicationContext();

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context.getApplicationContext());

        Intent repeating_intent = new Intent(context, PudelkoActivity.class);
        repeating_intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 999, repeating_intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notification = new NotificationCompat.Builder(context.getApplicationContext(), CHANEL_X_ID)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.dragi)
                .setColor(Color.RED)
                .setContentTitle("ILE BIJE informuje, że zakończyłeś cykl.")
                .setContentText("Kliknij aby przejść do apteczki ")
                .setAutoCancel(true);

        notificationManager.notify(999, notification.build());
    }
}