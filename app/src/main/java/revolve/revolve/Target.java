package revolve.revolve;

/**
 * This class is for the targets. It will take in a specific bitmap
 * and a score value. Like the ball class, it will be drawn on the edge of the
 * station and updates using the equation for the point on a circle.
 *
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.Random;

public class Target extends SolidObject {
    
    Bitmap target;
    int targetWidth, targetHeight;
    int targetCenterX, targetCenterY;
    int dCenterX, dCenterY;
    float tarPosX, tarPosY;
    int deg = 0;
    int spin;
    int score;
    
    Random rand;
    
    Paint p;
    
    public Target(Context context, int x, int y, Bitmap bm, int sp, int sc) {
        super(context, x, y);
        
        dCenterX = x;
        dCenterY = y;
        
        target = bm;
        targetWidth = target.getWidth();
        targetHeight = target.getHeight();
        targetCenterX = targetWidth/2;
        targetCenterY = targetHeight/2;

        spin = sp;
        score = sc;
        
        rand = new Random();

        p = new Paint();
        p.setAlpha(80);
        p.setColor(Color.RED);
    }
    
    private void update()
    {
        int randNum = rand.nextInt(4) + 1;
        deg = deg >= 360 ? 0 : (deg += randNum*spin);
        tarPosX = (float) (dCenterX - targetCenterX + 700 * Math.cos(Math.toRadians(deg)));
        tarPosY = (float) (dCenterY  - targetCenterY + 700 * Math.sin(Math.toRadians(deg)));
        setRect((int)tarPosY, (int)tarPosY + targetHeight, (int)tarPosX, (int)tarPosX + targetWidth);
    }

    public void drawTarget(Canvas canvas)
    {
        update();
        if(Global.debug)
            canvas.drawRect(rect, p);

        canvas.drawBitmap(target, tarPosX, tarPosY, null);
    }
}
