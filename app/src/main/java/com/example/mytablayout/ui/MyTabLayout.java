package com.example.mytablayout.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

/*******
 * created by Ethan Lee
 * on 2021/2/12
 *******/
public class MyTabLayout extends LinearLayout {
    private List<ColorTrackTextView> colorText;
    private Context mContext;
    private int textSize = 58;

    public MyTabLayout(Context context) {
        this(context , null);
    }

    public MyTabLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs , 0);
    }

    public MyTabLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(HORIZONTAL);
        mContext = context;
        colorText = new ArrayList<>();
    }

    public void initRes(String[] tab){
        colorText.clear();
        for (int i = 0 ; i < tab.length ; i ++){
            LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT ,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            params.weight = 1;
            ColorTrackTextView colorTrackTextView = new ColorTrackTextView(mContext);
            colorTrackTextView.setText(tab[i]);
            colorTrackTextView.setTextSize(textSize);
            colorTrackTextView.setLayoutParams(params);
            addView(colorTrackTextView);
            colorText.add(colorTrackTextView);
        }
    }
    public List<ColorTrackTextView> getTabChildren(){
        return colorText;
    }

    public void setTabTextSize(int size){
        textSize = size;
        for (int i = 0 ; i < colorText.size() ; i ++){
            colorText.get(i).setTextSize(textSize);
        }
    }
    
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }
}
