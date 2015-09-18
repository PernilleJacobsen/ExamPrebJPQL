/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpqldemo;

import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Pernille
 */
public class JpqlDemo
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpqlDemoPU");
        EntityManager em = emf.createEntityManager();
        //Find all Students in the system 
        System.out.println("all students: "+em.createQuery("SELECT s FROM Student s").getResultList());
        //Find all Students in the System with the first Name jan 
        System.out.println("students firstname Jan: "+em.createQuery("SELECT s FROM Student s WHERE(s.firstname='Jan')").getResultList());
        //Find all Students in the system with the last name Olsen 
        System.out.println("students lastname Olsen: "+em.createQuery("SELECT s FROM Student s WHERE(s.lastname='Olsen')").getResultList());
        //Find the total sum of study point scores, for a student given the student id. 
        //virker ikke efter hensigten
        Student s = (Student)em.createQuery("SELECT s FROM Student s WHERE s.id = 2").getSingleResult();
        System.out.println("her er den"+s.getFirstname());
        Collection<Studypoint> stp = s.getStudypointCollection();
        
        System.out.println("studypoint for student id 2: "+stp.size());
        
        
        //Find the total sum of studypoint scores, given to all students. 
        System.out.println("samlet sum af studypoints er: "+em.createQuery("SELECT SUM (s.score) FROM stydypoint s").getSingleResult());  
        // Find those students that has the greatest total of studypoint scores 
        System.out.println("Student with greatest total of studypoint: "+ em.createQuery("SELECT s FROM Student s WHERE(size(s.studypointCollection)) = SELECT MAX(size(ss.studypointCollection)) FROM Student ss").getResultList());
               
        //Find those students that has the lowest total of studypoint scores 
        System.out.println("Student with lowest total of studypoint: "+ em.createQuery("SELECT s FROM Student s WHERE(size(s.studypointCollection)) = SELECT MIN(size(ss.studypointCollection)) FROM Student ss").getResultList());
        //Create a method to create new Students and test the method 
        JpqlDemo jpql = new JpqlDemo();
        System.out.println("ny student er: "+jpql.createStudent());
  
        //Add a method to the Student class addStudyPoint(..) and test the method 
        
        Studypoint sp = new Studypoint();
        sp.setScore(4);
        sp.setDescription("opgaver");
        sp.setMaxval(5);
        sp.setStudent(em.find(Student.class, 1));
        addStudyPoint(sp);
       
        
    }
    public Student createStudent()
    {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpqlDemoPU");
        EntityManager em = emf.createEntityManager();
        
        Student s = new Student();
        s.setFirstname("Marta");
        s.setLastname("Miszyk");
        em.getTransaction().begin();
        em.persist(s);
        em.getTransaction().commit();
        return em.find(Student.class, s.getId());
        
    }
    public static void addStudyPoint(Studypoint sp)
    {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpqlDemoPU");
        EntityManager em = emf.createEntityManager();
        
            em.getTransaction().begin();
            em.persist(sp);
            em.getTransaction().commit();
            System.out.println("point tilføjet");
        
    }
    
}
