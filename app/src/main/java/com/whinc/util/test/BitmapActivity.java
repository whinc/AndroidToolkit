package com.whinc.util.test;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.whinc.util.BitmapUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

public class BitmapActivity extends AppCompatActivity {
    @Bind(R.id.source_iv)
    ImageView mSourceIv;
    @Bind(R.id.target_iv)
    ImageView mTargetIv;
    @Bind(R.id.target2_iv)
    ImageView mTarget2Iv;
    @Bind(R.id.toolbar)
    Toolbar mToolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitmap);
        ButterKnife.bind(this);
        setSupportActionBar(mToolBar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_bitmap, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        Bitmap src = BitmapFactory.decodeResource(getResources(), R.drawable.img1);
        Bitmap src2 = BitmapFactory.decodeResource(getResources(), R.drawable.h_img);
        ViewGroup.LayoutParams lp = mTargetIv.getLayoutParams();
        float ratioWH = 1.0f * lp.width / lp.height;
        ViewGroup.LayoutParams lp2 = mTarget2Iv.getLayoutParams();
        float ratioWH2 = 1.0f * lp2.width / lp2.height;
        Bitmap dst = null;
        Bitmap dst2 = null;
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_crop_center) {
            dst = BitmapUtils.crop(src, ratioWH, BitmapUtils.CROP_CENTER);
            dst2 = BitmapUtils.crop(src2, ratioWH2, BitmapUtils.CROP_CENTER);
        } else if (id == R.id.action_crop_left_top) {
            dst = BitmapUtils.crop(src, ratioWH, BitmapUtils.CROP_LEFT | BitmapUtils.CROP_TOP);
            dst2 = BitmapUtils.crop(src2, ratioWH2, BitmapUtils.CROP_LEFT | BitmapUtils.CROP_TOP);
        } else if (id == R.id.action_crop_right_bottom) {
            dst = BitmapUtils.crop(src, ratioWH, BitmapUtils.CROP_RIGHT | BitmapUtils.CROP_BOTTOM);
            dst2 = BitmapUtils.crop(src2, ratioWH2, BitmapUtils.CROP_RIGHT | BitmapUtils.CROP_BOTTOM);
        }
        mTargetIv.setImageBitmap(dst);
        mTarget2Iv.setImageBitmap(dst2);

        src.recycle();
        src = null;
        src2.recycle();
        src2 = null;

        return true;
    }
}
