package com.example.mytablayout;

import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DashboardFragment extends Fragment {
    private static final String TAG = "DashboardFragment";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);

        return root;
    }

    private Bitmap getBilinearBitmap(Bitmap bitmap, int desWidth, int desHeight) {
        if ((bitmap == null) || ((desWidth <= 0) || (desHeight <= 0))) {
            return null;
        }
        int sourceWidth = bitmap.getWidth();
        int sourceHeight = bitmap.getHeight();
        Log.d(TAG, "desWidth = " + desWidth + " - " + desHeight + " - sourceWidth = " + sourceWidth + " - " + sourceHeight);
        float sampleRate = 1f;
        if ((sourceHeight > sourceWidth) && (sourceHeight > desHeight)) { // 高度较大，以高度为基准设置压缩比例
            sampleRate = (((float) (sourceHeight * 100)) / desHeight) / 100;
        } else if ((sourceWidth > sourceHeight) && (sourceWidth > desWidth)) { // 宽度较大，以宽度为基准设置压缩比例
            sampleRate = (((float) (sourceWidth * 100)) / desWidth) / 100;
        }
        Log.d(TAG, "sampleRate = " + sampleRate);
        int finalWidth = (int) (sourceWidth / sampleRate);
        int finalHeight = (int) (sourceHeight / sampleRate);
        Log.d(TAG, "finalWidth = " + finalWidth + " - finalHeight = " + finalHeight);
        return Bitmap.createScaledBitmap(bitmap, finalWidth, finalHeight, true);
    }

    private Bitmap sampleBySizeFromUri(Context context, Uri sourceUri, int targetWidth, int targetHeight) {
        if ((context == null) || (sourceUri == null)) {
            return null;
        }
        try {
            InputStream inputStream = context.getContentResolver().openInputStream(sourceUri); // 打开输入流
            BitmapFactory.Options options = new BitmapFactory.Options(); //用于计算原图尺寸
            options.inJustDecodeBounds = true; // 不加载进内存
            options.inDither = true;
            options.inPreferredConfig = Bitmap.Config.ARGB_8888; // 色深，（32位，4个字节）
            BitmapFactory.decodeStream(inputStream, null, options);
            try {
                inputStream.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
                Log.d(TAG, "ioException = " + ioException.getMessage());
            }

            int originalWidth = options.outWidth; // 原始宽度
            int originHeight = options.outHeight; //原始高度
            Log.d(TAG, "originalWidth = " + originalWidth + " - originHeight = " + originHeight
                    + " - targetWidth = " + targetWidth + " - targetHeight = " + targetHeight);
            if (((originalWidth <= 0) || (originHeight <= 0)) || ((targetWidth <= 0) || (targetHeight <= 0))) {
                return null;
            }
            int inSampleSize = 1; // 采样率
            if ((originalWidth > originHeight) && (originalWidth > targetWidth)) { // 原图宽度较大，以宽度为基准进行采样计算
                inSampleSize = originalWidth / targetWidth;
            } else if ((originHeight >= originalWidth) && (originHeight > targetHeight)) { // 原图高度较大，以高度作为基准进行采样计算
                inSampleSize = originHeight / targetHeight;
            }
//            //TODO
//            inSampleSize = 1;
            Log.d(TAG, "inSampleSize = " + inSampleSize);
            BitmapFactory.Options inputOption = new BitmapFactory.Options();
            inputOption.inSampleSize = inSampleSize;
            inputOption.inDither = true;
            inputOption.inPreferredConfig = Bitmap.Config.ARGB_8888;

            InputStream stream = context.getContentResolver().openInputStream(sourceUri);
            Bitmap targetBitmap = BitmapFactory.decodeStream(stream, null, inputOption);
            try {
                stream.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            if (targetBitmap != null) {
                Log.d(TAG, "targetBitmap = " + (targetBitmap == null) + "w = " + targetBitmap.getWidth() + " - " + targetBitmap.getHeight());
            }
            return targetBitmap;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.d(TAG, "e = " + e.getMessage());
        }
        return null;
    }


    private Uri writeBitmap(Context context, Bitmap bitmap, String name, int targetSizeKb) {
        if ((bitmap == null) || (context == null)) {
            Log.d("AlbumActivity", "bitmap null");
            return null;
        }
        Log.d(TAG, "targetSizeKb = " + targetSizeKb);
        if (targetSizeKb < 10) {
            targetSizeKb = 10;
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.Images.Media.DISPLAY_NAME, name + "___" + System.currentTimeMillis() + ".jpeg");
        contentValues.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        Uri uri = context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
        if (uri == null) {
            Log.d("AlbumActivity", "uri null");
            return null;
        }
        ByteArrayOutputStream outputArray = new ByteArrayOutputStream();
        int quality = 100;
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputArray);
        Log.d(TAG, "length = " + outputArray.toByteArray().length);
        while (outputArray.toByteArray().length / 1024 > targetSizeKb) {
            outputArray.reset();
            quality -= 5;
            Log.d(TAG, "quality = " + quality);
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputArray);
            Log.d(TAG, "length = " + outputArray.toByteArray().length);
        }
        try {
            OutputStream outputStream = context.getContentResolver().openOutputStream(uri);
            outputStream.write(outputArray.toByteArray());
            outputArray.reset();
            outputArray.close();
            outputArray.flush();
            outputStream.flush();
            outputStream.close();
            Log.d("AlbumActivity", "success !");
            return uri;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return uri;
    }
}