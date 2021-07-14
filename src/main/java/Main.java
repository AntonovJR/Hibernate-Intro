import entities.Student;
import org.hibernate.FlushMode;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        //Create Hibernate Connection
        Configuration cfg = new Configuration();
        cfg.configure();
        // Create Session Factory
        SessionFactory sessionFactory = cfg.buildSessionFactory();
        //Create Session
        Session session = sessionFactory.openSession();

        // Persist an entity
       /* Student student = new Student("Gosho Tapanara");
        session.beginTransaction();
        session.save(student);
        session.getTransaction().commit();*/

        //Read entity by Id
        session.beginTransaction();
        session.setHibernateFlushMode(FlushMode.MANUAL);
        //Student result = session.get(Student.class, 3L, LockMode.READ);
        Optional<Student> result = session.byId(Student.class).loadOptional(10L);
        session.getTransaction().commit();
        System.out.printf("Student: %s", result.orElseGet(() -> null));

        //Read entity as list
        session.beginTransaction();
        session.createQuery("FROM Student", Student.class)
                .setFirstResult(1)
                .setMaxResults(3)
                .stream().forEach(System.out::println);
        session.getTransaction().commit();


        // Close Session
        session.close();

    }

}

