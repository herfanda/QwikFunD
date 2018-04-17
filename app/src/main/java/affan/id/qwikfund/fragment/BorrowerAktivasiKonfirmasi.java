package affan.id.qwikfund.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import affan.id.qwikfund.R;
import affan.id.qwikfund.activity.BorrowerLogin;

/**
 * Created by Herfanda on 9/24/2017 AD.
 */

public class BorrowerAktivasiKonfirmasi extends Activity {
    private Button btnAgree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.borrower_aktivasi_konfirmasi);

        initLayout();
        initEvent();
    }

    private void initLayout(){
        btnAgree = (Button) findViewById(R.id.btnSetuju);

    }

    private void initEvent(){

        btnAgree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BorrowerAktivasiKonfirmasi.this, BorrowerLogin.class));
            }
        });

    }
}
