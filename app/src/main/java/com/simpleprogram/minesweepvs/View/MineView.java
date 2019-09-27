package com.simpleprogram.minesweepvs.View;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.media.MediaMetadataRetriever;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.simpleprogram.minesweepvs.Coord;
import com.simpleprogram.minesweepvs.Game;
import com.simpleprogram.minesweepvs.MainActivity;
import com.simpleprogram.minesweepvs.R;
import com.simpleprogram.minesweepvs.Ranges;

/**
 * TODO: document your custom view class.
 */
public class MineView extends View implements View.OnClickListener, View.OnLongClickListener {
    private String mExampleString; // TODO: use a default from R.string...
    private int mExampleColor = Color.RED; // TODO: use a default from R.color...
    private float mExampleDimension = 0; // TODO: use a default from R.dimen...
    private Drawable mExampleDrawable;

    private TextPaint mTextPaint;
    private float mTextWidth;
    private float mTextHeight;
    private int x;
    private int y;
    private Coord coord ;

    public void setGame(Game game) {
        this.game = game;
        this.setOnClickListener(this);
        this.setOnLongClickListener(this);
    }

    private Game game;

    public MineView(Context context) {
        super(context);
        init(null, 0);
    }

    public MineView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public MineView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.MineView, defStyle, 0);

        mExampleString = a.getString(
                R.styleable.MineView_exampleString);
        mExampleColor = a.getColor(
                R.styleable.MineView_exampleColor,
                mExampleColor);
        // Use getDimensionPixelSize or getDimensionPixelOffset when dealing with
        // values that should fall on pixel boundaries.
        mExampleDimension = a.getDimension(
                R.styleable.MineView_exampleDimension,
                mExampleDimension);

        if (a.hasValue(R.styleable.MineView_exampleDrawable)) {
            mExampleDrawable = a.getDrawable(
                    R.styleable.MineView_exampleDrawable);
            mExampleDrawable.setCallback(this);
        }

        a.recycle();

        // Set up a default TextPaint object
        mTextPaint = new TextPaint();
        mTextPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextAlign(Paint.Align.LEFT);

        // Update TextPaint and text measurements from attributes
        invalidateTextPaintAndMeasurements();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        //String value = "x=" + pxToDp(event.getX()) + ", y=" + pxToDp(event.getY());
        //Toast.makeText(this.getContext(), value, Toast.LENGTH_LONG).show();
        x = pxToDp( event.getX())/MainActivity.IMAGE_SIZE ;
        y = pxToDp(event.getY()) / MainActivity.IMAGE_SIZE;
        coord = new Coord(x,y);
        return super.onTouchEvent(event);
    }

    private int pxToDp(float pixel) {
        return (int) (pixel / Resources.getSystem().getDisplayMetrics().density);
    }

    private int dpToPx(float dp) {
        return (int) (dp *  Resources.getSystem().getDisplayMetrics().density);
    }
    private void invalidateTextPaintAndMeasurements() {
        mTextPaint.setTextSize(mExampleDimension);
        mTextPaint.setColor(mExampleColor);
        mTextWidth = mTextPaint.measureText(mExampleString);

        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        mTextHeight = fontMetrics.bottom;
    }

    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // TODO: consider storing these as member variables to reduce
        // allocations per draw cycle.
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();

        int contentWidth = getWidth() - paddingLeft - paddingRight;
        int contentHeight = getHeight() - paddingTop - paddingBottom;

        // Draw the text.
        canvas.drawText(mExampleString,
                paddingLeft + (contentWidth - mTextWidth) / 2,
                paddingTop + (contentHeight + mTextHeight) / 2,
                mTextPaint);

        // Draw the example drawable on top of the text.
        if (mExampleDrawable != null) {
            mExampleDrawable.setBounds(paddingLeft, paddingTop,
                    paddingLeft + contentWidth, paddingTop + contentHeight);
            mExampleDrawable.draw(canvas);
        }
        canvas.drawColor(0);
        for (Coord coord : Ranges.getAllCoords()) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), game.getBox(coord).getImage());
            //bitmap = getResizedBitmap(bitmap, bitmap.getWidth(), bitmap.getHeight());
            canvas.drawBitmap(bitmap, dpToPx(coord.x* MainActivity.IMAGE_SIZE)
                    , dpToPx(coord.y * MainActivity.IMAGE_SIZE), null);
        }
    }



    /**
     * Gets the example string attribute value.
     *
     * @return The example string attribute value.
     */
    public String getExampleString() {
        return mExampleString;
    }

    /**
     * Sets the view's example string attribute value. In the example view, this string
     * is the text to draw.
     *
     * @param exampleString The example string attribute value to use.
     */
    public void setExampleString(String exampleString) {
        mExampleString = exampleString;
        invalidateTextPaintAndMeasurements();
    }

    /**
     * Gets the example color attribute value.
     *
     * @return The example color attribute value.
     */
    public int getExampleColor() {
        return mExampleColor;
    }

    /**
     * Sets the view's example color attribute value. In the example view, this color
     * is the font color.
     *
     * @param exampleColor The example color attribute value to use.
     */
    public void setExampleColor(int exampleColor) {
        mExampleColor = exampleColor;
        invalidateTextPaintAndMeasurements();
    }

    /**
     * Gets the example dimension attribute value.
     *
     * @return The example dimension attribute value.
     */
    public float getExampleDimension() {
        return mExampleDimension;
    }

    /**
     * Sets the view's example dimension attribute value. In the example view, this dimension
     * is the font size.
     *
     * @param exampleDimension The example dimension attribute value to use.
     */
    public void setExampleDimension(float exampleDimension) {
        mExampleDimension = exampleDimension;
        invalidateTextPaintAndMeasurements();
    }

    /**
     * Gets the example drawable attribute value.
     *
     * @return The example drawable attribute value.
     */
    public Drawable getExampleDrawable() {
        return mExampleDrawable;
    }

    /**
     * Sets the view's example drawable attribute value. In the example view, this drawable is
     * drawn above the text.
     *
     * @param exampleDrawable The example drawable attribute value to use.
     */
    public void setExampleDrawable(Drawable exampleDrawable) {
        mExampleDrawable = exampleDrawable;
    }

    @Override
    public void onClick(View v) {
        //String value = "onClick x=" + x + ", y=" + y;
        //Toast.makeText(this.getContext(), value, Toast.LENGTH_LONG).show();
        /*Coord coord = new Coord(x, y);
        game.pressLeftButton(coord);
        invalidate();*/
        game.pressLeftButton(coord);
        invalidate();
    }

    @Override
    public boolean onLongClick(View v) {
        //String value = "onLongClick x=" + x + ", y=" + y;
        //Toast.makeText(this.getContext(), value, Toast.LENGTH_LONG).show();
        game.pressRightButton(coord);
        invalidate();
        return false;
    }
}
