package GUI;

import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import pathfinding.Route;
import pathfinding.Waypoint;
import pathfinding.WaypointMap;
import java.util.ArrayList;

/**
 * Created by dennis on 8/24/17.
 */
public class MapEditor extends Pane
{
    private ToggleButton addWaypoint;
    private ToggleButton connectWaypoints;
    private ToggleButton moveWaypoints;
    private ToggleButton deleteWaypoints;
    private Button findShortestRoute;
    private TextField xPosition;
    private TextField yPosition;
    private WaypointMap waypointMap;
    private Line temporaryLine;
    private int selection;

    public MapEditor()
    {
        addWaypoint = new ToggleButton("Add");
        connectWaypoints = new ToggleButton("Connect");
        moveWaypoints = new ToggleButton("Move");
        deleteWaypoints = new ToggleButton("Delete");

        ToggleGroup group = new ToggleGroup();
        group.getToggles().addAll(addWaypoint, connectWaypoints, moveWaypoints, deleteWaypoints);

        findShortestRoute = new Button("Find Shortest Route");

        xPosition = new TextField();
        yPosition = new TextField();

        waypointMap = new WaypointMap();

        temporaryLine = new Line();
        temporaryLine.setVisible(false);

        HBox hBox = new HBox();

        hBox.getChildren().addAll(addWaypoint, connectWaypoints, moveWaypoints, deleteWaypoints, xPosition, yPosition, findShortestRoute);

        getChildren().addAll(hBox, temporaryLine);

        createBindings();
    }

    private void createBindings()
    {
        setOnMouseClicked(e->
        {
            if(addWaypoint.isSelected())
            {
                waypointMap.addWaypoint(e.getX(), e.getY(), 0);
                updateWaypoints();
            }
            if(e.getButton() == MouseButton.SECONDARY)
            {
                selection = 0;
                temporaryLine.setVisible(false);
            }
        });

        setOnMouseMoved(e->
        {
            temporaryLine.setEndX(e.getX());
            temporaryLine.setEndY(e.getY());
        });

        addWaypoint.setOnAction(e->
        {
            selection = 0;
            temporaryLine.setVisible(false);
        });

        findShortestRoute.setOnAction(e-> showRoute());
    }

    public void updateMap()
    {
        for (int i = 0; i < getChildren().size(); i++)
        {
            if(getChildren().get(i) instanceof Label)
            {
                Label label = (Label) getChildren().get(i);
                label.setLayoutX(waypointMap.getWaypoint(Integer.parseInt(label.getText())).getxPosition());
                label.setLayoutY(waypointMap.getWaypoint(Integer.parseInt(label.getText())).getyPosition());
            }
            if(getChildren().get(i) instanceof ConnectionLine)
            {
                ConnectionLine line = (ConnectionLine) getChildren().get(i);
                line.setStartX(waypointMap.getWaypoint(line.getFromId()).getxPosition());
                line.setStartY(waypointMap.getWaypoint(line.getFromId()).getyPosition());
                line.setEndX(waypointMap.getWaypoint(line.getToId()).getxPosition());
                line.setEndY(waypointMap.getWaypoint(line.getToId()).getyPosition());
            }
        }
    }

    private void  updateWaypoints()
    {
        ArrayList<Waypoint> waypoints = waypointMap.getWaypoints();

        for (int i = 0; i < waypoints.size(); i++)
        {
            boolean notCreated = true;
            for (int j = 0; j < getChildren().size(); j++)
            {
                if(getChildren().get(j) instanceof Label)
                {
                    if((Integer.parseInt(((Label) getChildren().get(j)).getText()) == waypoints.get(i).getId()))
                    {
                        notCreated = false;
                    }

                }
            }
            if(notCreated)
            {
                Label label = new Label(Integer.toString(waypoints.get(i).getId()));
                label.setLayoutX(waypoints.get(i).getxPosition());
                label.setLayoutY(waypoints.get(i).getyPosition());
                label.setOnMouseEntered(e->
                {
                    if(moveWaypoints.isSelected() || connectWaypoints.isSelected() || deleteWaypoints.isSelected())
                    {
                        label.setScaleX(2);
                        label.setScaleY(2);
                    }
                });
                label.setOnMouseExited(e->
                {
                    label.setScaleX(1);
                    label.setScaleY(1);
                });
                label.setOnMouseClicked(e->
                {
                    if(connectWaypoints.isSelected())
                    {
                        addToSelection(Integer.parseInt(label.getText()));
                    }
                    if(deleteWaypoints.isSelected())
                    {
                        waypointMap.removeWaypoint(Integer.parseInt(label.getText()));
                        getChildren().remove(label);
                    }
                });
                label.setOnMouseDragged(e->
                {
                    if(moveWaypoints.isSelected())
                    {
                        Waypoint waypoint = waypointMap.getWaypoint(Integer.parseInt(label.getText()));
                        waypoint.setxPosition(label.getLayoutX() + e.getX());
                        waypoint.setyPosition(label.getLayoutY() + e.getY());
                        updateMap();
                    }
                });
                getChildren().add(label);
            }
        }
    }

