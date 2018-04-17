package affan.id.qwikfund.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import affan.id.qwikfund.R;

/**
 * Created by Herfanda on 9/24/2017 AD.
 */

public class BorrowerLogin extends Activity {

    private Button btnLoginAfterAktivasi;
    private Button btnLogin;
    private ImageButton btnShowHideLoginForm;
    private boolean viewGroupIsVisible = true;
    private View relativeViewGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.borrower_login_layout_after_aktivasi);

        initLayout();
        initEvent();
    }

    private void initLayout(){
        btnLoginAfterAktivasi = (Button) findViewById(R.id.btn_login_after_aktivasi);

        /*btnLogin = (Button) findViewById(R.id.btn_login);
        relativeViewGroup = findViewById(R.id.form_login_user);
        btnShowHideLoginForm = (ImageButton) findViewById(R.id.user_profile_photo);*/
    }

    private void initEvent(){


    }
}
