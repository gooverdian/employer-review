package ru.hh.school.employerreview.position;

import org.apache.commons.lang3.StringUtils;
import ru.hh.school.employerreview.position.dto.PositionDto;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

@Path("/position")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PositionResource {

  private final PositionDao positionDao;

  public PositionResource(PositionDao positionDao) {
    this.positionDao = positionDao;
  }

  @GET
  public List<PositionDto> getPositions(@QueryParam("text") String searchTerm) {

    List<Position> positions;
    if (StringUtils.isBlank(searchTerm)) {
      positions = positionDao.getAll();
    } else {
      positions = positionDao.findPositions(searchTerm);
    }
    List<PositionDto> positionDtos = new ArrayList<>();
    positions.forEach(position -> positionDtos.add(PositionDto.positionToDto(position)));

    return positionDtos;
  }

  @POST
  public PositionDto postPosition(PositionDto positionDto) {
    if (positionDto == null) {
      return new PositionDto();
    }

    Position position = positionDao.getByName(positionDto.getName());
    if (position != null) {
      return PositionDto.positionToDto(position);
    } else {
      position = new Position(positionDto.getName());
      positionDao.save(position);
      return PositionDto.positionToDto(position);
    }
  }
}
