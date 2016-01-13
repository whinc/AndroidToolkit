package com.whinc.util;

import android.graphics.Bitmap;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;

/**
 * Created by Administrator on 2015/11/6.
 */
public class BitmapUtils {
    private static final String TAG = BitmapUtils.class.getSimpleName();

    // crop type enum define
    public static final int CROP_CENTER = 0x00000001;
    public static final int CROP_LEFT   = 0x00000010;
    public static final int CROP_RIGHT  = 0x00000100;
    public static final int CROP_TOP    = 0x00001000;
    public static final int CROP_BOTTOM = 0x00010000;
    @IntDef(flag = true, value = {CROP_CENTER, CROP_LEFT, CROP_RIGHT, CROP_TOP, CROP_BOTTOM})
    public @interface CropType {}

    /**
     * Crop bitmap
     * @param src source bitmap
     * @param ratioWH the ratio of target bitmap width and height
     * @param cropType crop type, reference to {@link com.whinc.util.BitmapUtils.CropType}
     * @return return cropped bitmap
     */
    public static Bitmap crop(@NonNull Bitmap src, float ratioWH, @CropType int cropType) {
        int srcW = src.getWidth();
        int srcH = src.getHeight();
        float x, y, w, h;

        if (srcW > srcH) {      // fix height and scale width
            h = srcH;
            w = h * ratioWH;

            if (w <= srcW) {
                y = 0;
                if ((cropType & CROP_LEFT) != 0) {
                    x = 0;
                } else if ((cropType & CROP_RIGHT) != 0) {
                    x = srcW - w;
                } else {
                    x = (srcW - w) /2;
                }
            } else {
                h = srcW / w * h;
                w = srcW;
                x = 0;
                if ((cropType & CROP_TOP) != 0) {
                    y = 0;
                } else if ((cropType & CROP_BOTTOM) != 0) {
                    y = srcH - h;
                } else {        // crop center default
                    y = (srcH - h) / 2;
                }
            }
        } else {                // fix width and scale height
            w = srcW;
            h = w / ratioWH;

            if (h <= srcH) {
                x = 0;
                if ((cropType & CROP_TOP) != 0) {
                    y = 0;
                } else if ((cropType & CROP_BOTTOM) != 0) {
                    y = srcH - h;
                } else {
                    y = (srcH - h) / 2;
                }
            } else {
                w = srcH / h * w;
                h = srcH;
                y = 0;
                if ((cropType & CROP_LEFT) != 0) {
                    x = 0;
                } else if ((cropType & CROP_RIGHT) != 0) {
                    x = srcW - w;
                } else {        // crop center default
                    x = (srcW - w) / 2;
                }
            }
        }

        Bitmap dst = Bitmap.createBitmap(src, (int)x, (int)y, (int)w, (int)h);
        return dst;
    }
}
