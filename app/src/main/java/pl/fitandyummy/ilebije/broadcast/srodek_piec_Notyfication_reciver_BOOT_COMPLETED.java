package pl.fitandyummy.ilebije.broadcast;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

public class srodek_piec_Notyfication_reciver_BOOT_COMPLETED extends BroadcastReceiver  {



    public AlarmManager alarmManagerPiec;

    public PendingIntent pendingIntentPiec;

    public int dataRok;
    public int dataMiesiac;
    public int dataDzien;
    public int timegodziny;
    public int timeminuty;

    public Integer loadOkresszesc;

    public SharedPreferences preferences;
    public String terminNastepnegoBiciaSTR;

    public Timer timer;

    Boolean boolBoot = false;



    @Override
    public void onReceive(final Context context5, Intent intent5) {


        context5.getApplicationContext();


        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context5);

//value = preferences.getString("keyszesc", "defaultValue");
        timegodziny = preferences.getInt("godzinapiec", 99);
        timeminuty = preferences.getInt("minutapiec", 99);

// przekazywanie daty  po to aby wstawiał date od pierwszego bicia
        dataDzien = preferences.getInt("dzienBiciapiec", 99);
        dataMiesiac = preferences.getInt("miesiacBiciapiec", 99);
        dataRok = preferences.getInt("rokBiciapiec", 99);

        loadOkresszesc = preferences.getInt("okrespiec", 99);

        boolBoot = preferences.getBoolean("bollBoot",false);



        if (boolBoot == false){

            Toast.makeText(context5, "   " , Toast.LENGTH_LONG).show();


        } else if (boolBoot == true) {

            Toast.makeText(context5, "    ", Toast.LENGTH_LONG).show();


            //  Toast.makeText(context5, " Potwierdzono zakłuty poślad !!!   Następne bicie  ", Toast.LENGTH_LONG).show();


            final Calendar c = Calendar.getInstance();
            final int year = c.get(Calendar.YEAR);
            final int month = c.get(Calendar.MONTH);
            final int day = c.get(Calendar.DAY_OF_MONTH);


            final int h = c.get(Calendar.HOUR_OF_DAY);
            final int m = c.get(Calendar.MINUTE);


            //pobiera date i czas z datapickera


            c.set(Calendar.DAY_OF_MONTH, dataDzien);
            c.set(Calendar.MONTH, dataMiesiac);
            c.set(Calendar.YEAR, dataRok);
            c.set(Calendar.HOUR_OF_DAY, timegodziny);
            c.set(Calendar.MINUTE, timeminuty);

/*
        c.set(Calendar.DAY_OF_MONTH,day);
        c.set(Calendar.MONTH,month);
        c.set(Calendar.YEAR,year);
        c.set(Calendar.HOUR_OF_DAY, h);
        c.set(Calendar.MINUTE,m+6);
*/

            //formatuje dane na format daty do toasta
            Date dupa2 = c.getTime();
            SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MM-yyyy");
            String date2 = sdf2.format(dupa2);

            Date dupa3 = c.getTime();
            SimpleDateFormat sdf3 = new SimpleDateFormat("h:mm a");
            String date3 = sdf3.format(dupa3);

//wiadomosc o biciu
            terminNastepnegoBiciaSTR = "Następne bicie _, " + " " + date2 + ",  " + date3;

            preferences = PreferenceManager.getDefaultSharedPreferences(context5);
            SharedPreferences.Editor editorr = preferences.edit();
            editorr.putString("info5", terminNastepnegoBiciaSTR);
            editorr.apply();

// wiadomosc toast

            //  Toast.makeText(context5, " Potwierdzono zakłuty poślad !!!   Następne bicie T5  " + date2 + " " + date3, Toast.LENGTH_LONG).show();


            //tworzy intencje zbudowaną w osobnej class'ie

            Intent intentPiec = new Intent(context5, srodek_piec_Notyfication_reciver.class);
            pendingIntentPiec = PendingIntent.getBroadcast(context5, 500, intentPiec, PendingIntent.FLAG_UPDATE_CURRENT);

//alarm notyfikacji z interwałem dziennym razy okres  ( w nowej wersji juz bez interwału, tylko alarm )

            alarmManagerPiec = (AlarmManager) context5.getSystemService(Context.ALARM_SERVICE);
            alarmManagerPiec.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntentPiec);
        }
    }


}
