package revolve.revolve;

/**
 * This class is for the station that the ball and targets revolve around.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;

public class Station extends GameObject {

    Bitmap station;
    Matrix matrix;
    int stationWidth, stationHeight;
    int stationCenterX, stationCenterY;

    int deg = 0;

    public Station(Context context, int x, int y) {
        super(context, x, y);

        station = BitmapFactory.decodeResource(getResources(), R.drawable.station);
        stationWidth = station.getWidth();
        stationHeight = station.getHeight();
        stationCenterX = stationWidth/2;
        stationCenterY = stationHeight/2;

        this.setPos(x - stationCenterX, y - stationCenterY);

        matrix = new Matrix();
    }

    private void update()
    {
        deg = deg == 360 ? 0 : (deg += Global.stationSpin);
        matrix.setRotate(deg, stationCenterX, stationCenterY);
        matrix.postTranslate(this.pos.x, this.pos.y);
    }

    public void drawStation(Canvas canvas)
    {
        update();
        canvas.drawBitmap(station, matrix, null);
    }
}
