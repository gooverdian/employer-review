package ru.hh.school.employerreview.specializations;

import org.junit.Test;
import ru.hh.school.employerreview.specializations.dto.ProfessionalFieldDto;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class SpecializationsResourceTest extends SpecializationsCommonTest {

  @Test
  public void getSpecializationsTest() {
    SpecializationsResource specializationsResource = applicationContext.getBean(SpecializationsResource.class);

    List<ProfessionalFieldDto> professionalFieldDto = specializationsResource.getSpecializations("a");

    assertNotNull(professionalFieldDto);
    assertTrue(professionalFieldDto.size() > 0);
  }
}
