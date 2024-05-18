package hiber.service;

import hiber.model.Car;
import hiber.model.User;

import java.util.List;

public interface UserService {
    User add(User user);
    List<User> listUsers();
    void addCar(Car car);
    public User getUserByCarModelAndSeries(String model, int series);

}
