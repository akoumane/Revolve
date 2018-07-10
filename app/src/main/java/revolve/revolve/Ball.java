package revolve.revolve;

/**
 * This class is for the marker/ball. The image used is
 * ball.png and the image is set to be drawn on the edge of the station.
 * It will constantly update its position by using the equation of the point on
 * a circle.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Ball extends SolidObject {

    Bitmap ball;
    int ballWidth, ballHeight;
    int ballCenterX, ballCenterY;
    int dCenterX, dCenterY;
    float ballPosX, ballPosY;
    int deg = 0;

    Paint p;

    public Ball(Context context, int x, int y) {
        super(context, x, y);

        dCenterX = x;
        dCenterY = y;

        ball = BitmapFactory.decodeResource(getResources(), R.drawable.ball);
        ballWidth = ball.getWidth();
        ballHeight = ball.getHeight();
        ballCenterX = ballWidth/2;
        ballCenterY = ballHeight/2;

        p = new Paint();
        p.setAlpha(80);
        p.setColor(Color.MAGENTA);
    }

    private void update()
    {
        deg = deg == -360 ? 0 : (deg -= Global.ballSpin);
        ballPosX = (float) (dCenterX - ballCenterX + 700 * Math.cos(Math.toRadians(deg)));
        ballPosY = (float) (dCenterY  - ballCenterY + 700 * Math.sin(Math.toRadians(deg)));
        setRect((int)ballPosY, (int)ballPosY + ballHeight, (int)ballPosX, (int) ballPosX + ballWidth);
    }

    public void drawBall(Canvas canvas)
    {
        update();
        if(Global.debug)
            canvas.drawRect(rect, p);

        canvas.drawBitmap(ball, ballPosX, ballPosY, null);
    }
}
