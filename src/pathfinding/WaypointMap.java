package pathfinding;

import java.util.ArrayList;

/**
 * Created by dennis on 8/23/17.
 */
public class WaypointMap
{
    private ArrayList<Waypoint> waypoints = new ArrayList<>();

    public Route findShortestRoute(int from, int to, int maxsteps)
    {
        ArrayList<Route> routes = new ArrayList<>();

        traverse(routes, new Route(maxsteps), waypoints.get(from - 1), to);

        if(routes.size() > 0)
        {
            double shortestDistance = routes.get(0).getDistance();
            int index = 0;
            for (int i = 0; i < routes.size(); i++)
            {
                if(shortestDistance > routes.get(i).getDistance())
                {
                    shortestDistance = routes.get(i).getDistance();
                    index = i;
                }
            }
            return routes.get(index);
        }
        return null;
    }

    public ArrayList<Route> findAllRoutes(int from, int to, int maxsteps)
    {
        ArrayList<Route> routes = new ArrayList<>();

        traverse(routes, new Route(maxsteps), waypoints.get(from - 1), to);

        return routes;
    }

    private void traverse(ArrayList<Route> routes, Route currentRoute, Waypoint currentWaypoint, int endWaypoint)
    {
        //copy previous rute
        Route routeCopy = new Route(currentRoute.getWaypoints().length, currentRoute.getAmountOfSteps(), currentRoute.getWaypoints(), currentRoute.getDistance());
        routeCopy.extendRoute(currentWaypoint);

        for (int i = 0; i < currentWaypoint.getConnections().size(); i++)
        {
            Waypoint destination = currentWaypoint.getConnections().get(i).getDestination();
            System.out.println(destination);
            boolean notExceeded = routeCopy.canExtendRoute();

            //check if we are traversing in circles
            boolean isTraversingInCircles = false;
            for (int j = 0; j < routeCopy.getWaypoints().length; j++)
            {
                if(routeCopy.getWaypoints()[j] != null)
                {
                    if (routeCopy.getWaypoints()[j].getId() == destination.getId())
                    {
                        isTraversingInCircles = true;
                        break;
                    }
                }
            }

            if(isTraversingInCircles)
            {
                continue;
            }

            if(destination.getId() == endWaypoint)
            {
                routeCopy.extendRoute(destination);
                routes.add(routeCopy);
                break;
            }

            //only traverse if routelimit is not exceeded
            if(notExceeded)
            {
                traverse(routes, routeCopy, destination, endWaypoint);
            }
        }
    }

    public int addWaypoint(double x, double y, double z)
    {
        int id = waypoints.size() + 1;
        waypoints.add(new Waypoint(x, y, z, id));
        return id;
    }

    public void connectTwoWaypoints(int idFrom, int idTo)
    {
        waypoints.get(idFrom - 1).connect(waypoints.get(idTo - 1));
        waypoints.get(idTo - 1).connect(waypoints.get(idFrom - 1));
    }

    public void disconnectTwoWaypoints(int fromId, int toId)
    {
        waypoints.get(fromId - 1).disconnectFrom(toId);
        waypoints.get(toId - 1).disconnectFrom(fromId);
    }

    public void removeWaypoint(int id)
    {

    }

    public Waypoint getWaypoint(int id)
    {
        return waypoints.get(id - 1);
    }

    public Waypoint getClosestWaypoint(int x, int y, int z)
    {
        Waypoint destination = null;
        double distance = 0;
        int index = 0;
        if(waypoints.size() > 0)
        {
            double closestDistance = 0;
            for (int i = 0; i < waypoints.size(); i++)
            {
                destination = waypoints.get(i);
                distance = Math.sqrt(
                        Math.pow(x - destination.getxPosition(), 2) +
                        Math.pow(y - destination.getyPosition(), 2) +
                        Math.pow(z - destination.getzPosition(), 2));
                if(closestDistance > distance)
                {
                    closestDistance = distance;
                    index = i;
                }
            }
        }
        return waypoints.get(index);
    }

    public ArrayList<Waypoint> getWaypoints()
    {
        return waypoints;
    }
}
