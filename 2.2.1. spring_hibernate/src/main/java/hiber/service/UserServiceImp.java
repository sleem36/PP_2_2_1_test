package hiber.service;

import hiber.dao.UserDao;
import hiber.model.Car;
import hiber.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImp implements UserService {

   @Autowired
   private UserDao userDao;

   @Transactional
   @Override
   public User add(User user) {
      userDao.add(user);
      return user;
   }

   @Transactional
   @Override
   public void addCar(Car car) {
      userDao.addCar(car);
   }
   @Transactional(readOnly = true)
   @Override
   public List<User> listUsers() {
      return userDao.listUsers();
   }

   private List<User> userList;

   @Transactional
   public User getUserByCarModelAndSeries(String model, int series) {
      return userDao.getUserByCarModelAndSeries(model, series);
   }



}
