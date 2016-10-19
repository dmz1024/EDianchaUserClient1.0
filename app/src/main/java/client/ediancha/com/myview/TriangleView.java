package client.ediancha.com.myview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by dengmingzhi on 2016/10/19.
 */

public class TriangleView extends View {
    public TriangleView(Context context) {
        super(context);
    }

    public TriangleView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TriangleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        /*设置背景为白色*/
        canvas.drawColor(Color.WHITE);
        Paint paint = new Paint();
        /*去锯齿*/
        paint.setAntiAlias(true);
		/*设置paint的颜色*/
        paint.setColor(Color.WHITE);
		/*设置paint的　style　为STROKE：空心*/
        paint.setStyle(Paint.Style.FILL);
		/*设置paint的外框宽度*/
        paint.setStrokeWidth(1);

        /*画一个实心三角形*/
        Path path2 = new Path();
        path2.moveTo(90, 330);
        path2.lineTo(150, 330);
        path2.lineTo(120, 270);
        path2.close();
        canvas.drawPath(path2, paint);
    }


}
