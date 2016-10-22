package client.ediancha.com.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import client.ediancha.com.R;
import client.ediancha.com.fragment.TeaProductDescFragment;

public class TeaDescActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_center);
        getSupportFragmentManager().beginTransaction().add(R.id.fg_content, new TeaProductDescFragment()).commit();
    }
}
