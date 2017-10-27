package pathfinding;

/**
 * Created by dennis on 8/23/17.
 */
public class Route
{
    private int index;
    private double distance;
    private Waypoint[] waypoints;

    public Route(int maxSteps)
    {
        waypoints = new Waypoint[maxSteps];
    }

    public Route(int maxSteps, int amountOfSteps, Waypoint[] currentWaypoints, double distance)
    {
        index = amountOfSteps - 1;
        this.distance = distance;
        waypoints = new Waypoint[maxSteps];
        System.arraycopy(currentWaypoints, 0, waypoints, 0, currentWaypoints.length);

    }

    public boolean canExtendRoute()
    {
        if(index < waypoints.length - 1)
        {
            return true;
        }
        return false;
    }

    public void extendRoute(Waypoint waypoint)
    {
        try
        {
            waypoints[index++] = waypoint;
        }
        catch (IndexOutOfBoundsException e)
        {
            return;
        }
        updateDistance();
    }

    public double getDistance()
    {
        return distance;
    }

    public int getAmountOfSteps()
    {
        return index + 1;
    }

    public Waypoint[] getWaypoints()
    {
        return waypoints;
    }

    private void updateDistance()
    {
        if(index > 1)
        {
            distance += waypoints[index - 2].getConnectionById(waypoints[index - 1].getId()).getDistance();
        }
    }

    public String toString()
    {
        String string = "";

        for (int i = 0; i < index; i++)
        {
            string += waypoints[i] + " ";
        }

        string += " length: " + distance;
        return string;
    }
}
