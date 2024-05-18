package hiber.dao;

import hiber.model.Car;
import hiber.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

   @Autowired
   private SessionFactory sessionFactory;

   @Override
   public void add(User user) {
      sessionFactory.getCurrentSession().save(user);
   }
   @Override
   public void addCar(Car car) {
      sessionFactory.getCurrentSession().save(car);
   }

   @Override
   @SuppressWarnings("unchecked")
   public List<User> listUsers() {
      TypedQuery<User> query=sessionFactory.getCurrentSession().createQuery("from User");
      return query.getResultList();
   }

   @PersistenceContext
   private EntityManager entityManager;

   public User getUserByCarModelAndSeries(String model, int series) {
      Session session = entityManager.unwrap(Session.class);
      String hql = "SELECT u FROM User u JOIN u.car c WHERE c.model = :model AND c.series = :series";
      Query<User> query = session.createQuery(hql, User.class);
      query.setParameter("model", model);
      query.setParameter("series", series);
      List<User> users = query.getResultList();
      return users.isEmpty() ? null : users.get(0);
   }

}