    private void showRoute()
    {
        Route route = waypointMap.findShortestRoute(Integer.parseInt(xPosition.getText()), Integer.parseInt(yPosition.getText()), 20);
        System.out.println(route);

        for (int i = 0; i < getChildren().size(); i++)
        {
            if(getChildren().get(i) instanceof ConnectionLine)
            {
                ((ConnectionLine) getChildren().get(i)).setStroke(Color.BLACK);
            }
        }

        for (int i = 0; i < route.getWaypoints().length; i++)
        {
            if(route.getWaypoints()[i] != null)
            {
                for (int j = 0; j < getChildren().size(); j++)
                {
                    if (getChildren().get(j) instanceof Label)
                    {
                        boolean isThere = false;
                        for (int k = 0; k < route.getWaypoints().length; k++)
                        {
                            if(route.getWaypoints()[k] != null)
                            {
                                if (Integer.parseInt(((Label) getChildren().get(j)).getText()) == route.getWaypoints()[k].getId())
                                {
                                    isThere = true;
                                    break;
                                }
                            }
                        }
                        if(isThere)
                        {
                            getChildren().get(j).setEffect(new DropShadow(5, Color.GREEN));
                        }
                        else
                        {
                            getChildren().get(j).setEffect(null);
                        }
                    }
                    if(getChildren().get(j) instanceof ConnectionLine)
                    {
                        if(route.getWaypoints()[i + 1] != null)
                        {
                            ((ConnectionLine) getChildren().get(j)).checkifIsConnection(route.getWaypoints()[i].getId(), route.getWaypoints()[i + 1].getId());
                        }
                    }
                }
            }
        }
    }
    private void addToSelection(int id)
    {
        if(selection == 0)
        {
            System.out.println(id + " selected!");
            temporaryLine.setStartX(waypointMap.getWaypoints().get(id - 1).getxPosition());
            temporaryLine.setStartY(waypointMap.getWaypoints().get(id - 1).getyPosition());
            temporaryLine.setVisible(true);
            selection = id;
        }
        else
        {
            temporaryLine.setVisible(false);
            System.out.println("Connected " + selection + " with " + id);
            waypointMap.connectTwoWaypoints(selection, id);
            double x = waypointMap.getWaypoint(selection).getxPosition();
            double y = waypointMap.getWaypoint(selection).getyPosition();
            double x2 = waypointMap.getWaypoint(id).getxPosition();
            double y2 = waypointMap.getWaypoint(id).getyPosition();
            ConnectionLine line = new ConnectionLine(x, y, x2, y2, selection, id);
            line.setOnMouseClicked(e->
            {
                if(deleteWaypoints.isSelected())
                {
                    waypointMap.disconnectTwoWaypoints(line.getFromId(), line.getToId());
                    for (int i = 0; i < getChildren().size(); i++)
                    {
                        if(getChildren().get(i) instanceof ConnectionLine)
                        {
                            if(((ConnectionLine) getChildren().get(i)).getFromId() == line.getFromId() &&
                                    ((ConnectionLine) getChildren().get(i)).getToId() == line.getToId())
                            {
                                getChildren().remove(i);
                            }
                        }
                    }
                }
            });
            line.setOnMouseEntered(e->
            {
                if(deleteWaypoints.isSelected())
                {
                    line.setEffect(new DropShadow(4, Color.RED));
                }
            });
            line.setOnMouseExited(e->
            {
                if(deleteWaypoints.isSelected())
                {
                    line.setEffect(null);
                }
            });
            getChildren().addAll(line);
            selection = 0;
        }
    }
}
