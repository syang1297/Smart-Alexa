//getting VehicleLocation

import com.smartcar.sdk.AuthClient;
import com.smartcar.sdk.SmartcarException;
import com.smartcar.sdk.Vehicle;
import com.smartcar.sdk.data.SmartcarResponse;
import com.smartcar.sdk.data.VehicleIds;
import com.smartcar.sdk.data.*;
import java.util.*;

public class CarInformation {
    public Vehicle createCarInstance() throws SmartcarException {
        //gets list of vehicles under the client
        SmartcarResponse<VehicleIds> vehicleIdsResponse = AuthClient.getVehicleIds(auth.getAccessToken());
        String[] vehicleIds = vehicleIdsResponse.getData().getVehicleIds();

        //creates an instance of the vehicle we want
        Vehicle vehicle = new Vehicle(vehicleIds[0], auth.getAccessToken());
        String make = vehicle.info().getMake();
        String model = vehicle.info().getModel();
        return vehicle;
    }



    public String getCarCoordinates(Vehicle vehicle) throws SmartcarException {
        SmartcarResponse<VehicleLocation> locationResponse = vehicle.location();
        VehicleLocation locationData = locationResponse.getData();
        String latLong = locationData.getLatitude() + ", " + locationData.getLongitude();
        return latLong;
    }

    public void lockCar(Vehicle vehicle) throws SmartcarException {
        vehicle.lock();
    }

    public void unlockCar(Vehicle vehicle){
        try {
            vehicle.unlock();
        } catch (SmartcarException e) {
            e.printStackTrace();
        }
    }

    public String oilChange(Vehicle vehicle) {
        SmartcarResponse<VehicleOdometer> odometerResponse = vehicle.odometer();
        VehicleOdometer odometerData = odometerResponse.getData();
        Double odometer = Double.valueOf(odometerData.getDistance());
        Integer odometerInt = Integer.valueOf(odometer.intValue());
        Integer milesLeft =  odometerInt % 3000;
        String resp = "You have " + milesLeft + "miles left before your next oil change.";
        return resp;
    }
}
