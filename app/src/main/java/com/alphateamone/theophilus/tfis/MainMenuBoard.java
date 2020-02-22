package com.alphateamone.theophilus.tfis;

import android.animation.ArgbEvaluator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.alphateamone.theophilus.tfis.Activities.AccountDetailActivity;
import com.alphateamone.theophilus.tfis.Activities.Accounts;
import com.alphateamone.theophilus.tfis.Activities.CreateAccount;
import com.alphateamone.theophilus.tfis.Activities.History;
import com.alphateamone.theophilus.tfis.Activities.Profile;
import com.alphateamone.theophilus.tfis.Activities.Settings;
import com.alphateamone.theophilus.tfis.Adapters.CarouselAdapter;
import com.alphateamone.theophilus.tfis.Adapters.ImageSliderRecyclerView;
import com.alphateamone.theophilus.tfis.Adapters.MainDrawerListAdapter;
import com.alphateamone.theophilus.tfis.DialogBox.MyLoadingAlertDialogFrag;
import com.alphateamone.theophilus.tfis.Model.ImgCarousel;
import com.alphateamone.theophilus.tfis.Model.Users;
import com.alphateamone.theophilus.tfis.Network.MyVolleySingleton;
import com.alphateamone.theophilus.tfis.Utils.Helpers;
import com.alphateamone.theophilus.tfis.Utils.L;
import com.alphateamone.theophilus.tfis.Utils.NavItems;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import com.alphateamone.theophilus.tfis.Activities.CheckBalance;
//import com.alphateamone.theophilus.tfis.Activities.PayNow;

public class MainMenuBoard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    ListView mDrawerList;
    ArrayList<NavItems> navItems;

    ViewPager viewPager;
    ArrayList<ImgCarousel> imgCarousels;
    CarouselAdapter carouselAdapter;
    ArgbEvaluator evaluator = new ArgbEvaluator();

    private static final int REQUEST_CODE_SCANCODE_KEY      = 3;
    private static final int REQUEST_CODE_CHECK_BALANCE_KEY = 4;
    private Button mPayNow, vTransfer, scanQr, mCreateAccount, mCheckBal;
    Typeface mTypeface, mTypefaceBlack, mTypefaceBold, mTypefaceRegular;
    private MyLoadingAlertDialogFrag myLoadingAlertDialogFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu_board);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle(R.string.app_name);

