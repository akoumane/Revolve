package revolve.revolve;

import android.content.Context;
import android.view.View;
import android.graphics.Point;

public class GameObject extends View {

    Point pos;

    public GameObject(Context context, int x, int y) {
        super(context);

        pos = new Point(x, y);
    }

    public Point getPos() {return this.pos;}

    public void setPos(Point p) {this.pos = p;}

    public void setPos(int x, int y)
    {
        this.pos.x = x;
        this.pos.y = y;
    }
}
