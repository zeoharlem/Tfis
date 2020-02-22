package com.alphateamone.theophilus.tfis.Activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alphateamone.theophilus.tfis.MainMenuBoard;
import com.alphateamone.theophilus.tfis.R;

import cdflynn.android.library.checkview.CheckView;
import de.hdodenhof.circleimageview.CircleImageView;

public class AccountDetailActivity extends AppCompatActivity {

    Typeface mTypeface, mTypefaceBlack, mTypefaceBold, mTypefaceRegular;
    private CheckView checkViewMarker;
    private CircleImageView circleImageView;
    private LinearLayout pupilData;
    private Button confirmUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_detail);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setButtonTypefaces();
    }

    private void setButtonTypefaces(){
        mTypefaceBlack  = Typeface.createFromAsset(getAssets(), "fonts/ProximaNova-Black.ttf");
        mTypefaceBold   = Typeface.createFromAsset(getAssets(), "fonts/FuturaMediumBt.ttf");
        mTypefaceRegular= Typeface.createFromAsset(getAssets(), "fonts/FuturaBookFont.ttf");

        TextView fullname       = findViewById(R.id.fullname);
        TextView classGrade     = findViewById(R.id.classgrade);
        TextView guardian       = findViewById(R.id.guardian);

        circleImageView         = findViewById(R.id.imageCircleView);
        pupilData               = findViewById(R.id.pupilDetail);

        checkViewMarker         = findViewById(R.id.checkMarker);

        confirmUser             = findViewById(R.id.confirmUser);

        fullname.setTypeface(mTypefaceBold);
        classGrade.setTypeface(mTypefaceRegular);

        guardian.setTypeface(mTypefaceBlack);

        confirmUser.setTypeface(mTypefaceBlack);
        confirmUser.setOnClickListener(onClickListener);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            Intent mainMenuBoard    = new Intent(this, MainMenuBoard.class);
            mainMenuBoard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP  | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(mainMenuBoard);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    View.OnClickListener onClickListener    = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            confirmUser.setVisibility(View.GONE);
            circleImageView.setVisibility(View.GONE);
            pupilData.setVisibility(View.GONE);

            checkViewMarker.check();
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent   = new Intent(getApplicationContext(), MainMenuBoard.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP  | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                }
            }, 2000);

        }
    };
}
