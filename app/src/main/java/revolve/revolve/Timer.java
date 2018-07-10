package revolve.revolve;

/**
 * This class is for the count down. The timer is set to 1 minute.
 * Once the timer reaches 0, it will set the game status to "true"
 * and forces the game to jump to the evaluation screen
 */

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.CountDownTimer;

public class Timer {

    String time;
    Paint p;
    Point pos;

    public Timer(int x, int y)
    {
        p = new Paint();
        p.setColor(Color.BLACK);
        p.setTextSize(100);

        pos = new Point(x, y);

        time = "";

        new CountDownTimer(60000, 1000) {

            public void onTick(long millisUntilFinished) {
               time = String.valueOf(millisUntilFinished/1000);
            }

            public void onFinish() {
               GameView.status = true;
            }
        }.start();
    }

    //used to draw the timer at the top of the gameview
    public void drawTime(Canvas canvas)
    {
        canvas.drawText(time, pos.x-50, pos.y-1000, p);
    }
}
