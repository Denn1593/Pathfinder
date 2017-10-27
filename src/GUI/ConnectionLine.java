package GUI;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

/**
 * Created by Dennis on 24-08-2017.
 */
public class ConnectionLine extends Line
{
    private int fromId;
    private int toId;

    public ConnectionLine(double x1, double y1, double x2, double y2, int fromId, int toId)
    {
        super(x1, y1, x2, y2);
        this.toId = toId;
        this.fromId = fromId;
        setStrokeWidth(4);
    }

    public void checkifIsConnection(int fromId, int toId)
    {
        if((fromId == this.fromId && toId == this.toId) || (fromId == this.toId && toId == this.fromId))
        {
            setStroke(Color.LIGHTGREEN);
        }
    }

    public int getFromId()
    {
        return fromId;
    }

    public int getToId()
    {
        return toId;
    }
}
