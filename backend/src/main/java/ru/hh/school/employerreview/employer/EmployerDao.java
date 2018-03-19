package ru.hh.school.employerreview.employer;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import static java.util.Objects.requireNonNull;

public class EmployerDao {

  private final SessionFactory sessionFactory;

  public EmployerDao(SessionFactory sessionFactory) {
    this.sessionFactory = requireNonNull(sessionFactory);
  }

  @Transactional
  public Employer getByHhId(int hhId){
    String hql = "FROM Employer E WHERE E.hhId = " + hhId;
    Query query = getSession().createQuery(hql);
    List<Employer> results = query.list();
    if (results.isEmpty())
      return null;
    else
      return (Employer) results.get(0);
  }

  @Transactional
  public void save(List<Employer> employers) {
    for (Employer employer: employers) {
      Employer employerFromDB = getByHhId(employer.getHhId());
      if (employerFromDB == null) {
        getSession().save(employer);
      }else if (employer.getAreaId() > employerFromDB.getAreaId()){
        employerFromDB.setAreaId(employer.getAreaId());
        getSession().update(employerFromDB);
      }
    }
  }

  @Transactional
  public int truncate(){
    String hql = String.format("delete from Employer");
    Query query = getSession().createQuery(hql);
    return query.executeUpdate();
  }

  private Session getSession() {
    return sessionFactory.getCurrentSession();
  }
}
