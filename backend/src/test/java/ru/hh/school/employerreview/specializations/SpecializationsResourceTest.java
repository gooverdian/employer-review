package ru.hh.school.employerreview.specializations;

import org.junit.Before;
import org.junit.Test;
import ru.hh.school.employerreview.downloader.AbstractDownloader;
import ru.hh.school.employerreview.downloader.SpecializationsDownloader;
import ru.hh.school.employerreview.specializations.dto.ProfessionalFieldDto;

import javax.inject.Inject;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class SpecializationsResourceTest extends SpecializationsCommonTest {

  @Inject
  protected SpecializationDao specializationDao;

  @Before
  public void prepareDb() {
    AbstractDownloader.setApplicationContext(applicationContext);
    SpecializationsDownloader.main();
    specializationDao = applicationContext.getBean(SpecializationDao.class);
  }

  @Test
  public void getSpecializationsTest() {
    SpecializationsResource specializationsResource = applicationContext.getBean(SpecializationsResource.class);

    List<ProfessionalFieldDto> professionalFieldDto = specializationsResource.getSpecializations("a");

    assertNotNull(professionalFieldDto);
    assertTrue(professionalFieldDto.size() > 0);
  }
}
