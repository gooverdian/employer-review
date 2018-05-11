package ru.hh.school.employerreview.specializations;

import org.apache.commons.lang3.StringUtils;
import ru.hh.school.employerreview.specializations.dto.ProfessionalFieldDto;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/specializations")
@Produces(MediaType.APPLICATION_JSON)
public class SpecializationsResource {

  private final SpecializationDao specializationDao;

  public SpecializationsResource(SpecializationDao specializationDao) {
    this.specializationDao = specializationDao;
  }

  @GET
  public List<ProfessionalFieldDto> getSpecializations(@QueryParam("text") String searchTerm) {

    List<Specialization> specializations;
    if (StringUtils.isBlank(searchTerm)) {
      specializations = specializationDao.getAll();
    } else {
      specializations = specializationDao.findSpecializations(searchTerm);
    }
    return ProfessionalFieldDto.specializationsToProfessionalFieldDtos(specializations);
  }
}
