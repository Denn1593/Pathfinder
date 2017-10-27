package pathfinding;

/**
 * Created by dennis on 8/23/17.
 */
public class Connection
{
    private Waypoint destination;
    private Waypoint origin;
    private double distance;

    public Connection(Waypoint origin, Waypoint destination)
    {
        this.destination = destination;
        this.origin = origin;
        updateDistance();
    }

    private void updateDistance()
    {
        distance = Math.sqrt(
                Math.pow(origin.getxPosition() - destination.getxPosition(), 2) +
                Math.pow(origin.getyPosition() - destination.getyPosition(), 2) +
                Math.pow(origin.getzPosition() - destination.getzPosition(), 2));
    }

    public void refreshDistance()
    {
        updateDistance();
    }

    public Waypoint getDestination()
    {
        return destination;
    }

    public Waypoint getOrigin()
    {
        return origin;
    }

    public double getDistance()
    {
        return distance;
    }

    public String toString()
    {
        return "pathfinding.Connection from " + origin + " to " + destination;
    }
}
