package pl.fitandyummy.ilebije;

import android.content.Intent;
import android.graphics.Typeface;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tpay.android.library.web.TpayActivity;
import com.tpay.android.library.web.TpayPayment;

public class FajnaApka extends AppCompatActivity {

    private TpayPayment.Builder paymentBulider = null;

    TextView dorzucam, dzikow, dzik, mocarnoscTxt;
    Button pomozkoledze;
    EditText kwota;
    String kwotaString;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fajna_apka);
        setupPayment(savedInstanceState);
        ImageView back = (ImageView) findViewById(R.id.toolbarArrowbackBtn);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent doMainIntent = new Intent(getApplicationContext(), PreMainActivity.class);
                startActivity(doMainIntent);
            }
        });

        mocarnoscTxt = (TextView) findViewById(R.id.textMocarnosc);
        dorzucam = (TextView) findViewById(R.id.DORZUCAM);
        dzikow = (TextView) findViewById(R.id.dzikow);
        dzik = (TextView) findViewById(R.id.dzik);
        kwota = (EditText) findViewById(R.id.kwota);
        pomozkoledze = (Button) findViewById(R.id.pomozkoledze);

        Typeface text1 = Typeface.createFromAsset(getAssets(), "fonts/KO.ttf");
        Typeface text2 = Typeface.createFromAsset(getAssets(), "fonts/SO.ttf");

        mocarnoscTxt.setTypeface(text1);
        dorzucam.setTypeface(text1);
        dzikow.setTypeface(text1);
        dzik.setTypeface(text2);
        kwota.setTypeface(text1);
        pomozkoledze.setTypeface(text1);

        kwotaString = kwota.getText().toString();

        pomozkoledze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setupPayment(savedInstanceState);

                final Intent payIntent = new Intent(FajnaApka.this, TpayActivity.class);
                final TpayPayment tpayPayment = paymentBulider.create();
                payIntent.putExtra(TpayActivity.EXTRA_TPAY_PAYMENT, tpayPayment);
                startActivityForResult(payIntent, TpayActivity.TPAY_PAYMENT_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TpayActivity.TPAY_PAYMENT_REQUEST:
                if (resultCode == RESULT_OK) {
                    Toast.makeText(getApplicationContext(), "Niech najlepsza jakościowo masa będzie zawsze z Tobą :D ", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Płatność nie została wykonana poprawnie", Toast.LENGTH_SHORT).show();
                }
            default:
                super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void setupPayment(Bundle savedInstanceState) {
        EditText kwota = (EditText) findViewById(R.id.kwota);
        String kwotaString = kwota.getText().toString();

        if (savedInstanceState == null) {
            paymentBulider = new TpayPayment.Builder()
                    .setId("30944")
                    .setAmount(kwotaString)
                    .setCrc("ilebije")
                    .setSecurityCode("51dR59Lz5ARM9MCL")
                    .setDescription("za przyrosty :D ")
                    .setClientEmail("podaj adres mail")
                    .setClientName("podaj imie i nazwisko");

        } else {
            paymentBulider = savedInstanceState.getParcelable(TpayActivity.EXTRA_TPAY_PAYMENT);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(TpayActivity.EXTRA_TPAY_PAYMENT, paymentBulider);
    }
}