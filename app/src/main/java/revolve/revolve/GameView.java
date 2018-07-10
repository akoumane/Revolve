package revolve.revolve;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Parcelable;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;

import java.util.Random;

public class GameView extends View {

    Context context;
    Handler handler;
    Runnable runnable;
    final int UPDATE_MILLS = 17;
    Random rand;

    //Objects that need to be drawn
    Station station;
    Ball ball;
    Target[] target;

    //Object for the countdown
    Timer timer;

    Display display;
    Point point;
    int dWidth, dHeight;
    int dCenterX, dCenterY;

    //Stats needed during the evaluation
    public static int score = 0;
    public static int missCount = 0;
    public static int hitCount = 0;

    Paint p;

    //Objects for the sounds needed during gameplay
    MediaPlayer blop;
    MediaPlayer error;

    //Boolean to determine if the game is finished or not
    public static boolean status = false;

    public static boolean exit = false;

    public GameView(Context context) {
        super(context);

        this.context = context;
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        };

        rand = new Random();

        display = ((Activity)getContext()).getWindowManager().getDefaultDisplay();
        point = new Point();
        display.getSize(point);
        dWidth = point.x;
        dHeight = point.y;
        dCenterX = dWidth/2;
        dCenterY = dHeight/2;

        station = new Station(context, dCenterX, dCenterY);
        ball = new Ball(context, dCenterX, dCenterY);

        target = new Target[3];

        target[0] = new Target(context, dCenterX, dCenterY,
                BitmapFactory.decodeResource(getResources(), R.drawable.target1),
                Global.target1Spin, 1);

        target[1] = new Target(context, dCenterX, dCenterY,
                BitmapFactory.decodeResource(getResources(), R.drawable.target2),
                Global.target2Spin, 2);

        target[2] = new Target(context, dCenterX, dCenterY,
                BitmapFactory.decodeResource(getResources(), R.drawable.target3),
                Global.target3Spin, 3);

        timer = new Timer(dCenterX, dCenterY);

        blop = MediaPlayer.create(context, R.raw.blop);
        error = MediaPlayer.create(context, R.raw.error);

        p = new Paint();
        p.setColor(Color.BLACK);
        p.setTextSize(100);

    }

    @Override
    protected void onDraw(final Canvas canvas) {
        super.onDraw(canvas);

        if(status) {
            goToEvalScreen();
            return;
        }

        station.drawStation(canvas);
        target[0].drawTarget(canvas);
        target[1].drawTarget(canvas);
        target[2].drawTarget(canvas);
        ball.drawBall(canvas);
        timer.drawTime(canvas);

        canvas.drawText("Score: " + score, dCenterX-200, dHeight-200, p);
        canvas.drawText("Miss: " + missCount, dCenterX-200, dHeight-100, p);

        handler.postDelayed(runnable, UPDATE_MILLS);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int type = event.getAction();

        //checks for collision of the marker with the targets
        if(type == MotionEvent.ACTION_DOWN)
        {
            if(ball.isCollide(target[0])) {
                blop.start();
                hitCount++;
                score += target[0].score;
            }
            else if(ball.isCollide(target[1])) {
                blop.start();
                hitCount++;
                score += target[1].score;
            }
            else if(ball.isCollide(target[2])) {
                blop.start();
                hitCount++;
                score += target[2].score;
            }
            else {
                error.start();
                missCount++;
            }
        }

        return true;
    }

    //resets the game stats
    public static void reset()
    {
        score = 0;
        missCount = 0;
        status = false;
    }

    //Once the status is set to "true", this method is executed from the onDraw method
    private void goToEvalScreen()
    {
        Context ctx = context.getApplicationContext();
        Intent intent = new Intent(ctx, Evaluation.class);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        ctx.startActivity(intent);
    }
}
