package com.example.mytablayout.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.example.mytablayout.R;

import java.util.Objects;


/*******
 * 字体变色的TextView
 *
 * created by Ethan Lee
 * on 2021/1/17
 *******/
public class ColorTrackTextView extends View {
    private static final String TAG = "ColorTrackTextView";
    private int mOriginColor = Color.BLACK;
    private int mChangeColor = Color.RED;
    private Paint textPaint, changeColorTextPaint;
    private Rect textBound;
    private float changePercent = 0f;
    private boolean changeLeft = true;
    private String mText = "";
    private int mTextSize = 58;

    public ColorTrackTextView(Context context) {
        this(context, null);
    }

    public ColorTrackTextView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ColorTrackTextView(Context context, AttributeSet attributeSet, int def) {
        super(context, attributeSet, def);
        initRes(context, attributeSet, def);
    }

    private void initRes(Context context, AttributeSet attributeSet, int def) {
        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.ColorTrackTextView);
        mOriginColor = typedArray.getColor(R.styleable.ColorTrackTextView_origin_color, mOriginColor);
        mChangeColor = typedArray.getColor(R.styleable.ColorTrackTextView_change_color, mChangeColor);
        textPaint = new Paint();
        textPaint.setColor(mOriginColor);
        textPaint.setTextSize(getTextSize());
        textPaint.setAntiAlias(true);
        textPaint.setDither(true);
        changeColorTextPaint = new Paint();
        changeColorTextPaint.setTextSize(getTextSize());
        changeColorTextPaint.setColor(mChangeColor);
        changeColorTextPaint.setAntiAlias(true);
        changeColorTextPaint.setDither(true);
        textBound = new Rect();
        typedArray.recycle();
    }

    public void changeLeftColor(float percent) {
        this.changeLeft = true;
        changePercent(percent);
    }

    public void changeRightColor(float percent) {
        this.changeLeft = false;
        changePercent(percent);
    }

    private void changePercent(float percent) {
        if (percent < 0f) {
            this.changePercent = 0f;
        } else if (percent > 1f) {
            this.changePercent = 1f;
        } else {
            this.changePercent = percent;
        }
        invalidate();
    }

    private Rect getTextRect() {
        if (textPaint != null) {
            textPaint.getTextBounds(getText().toString(), 0, getText().toString().length(), textBound);
            return textBound;
        } else {
            return null;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (widthMode == MeasureSpec.AT_MOST) {
            textPaint.getTextBounds(getText().toString(), 0, getText().toString().length(), textBound);
            width = textBound.width() + getPaddingLeft() + getPaddingRight();
        }
        if (heightMode == MeasureSpec.AT_MOST) {
            textPaint.getTextBounds(getText().toString(), 0, getText().toString().length(), textBound);
            height = textBound.height() + getPaddingTop() + getPaddingBottom();
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);   // 自己画字
        Paint.FontMetricsInt fontMetricsInt = textPaint.getFontMetricsInt();
        int dy = (fontMetricsInt.bottom - fontMetricsInt.top) / 2 - fontMetricsInt.bottom;
        int baseLine = getHeight() / 2 + dy;
        int x = getWidth() / 2 - Objects.requireNonNull(getTextRect()).width() / 2;
        canvas.save();
        canvas.drawText(getText().toString(), x, baseLine, textPaint);
        canvas.restore();
        canvas.save();
        if (changeLeft) {
            canvas.clipRect(0, 0, (int) (getWidth() * changePercent), getHeight());
        } else {
            canvas.clipRect((int) (getWidth() - getWidth() * changePercent), 0, getWidth(), getHeight());
        }
        canvas.drawText(getText().toString(), x, baseLine, changeColorTextPaint);
        canvas.restore();
    }

    public String getText() {
        return mText;
    }
    private int getTextSize() {
        return mTextSize;
    }

    public void setText(String s) {
         mText = s;
    }

    public void setTextSize(int textSize) {
        mTextSize = textSize;
    }
}
