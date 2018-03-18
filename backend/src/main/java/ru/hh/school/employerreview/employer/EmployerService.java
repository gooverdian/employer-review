package ru.hh.school.employerreview.employer;

import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

import static java.util.Objects.requireNonNull;

//@Service
public class EmployerService {

  private final SessionFactory sessionFactory;

  //@Autowired
  private final EmployerDao employerDao;

  public EmployerService(SessionFactory sessionFactory, EmployerDao employerDao) {
    this.sessionFactory = requireNonNull(sessionFactory);
    this.employerDao = requireNonNull(employerDao);
  }

  @Transactional
  public void save(Employer employer) {    employerDao.save(employer); }

  @Transactional
  public Optional<Employer> get(int employerId) {    return employerDao.get(employerId); }

  @Transactional
  public Set<Employer> getAll() {  return  employerDao.getAll();  }

  @Transactional
  public void update(Employer employer) {   employerDao.update(employer); }

  @Transactional
  public void changeName(int employerId, String name) {
   Optional<Employer> optionalEmployer = employerDao.get(employerId);
      if (!optionalEmployer.isPresent()) {
        throw new IllegalArgumentException("there is no employer with id " + employerId);
      }
      optionalEmployer.get().setName(name);
  }

  @Transactional
  public void delete(int employerId) { employerDao.delete(employerId); }

}

