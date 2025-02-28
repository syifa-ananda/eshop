package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.service.CarService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CarControllerTest {

    private CarService carService;
    private CarController carController;

    @BeforeEach
    void setUp() {
        // Create a mock for CarService
        carService = mock(CarService.class);
        // Pass the mock to the CarController
        carController = new CarController(carService);
    }

    @Test
    void testCreateCarPage() {
        // Prepare a model to capture attributes
        Model model = new ExtendedModelMap();

        // Call the controller method
        String viewName = carController.createCarPage(model);

        // Check the returned view
        assertEquals("createCar", viewName);
        // Ensure "car" attribute was added
        assertTrue(model.containsAttribute("car"));
        // Optionally check that it’s a Car instance
        Object carObject = model.getAttribute("car");
        assertNotNull(carObject);
        assertTrue(carObject instanceof Car);
    }

    @Test
    void testCreateCarPost() {
        // Prepare a Car object
        Car car = new Car();
        car.setCarId("123");
        car.setCarName("Test Car");

        // Call the controller method
        String redirect = carController.createCarPost(car);

        // Verify that carService.create(...) was called
        verify(carService, times(1)).create(car);
        // Check the redirect
        assertEquals("redirect:listCar", redirect);
    }

    @Test
    void testCarListPage() {
        // Stub the service to return some cars
        List<Car> mockCars = new ArrayList<>();
        Car car1 = new Car();
        car1.setCarId("id-1");
        mockCars.add(car1);
        when(carService.findAll()).thenReturn(mockCars);

        Model model = new ExtendedModelMap();
        String viewName = carController.carListPage(model);

        // Verify the view
        assertEquals("carList", viewName);
        // Verify model attribute
        assertTrue(model.containsAttribute("cars"));
        // Check the content
        List<Car> carsFromModel = (List<Car>) model.getAttribute("cars");
        assertEquals(1, carsFromModel.size());
        assertEquals("id-1", carsFromModel.get(0).getCarId());

        // Ensure carService.findAll() was called
        verify(carService, times(1)).findAll();
    }

    @Test
    void testEditCarPage() {
        // Stub the service
        Car existingCar = new Car();
        existingCar.setCarId("123");
        existingCar.setCarName("Existing Car");
        when(carService.findById("123")).thenReturn(existingCar);

        Model model = new ExtendedModelMap();
        String viewName = carController.editCarPage("123", model);

        assertEquals("editCar", viewName);
        assertTrue(model.containsAttribute("car"));

        // Ensure it’s the same Car we stubbed
        Car carFromModel = (Car) model.getAttribute("car");
        assertEquals("123", carFromModel.getCarId());
        assertEquals("Existing Car", carFromModel.getCarName());

        // Verify service call
        verify(carService, times(1)).findById("123");
    }

    @Test
    void testEditCarPost() {
        // Prepare updated Car
        Car updatedCar = new Car();
        updatedCar.setCarId("abc");
        updatedCar.setCarName("New Name");
        updatedCar.setCarColor("Blue");
        updatedCar.setCarQuantity(5);

        // Call the controller method
        String redirect = carController.editCarPost(updatedCar);

        // Check redirect
        assertEquals("redirect:listCar", redirect);

        // Verify the service call
        // We can capture the arguments to check them
        ArgumentCaptor<String> idCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Car> carCaptor = ArgumentCaptor.forClass(Car.class);
        verify(carService).update(idCaptor.capture(), carCaptor.capture());

        // The ID is from updatedCar.getCarId()
        assertEquals("abc", idCaptor.getValue());
        // The Car object has the updated fields
        Car capturedCar = carCaptor.getValue();
        assertEquals("abc", capturedCar.getCarId());
        assertEquals("New Name", capturedCar.getCarName());
        assertEquals("Blue", capturedCar.getCarColor());
        assertEquals(5, capturedCar.getCarQuantity());
    }

    @Test
    void testDeleteCar() {
        // Call the controller method
        String redirect = carController.deleteCar("del-123");

        // Check redirect
        assertEquals("redirect:listCar", redirect);
        // Verify the service call
        verify(carService).deleteCarById("del-123");
    }
}
