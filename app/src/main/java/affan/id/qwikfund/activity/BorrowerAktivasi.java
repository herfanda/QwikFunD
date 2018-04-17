package affan.id.qwikfund.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import affan.id.qwikfund.R;
import affan.id.qwikfund.fragment.BorrowerAktivasiKonfirmasi;


/**
 * Created by yunus on 9/5/17.
 */

public class BorrowerAktivasi extends Activity {
    private Animation animShowHideBtn;
    private Button btnDob;
    private Button btnDebitCard;
    private Button btnCreditCard;
    private android.app.Fragment fragment;
    private Activity activity;

    private BorrowerAktivasiKonfirmasi borrowerAktivasiKonfirmasi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.borrower_aktivasi);
        initLayout();
        initEvent();
    }

    private void initLayout() {
        btnDob          = (Button) findViewById(R.id.btnDob);
        btnDebitCard    = (Button) findViewById(R.id.btnDebitCard);
        btnCreditCard   = (Button) findViewById(R.id.btnCreaditCard);
        animShowHideBtn = AnimationUtils.loadAnimation(this, R.anim.showhidepress);
    }

    private void initEvent() {
        btnDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(animShowHideBtn);
                startActivity(new Intent(BorrowerAktivasi.this,BorrowerAktivasiKonfirmasi.class));

            }
        });

        btnDebitCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(animShowHideBtn);
                startActivity(new Intent(BorrowerAktivasi.this,BorrowerAktivasiKonfirmasi.class));

            }
        });

        btnCreditCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(animShowHideBtn);
                startActivity(new Intent(BorrowerAktivasi.this,BorrowerAktivasiKonfirmasi.class));
            }
        });

    }

}
