package demo.example.zj.networkchangechecker;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.renderscript.Sampler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements View.OnClickListener{
    private final String TAG = "NetworkDemo";
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private final String SPNAME = "network";
    private Button reset;
    private TextView tv_total;
    private TextView tv_mobile;
    private TextView tv_wifi;
    private TextView[] allTextView;
    private String[] allType = {"MOBILE", "WIFI"};

    private SharedPreferences.OnSharedPreferenceChangeListener mListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            Log.e(TAG, "sharedpreference changed key is " + key  + " ---count = " + sharedPreferences.getInt(key, 0));
            if (key.equals("total")) {
                Log.e(TAG, "--- UPDATE TOTAL");
                tv_total.setText("TOTAL : " + sharedPreferences.getInt("total", 0));
            } else {
                Log.e(TAG, "UPDATE TYPE");
                int type = Integer.parseInt(key.trim());
                switch (type) {
                    case ConnectivityManager.TYPE_MOBILE:
                        tv_mobile.setText("TYPE_MOBILE : " + sharedPreferences.getInt(key, 0));
                        break;
                    case ConnectivityManager.TYPE_WIFI:
                        tv_wifi.setText("TYPE_WIFI : " + sharedPreferences.getInt(key, 0));
                        break;
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSharedPreferences = getSharedPreferences(SPNAME, Context.MODE_PRIVATE);
        initView();
    }

    private void initView() {
        reset = (Button) findViewById(R.id.reset);
        reset.setOnClickListener(this);
        tv_mobile = (TextView) findViewById(R.id.tv_mobile);
        tv_wifi = (TextView) findViewById(R.id.tv_wifi);
        tv_total = (TextView) findViewById(R.id.tv_total);
        allTextView = new TextView[]{tv_mobile, tv_wifi};
    }

    @Override
    protected void onStart() {
        super.onStart();
        mSharedPreferences.registerOnSharedPreferenceChangeListener(mListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        reflashAllView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSharedPreferences.unregisterOnSharedPreferenceChangeListener(mListener);
    }

    @Override
    public void onClick(View v) {

        Log.e(TAG, "reset click");
        if (v.getId() == R.id.reset) {
            getEditor().clear().commit();
        }
        reflashAllView();
    }

    private SharedPreferences.Editor getEditor() {
        if (mSharedPreferences == null) {
            mSharedPreferences = getSharedPreferences(SPNAME, Context.MODE_PRIVATE);
        }
        return mSharedPreferences.edit();
    }

    private void reflashAllView() {
        tv_total.setText("TOTAL : " + mSharedPreferences.getInt("total", 0));
        for(int i = 0; i < allTextView.length; i++) {
            allTextView[i].setText("TYPE_" + allType[i] + " : " + mSharedPreferences.getInt(i + "", 0));
        }
    }
}
