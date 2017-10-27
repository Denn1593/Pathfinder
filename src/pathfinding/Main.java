package pathfinding;

/**
 * Created by dennis on 8/23/17.
 */
public class Main
{
    public static void main(String[] args)
    {
        WaypointMap map = new WaypointMap();

        map.addWaypoint(1.64, 2.23, 0);
        map.addWaypoint(6.74, 5.88, 0);
        map.addWaypoint(5.14, 7.99, 0);
        map.addWaypoint(7.46, 8.37, 0);
        map.addWaypoint(10, 10.34, 0);
        map.addWaypoint(4.18, 4.9, 0);

        map.connectTwoWaypoints(1, 6);
        map.connectTwoWaypoints(6, 3);
        map.connectTwoWaypoints(6, 2);
        map.connectTwoWaypoints(2, 4);
        map.connectTwoWaypoints(4, 5);


        //System.out.println(map.getWaypoint(3).getConnections());

        System.out.println(map.findShortestRoute(1, 5, 20));
    }
}
