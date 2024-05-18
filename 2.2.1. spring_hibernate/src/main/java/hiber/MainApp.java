package hiber;

import hiber.config.AppConfig;
import hiber.model.Car;
import hiber.model.User;
import hiber.service.UserService;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;

@Component
public class MainApp {
   @Autowired
   private SessionFactory sessionFactory;


   public static void main(String[] args) throws SQLException {
      AnnotationConfigApplicationContext context = 
            new AnnotationConfigApplicationContext(AppConfig.class);

      MainApp mainApp = context.getBean(MainApp.class);
      UserService userService = context.getBean(UserService.class);



      userService.add(new User("User1", "Lastname1", "user1@mail.ru"));
      userService.add(new User("User2", "Lastname2", "user2@mail.ru"));
      userService.add(new User("User3", "Lastname3", "user3@mail.ru"));
      userService.add(new User("User4", "Lastname4", "user4@mail.ru"));


      // Создаем машины и устанавливаем их пользователям
      User userOne = new User("UserOne", "LastnameOne", "userOne@mail.ru");
      Car carOne = new Car("Volvo1", 111);
      userOne.setCar(carOne);

      User userTwo = new User("UserTwo", "LastnameTwo", "userTwo@mail.ru");
      Car carTwo = new Car("VolvoTwo", 222);
      userTwo.setCar(carTwo);

      Session session = mainApp.sessionFactory.openSession();
      session.getTransaction().begin();
      session.save(userOne);
      session.save(userTwo);
      session.getTransaction().commit();
      session.close();

      User user1 = userService.getUserByCarModelAndSeries("Volvo1", 111);
      User user2 = userService.getUserByCarModelAndSeries("VolvoTwo", 222);
      System.out.println(" У " + user1.getFirstName() + " - " + carOne.getModel());
      System.out.println(" У " + user2.getFirstName() + " - " + carTwo.getModel());

      List<User> users = userService.listUsers();
      for (User user : users) {
         System.out.println("Id = "+user.getId());
         System.out.println("First Name = "+user.getFirstName());
         System.out.println("Last Name = "+user.getLastName());
         System.out.println("Email = "+user.getEmail());
         Car car = user.getCar();
         if (car != null) {
            System.out.println("Car: " + car.getModel() + " - " + car.getSeries());
         } else {
            System.out.println("No car");
         }
         System.out.println();
      }

      context.close();
   }
}
