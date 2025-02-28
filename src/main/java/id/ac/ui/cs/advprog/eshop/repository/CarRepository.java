package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Car;
import org.springframework.stereotype.Repository;

@Repository
public class CarRepository extends InMemoryRepository<Car> implements CarRepositoryInterface {

    @Override
    protected String getId(Car car) {
        return car.getCarId();
    }

    @Override
    protected void setId(Car car, String id) {
        car.setCarId(id);
    }

    @Override
    protected boolean idMatches(Car car, String id) {
        return car.getCarId().equals(id);
    }

    @Override
    protected void updateEntity(Car existingCar, Car updatedCar) {
        existingCar.setCarName(updatedCar.getCarName());
        existingCar.setCarColor(updatedCar.getCarColor());
        existingCar.setCarQuantity(updatedCar.getCarQuantity());
    }
}
