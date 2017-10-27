package pathfinding;

import java.util.ArrayList;

/**
 * Created by dennis on 8/23/17.
 */
public class Waypoint
{
    private double xPosition;
    private double yPosition;
    private double zPosition;
    private int id;

    private ArrayList<Connection> connections = new ArrayList<>();

    public Waypoint(double x, double y, double z, int id)
    {
        xPosition = x;
        yPosition = y;
        zPosition = z;
        this.id = id;
    }

    public int getId()
    {
        return id;
    }

    public ArrayList<Connection> getConnections()
    {
        return connections;
    }

    public Connection getConnectionById(int id)
    {
        for (int i = 0; i < connections.size(); i++)
        {
            if(connections.get(i).getDestination().getId() == id)
            {
                return connections.get(i);
            }
        }
        return null;
    }

    public void connect(Waypoint destination)
    {
        if(destination.getId() == id)
        {
            System.out.println("Cannot connect waypoint with itself! Ignoring...");
            return;
        }
        for (int i = 0; i < connections.size(); i++)
        {
            if(destination.getId() == connections.get(i).getDestination().getId())
            {
                System.out.println("Connection already made! Ignoring...");
                return;
            }
        }
        connections.add(new Connection(this, destination));
    }

    public void disconnectFrom(int id)
    {
        for (int i = 0; i < connections.size(); i++)
        {
            if(connections.get(i).getDestination().getId() == id)
            {
                connections.remove(i);
            }
        }
    }

    public void setxPosition(double xPosition)
    {
        this.xPosition = xPosition;
        updateConnectionDistances();
    }

    public void setyPosition(double yPosition)
    {
        this.yPosition = yPosition;
        updateConnectionDistances();
    }

    public void setzPosition(double zPosition)
    {
        this.zPosition = zPosition;
        updateConnectionDistances();
    }

    private void updateConnectionDistances()
    {
        for (int i = 0; i < connections.size(); i++)
        {
            connections.get(i).refreshDistance();
            connections.get(i).getDestination().getConnectionById(id).refreshDistance();
        }
    }

    public double getxPosition()
    {
        return xPosition;
    }

    public double getyPosition()
    {
        return yPosition;
    }

    public double getzPosition()
    {
        return zPosition;
    }

    public String toString()
    {
        return "Wp " + id;
    }
}
