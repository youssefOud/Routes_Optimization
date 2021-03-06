package controler;

import view.MainWindow;

/**
 * Represents the State after the add button has been pressed in order to add a
 * delivery point.
 *
 * @author PLD-HEXA-301
 */
public class AddState extends DefaultState {

    /**
     * Represents the duration entered by the user when he wants to add a new
     * delivery point
     */
    private int duration;

    /**
     * Number of delivery point original. Increase when a new delivery point is
     * added and decrease if the user has clicked on undo
     */
    private int originalPointNumber;

    /**
     * Constructor
     */
    public AddState() {
        originalPointNumber = 0;
    }

    /**
     * Sets originalPointNumber to the given value.
     *
     * @param originalPointNumber
     */
    public void setOriginalPointNumber(int originalPointNumber) {
        this.originalPointNumber = originalPointNumber;
    }

    /**
     * Increments originalPointNumber.
     */
    public void addNumberPoint() {
        originalPointNumber++;
    }

    /**
     * Decrements originalPointNumber.
     */
    public void subtractPointNumber() {
        originalPointNumber--;
    }

    /**
     * Sets duration to the given value.
     *
     * @param duration
     */
    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public void mouseClick(Controller controller, MainWindow mainWindow, CmdList cmdList, int x, int y) {
        x /= mainWindow.getGraphicalView().getScale();
        y /= mainWindow.getGraphicalView().getScale();
        double latitude = mainWindow.getGraphicalView().getLatMax() - (y + mainWindow.getGraphicalView().getPointradius()) / mainWindow.getGraphicalView().getWidthScale();
        double longitude = mainWindow.getGraphicalView().getLongMax() - (mainWindow.getGraphicalView().getMapSize() - x - mainWindow.getGraphicalView().getPointradius()) / mainWindow.getGraphicalView().getHeightScale();
        double minDistance = minimalDistance; // minimal distance to get the point corresponding to x and y on the map
        double distance;
        Integer indexNewDeliveryPoint = null;
        // To retrieve the index of the new delivery point added
        int numberOfCoordinates = mainWindow.getGraphicalView().getMap().getCoordinates().length;
        for (int i = 0; i < numberOfCoordinates; i++) {

            double curLatitude = mainWindow.getGraphicalView().getMap().getCoordinates()[i].getLatitude();
            double curLongitude = mainWindow.getGraphicalView().getMap().getCoordinates()[i].getLongitude();
            distance = Math.sqrt(Math.pow(latitude - curLatitude, 2) + Math.pow(longitude - curLongitude, 2));
            if (distance < minDistance) {
                minDistance = distance;
                indexNewDeliveryPoint = i;
            }

        }
        if (indexNewDeliveryPoint != null) {
            if (mainWindow.getGraphicalView().getMap().getUnreachablePoints().contains(indexNewDeliveryPoint)
                     || mainWindow.getGraphicalView().getMap().getNonReturnPoints().contains(indexNewDeliveryPoint)) {
                indexNewDeliveryPoint = null;
                controller.setCurState(controller.computeState);
                mainWindow.showError("The point you have chosen is invalid");
                
            } else {
                cmdList.add(new CmdAddDeliveryPoint(mainWindow, indexNewDeliveryPoint, duration, originalPointNumber, controller));
                controller.setCurState(controller.computeState);
            }

        } else {
            controller.setCurState(controller.computeState);
            mainWindow.showError("The point you have chosen is invalid");

        }
    }

    @Override
    public void loadPlan(Controller controller, MainWindow mainWindow) {
    }
}
