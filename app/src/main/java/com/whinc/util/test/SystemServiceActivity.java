package com.whinc.util.test;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.whinc.util.SystemServiceUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnLongClick;

public class SystemServiceActivity extends AppCompatActivity {

    @Bind(R.id.rootLayout)
    CoordinatorLayout mRootLayout;
    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_service);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_system_service, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_schedule_start_activity) {
            testScheduleStartActivity();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showSnackbar(String text) {
        Snackbar.make(mRootLayout, text, Snackbar.LENGTH_SHORT).show();
    }

    @OnLongClick(R.id.copy_edtTxt)
    protected boolean testCopyToClipboard(TextView view) {
        CharSequence text = view.getText();
        if (TextUtils.isEmpty(text)) {
            showSnackbar("Empty text!");
            return true;
        }

        SystemServiceUtils.copyToClipboard(this, text);
        showSnackbar("has copied \"" + text + "\" to clipboard");
        return true;
    }

    private void testScheduleStartActivity() {
        SystemServiceUtils.scheduleStartActivity(this, 2000, MainActivity.class);
    }
}