//        FloatingActionButton fab    = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        //Set the custome sidebar view for the Navigation view here
        navItems    = new ArrayList<>();
        navItems.add(new NavItems("Dashboard", "CheckList for the application", R.drawable.ic_new_email_outline));
        navItems.add(new NavItems("Accounts", "Account of Students", R.drawable.ic_account));
        navItems.add(new NavItems("History Activity", "Get Log of Attendance", R.drawable.ic_lock));

        navItems.add(new NavItems("Profile", "Active Account User Profile", R.drawable.ic_profile));
        navItems.add(new NavItems("Settings", "Change Password/Username", R.drawable.ic_settings));
        navItems.add(new NavItems("Sign Out", "Close Application", R.drawable.ic_power_button));

        DrawerLayout drawer             = findViewById(R.id.drawer_layout);
        mDrawerList                     = findViewById(R.id.navList);
        MainDrawerListAdapter mDrawAdapt= new MainDrawerListAdapter(this, navItems);
        mDrawerList.setAdapter(mDrawAdapt);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        finishStartIntent(MainMenuBoard.class);
                        break;
                    case 1:
                        startIntentTask(Accounts.class);
                        break;
                    case 2:
                        startIntentTask(History.class);
                        break;
                    case 3:
                        startIntentTask(Profile.class);
                        break;
                    case 4:
                        startIntentTask(Settings.class);
                        break;
                    case 5:
                        finishStartIntent(MainActivity.class);
                        break;
                }
            }
        });
        ActionBarDrawerToggle toggle    = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView   = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Set the button typeface
        setButtonTypefaces();

        //Call the setButtonRow for the customTypeface call and OnclickListener
        setTaskButtonRow();

        //Set RecyclerViewAdapter Horizontal for carousel scrolling
        setRecyclerImgSliderAdapter();
    }

    private void setRecyclerImgSliderAdapter(){
        imgCarousels    = new ArrayList<>();
        imgCarousels.add(new ImgCarousel(R.drawable.man));
        imgCarousels.add(new ImgCarousel(R.drawable.img1));
        imgCarousels.add(new ImgCarousel(R.drawable.img2));
        imgCarousels.add(new ImgCarousel(R.drawable.avatar));
        imgCarousels.add(new ImgCarousel(R.drawable.img3));
        imgCarousels.add(new ImgCarousel(R.drawable.img4));
        imgCarousels.add(new ImgCarousel(R.drawable.avatar));
        imgCarousels.add(new ImgCarousel(R.drawable.img2));
        imgCarousels.add(new ImgCarousel(R.drawable.img1));
        imgCarousels.add(new ImgCarousel(R.drawable.img3));
        imgCarousels.add(new ImgCarousel(R.drawable.img4));

        RecyclerView recyclerView                       = findViewById(R.id.viewPagerCarousel);
        ImageSliderRecyclerView imageSliderRecyclerView = new ImageSliderRecyclerView(this, imgCarousels);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(imageSliderRecyclerView);
    }

    private void setViewPagerAdapter(){
        imgCarousels    = new ArrayList<>();
        imgCarousels.add(new ImgCarousel(R.drawable.man));
        imgCarousels.add(new ImgCarousel(R.drawable.avatar));

        carouselAdapter = new CarouselAdapter(imgCarousels, this);
        viewPager       = findViewById(R.id.viewPagerCarousel);
        viewPager.setAdapter(carouselAdapter);
        //viewPager.setPadding(0,0,0, 0);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
                if(i < (carouselAdapter.getCount() - 1)){

                }
            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    private void startIntentTask(Class className){
        Intent intent   = new Intent(getApplicationContext(), className);
        startActivity(intent);
    }

    private String setUserNameRow(){
        SharedPreferences preferences   = getSharedPreferences("MyDataTfis", MODE_PRIVATE);
        return preferences.getString("fullname", "");
    }

    private void setButtonTypefaces(){
        Typeface mTypefaceHeavy = Typeface.createFromAsset(getAssets(), "fonts/FuturaBold.ttf");
        mTypefaceBlack  = Typeface.createFromAsset(getAssets(), "fonts/ProximaNova-Black.ttf");
        mTypefaceBold   = Typeface.createFromAsset(getAssets(), "fonts/FuturaMediumBt.ttf");
        mTypefaceRegular= Typeface.createFromAsset(getAssets(), "fonts/FuturaBookFont.ttf");
        TextView textReceiver   = findViewById(R.id.textReceiver);
        TextView textPhoneGap   = findViewById(R.id.phoneTag);
        TextView textNumber1    = findViewById(R.id.tv_appname);
        TextView textNumber2    = findViewById(R.id.tv_appname2);
        TextView titleDashboard = findViewById(R.id.titleDashboard);
        TextView markedPupils   = findViewById(R.id.markedPupils);
        TextView unmarkedPupils = findViewById(R.id.unmarkedPupil);

//        Button guardians        = findViewById(R.id.guardians);

        scanQr                  = findViewById(R.id.qrcodeScan);
        //mCreateAccount          = findViewById(R.id.createUser);
        vTransfer               = findViewById(R.id.openUserAccount);

        titleDashboard.setText(setUserNameRow());
        titleDashboard.setTypeface(mTypefaceBold);
        //textPhoneGap.setTypeface(mTypefaceRegular);
        //textReceiver.setTypeface(mTypefaceHeavy);

        markedPupils.setTypeface(mTypefaceRegular);
        unmarkedPupils.setTypeface(mTypefaceRegular);

        //mCreateAccount.setTypeface(mTypefaceBlack);
        scanQr.setTypeface(mTypefaceBlack);
        //vTransfer.setTypeface(mTypefaceBlack);
//        guardians.setTypeface(mTypefaceBlack);

        textNumber1.setTypeface(mTypefaceBlack);
        textNumber2.setTypeface(mTypefaceBlack);
    }

    private void finishStartIntent(Class className){
        Intent intent   = new Intent(getApplicationContext(), className);
        startActivity(intent);
        finish();
    }

    private void setTaskButtonRow(){
        //scanQr          = findViewById(R.id.qrcodeScan);

        //Set the custom Typeface to each Button
        scanQr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takeKeyToScanCodeActivity();
            }
        });
    }

    //OnActivityResult test whether this method triggered the call
    private void takeKeyToScanCodeActivity() {
        Intent intent   = new Intent(getApplicationContext(), ScanCodeActivity.class);
        startActivityForResult(intent, REQUEST_CODE_SCANCODE_KEY);
    }

    //OnActivityResult test whether this method triggered the call
    private void takeKeyToCheckBalActivity() {
        Intent intent   = new Intent(getApplicationContext(), ScRow.class);
        startActivityForResult(intent, REQUEST_CODE_CHECK_BALANCE_KEY);
    }

    private void takeKeyToCreateActivity() {
        Intent intent   = new Intent(getApplicationContext(), CreateAccount.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        assert data != null;
        final String nData    = data.getStringExtra("keyQrCode");
        if(requestCode == REQUEST_CODE_SCANCODE_KEY && resultCode == RESULT_OK){
            L.l(getApplicationContext(), "Scan Code results" + nData);
            myLoadingAlertDialogFrag    = new MyLoadingAlertDialogFrag();
            myLoadingAlertDialogFrag.show(getSupportFragmentManager(), "MyLoadingAlertBox");
            myLoadingAlertDialogFrag.callAlertLoadingTaskCallback(new MyLoadingAlertDialogFrag.AlertLoadingTaskCallback() {
                @Override
                public void CallbackTask(final MyLoadingAlertDialogFrag myLoadingAlertDialogFrag) {
                    new Handler(getMainLooper()).postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            myLoadingAlertDialogFrag.dismiss();
                            Intent intent   = new Intent(getApplicationContext(), AccountDetailActivity.class);
                            startActivity(intent);
                        }
                    }, 3000);
                }
            });
        }
    }

    private void checkBalanceRequestAction(final String walletcode, final VolleyCheckBalanceActionListener balanceActionListener){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Helpers.URL_STRING + "/balance", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject   = new JSONObject(response);
                    if(!jsonObject.getString("status").equals("OK")){
                        throw new JSONException(jsonObject.getString("message"));
                    }
                    balanceActionListener.onSuccess(parseJson(jsonObject));
                } catch (JSONException e) {
                    balanceActionListener.onFailure("JSON ERROR:"+e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                balanceActionListener.onFailure("V-Error:"+error);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params  = new HashMap<>();
                params.put("apiKey", Helpers.API_KEY);
                params.put("apiId", Helpers.API_ID);
                params.put("wallet", walletcode);
                return params;
            }
        };
        MyVolleySingleton.getInstance(this).addToRequestQueue(stringRequest, "checkBalTag");
    }

    private Users parseJson(JSONObject jsonObject) throws JSONException {
        Users users = new Users();
        JSONObject getData  = jsonObject.getJSONObject("data");
        if(getData.has("register_id") && !getData.getString("register_id").isEmpty()){
            users.setFirstname(getData.getString("fullname"));
            users.setRegisterId(getData.getString("register_id"));
            users.setCashBalance(getData.getString("cashbalance"));
            users.setDateCreated(getData.getString("todays_date"));
            users.setExpiryDate(getData.getString("expiry_date"));
            users.setUserState(getData.getString("user_state"));
        }
        return users;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu_board, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    interface VolleyCheckBalanceActionListener{
        void onSuccess(Users users);
        void onFailure(String message);
    }
}
