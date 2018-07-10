package revolve.revolve;

import android.content.Context;
import android.graphics.Rect;

public class SolidObject extends GameObject {

    Rect rect;

    public SolidObject(Context context, int x, int y) {
        super(context, x, y);
        rect = new Rect();
    }

    public void setRect(Rect r) {this.rect = r;}
    public void setRect(int top, int bottom, int left, int right)
    {
        this.rect = new Rect(left, top, right, bottom);
    }

    public boolean isCollide(SolidObject obj)
    {
        if(this.rect.left < obj.rect.right && this.rect.right > obj.rect.left &&
                this.rect.top < obj.rect.bottom && this.rect.bottom > obj.rect.top)
            return true;
        else
            return  false;
    }
}
