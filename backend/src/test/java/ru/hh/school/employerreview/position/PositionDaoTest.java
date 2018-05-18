package ru.hh.school.employerreview.position;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.hh.school.employerreview.EmployerReviewTestBase;
import ru.hh.school.employerreview.TestConfig;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class PositionDaoTest extends EmployerReviewTestBase {

  @Inject
  private PositionDao positionDao;

  @Inject
  protected TestConfig.TestQueryExecutorDao testQueryExecutorDao;

  @Before
  public void createPositions() {
    Position position01 = new Position("Gardener");
    Position position02 = new Position("Senior Gardener");
    Position position03 = new Position("Developer");
    Position position04 = new Position("Senior Developer");

    positionDao.save(position01);
    positionDao.save(position02);
    positionDao.save(position03);
    positionDao.save(position04);
  }

  @Test
  public void findPositionsTest() {

    List<Position> positions = positionDao.findPositions("gard");
    List<String> positionNames = positions.stream().map(position -> position.getName()).collect(Collectors.toList());

    assertNotNull(positions);
    assertEquals(2, positions.size());
    assertTrue(positionNames.contains("Gardener"));
    assertTrue(positionNames.contains("Senior Gardener"));

    positions = positionDao.findPositions("ENI");
    positionNames = positions.stream().map(position -> position.getName()).collect(Collectors.toList());

    assertNotNull(positions);
    assertEquals(2, positions.size());
    assertTrue(positionNames.contains("Senior Gardener"));
    assertTrue(positionNames.contains("Senior Developer"));
  }

  @Test
  public void getByNameTest() {
    Position position = positionDao.getByName("gardener");

    assertNotNull(position);
    assertEquals("Gardener", position.getName());
  }

  @Test
  public void getByNameNullTest() {
    Position position = positionDao.getByName("Senior");
    assertNull(position);
  }

  @After
  public void deleteEntities() {
    testQueryExecutorDao.executeQuery("delete from Position");
  }
}
