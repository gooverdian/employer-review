package ru.hh.school.employerreview.position;

import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.hh.school.employerreview.HttpResourceTestBase;
import ru.hh.school.employerreview.position.dto.PositionDto;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class PositionResourceTest extends HttpResourceTestBase {

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
  public void getPositionsAllTest() throws IOException {
    List<PositionDto> positionDtos;
    positionDtos = OBJECT_MAPPER.readValue(new URL("http://localhost:8081/position"), new TypeReference<List<PositionDto>>() {
    });

    assertNotNull(positionDtos);
    assertEquals(4, positionDtos.size());

    List<String> positionNames = positionDtos.stream().map(positionDto -> positionDto.getName()).collect(Collectors.toList());

    assertTrue(positionNames.contains("Gardener"));
    assertTrue(positionNames.contains("Senior Gardener"));
    assertTrue(positionNames.contains("Developer"));
    assertTrue(positionNames.contains("Senior Developer"));
  }

  @Test
  public void getPositionsFilterTextTest() throws IOException {
    List<PositionDto> positionDtos;
    positionDtos = OBJECT_MAPPER.readValue(new URL("http://localhost:8081/position?text=gard"), new TypeReference<List<PositionDto>>() {
    });

    assertNotNull(positionDtos);
    assertEquals(2, positionDtos.size());

    List<String> positionNames = positionDtos.stream().map(positionDto -> positionDto.getName()).collect(Collectors.toList());

    assertTrue(positionNames.contains("Gardener"));
    assertTrue(positionNames.contains("Senior Gardener"));
  }

  @Test
  public void postPositionTest() throws IOException {
    String positionJson = "{\"name\":\"lawyer\"}";

    CloseableHttpClient httpClient = httpClient();
    HttpPost httpPost = new HttpPost("http://localhost:8081/position");
    HttpEntity entity = new ByteArrayEntity(positionJson.getBytes("UTF-8"));
    httpPost.setHeader("Content-Type", "application/json");
    httpPost.setEntity(entity);
    HttpResponse response = httpClient.execute(httpPost);

    String result = new BufferedReader(new InputStreamReader(response.getEntity().getContent())).lines().collect(Collectors.joining("\n"));
    PositionDto positionDto = OBJECT_MAPPER.readValue(result, PositionDto.class);

    Position position = positionDao.getByName("lawyer");
    assertNotNull(position);
    assertEquals("lawyer", position.getName());
    assertEquals(positionDto.getId(), position.getId());
  }

  @After
  public void removePositions() {
    List<Position> positions = positionDao.getAll();
    positions.forEach(position -> positionDao.delete(position));
  }
}
