package ru.hh.school.employerreview.downloader;

import com.fasterxml.jackson.core.type.TypeReference;
import org.hibernate.exception.ConstraintViolationException;
import ru.hh.school.employerreview.downloader.dto.ProfessionalFieldJson;
import ru.hh.school.employerreview.downloader.dto.SpecializationJson;
import ru.hh.school.employerreview.specializations.ProfessionalField;
import ru.hh.school.employerreview.specializations.ProfessionalFieldDao;
import ru.hh.school.employerreview.specializations.Specialization;
import ru.hh.school.employerreview.specializations.SpecializationDao;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.logging.Logger;

public class SpecializationsDownloader extends AbstractDownloader {
  private static final String URL_SPECIALIZATIONS = "https://api.hh.ru/specializations";
  private final static Logger LOGGER = Logger.getLogger(SpecializationsDownloader.class.getName());

  public static void main(String... args) {
    init();

    ProfessionalFieldDao professionalFieldDao = applicationContext.getBean(ProfessionalFieldDao.class);
    SpecializationDao specializationDao = applicationContext.getBean(SpecializationDao.class);

    try {
      List<ProfessionalFieldJson> profFields;
      profFields = OBJECT_MAPPER.readValue(new URL(URL_SPECIALIZATIONS), new TypeReference<List<ProfessionalFieldJson>>() {
      });

      int profFieldRecordsCount = 0;
      int specializationRecordsCount = 0;

      for (ProfessionalFieldJson profFieldJson : profFields) {
        ProfessionalField professionalField = new ProfessionalField(profFieldJson.getId(), profFieldJson.getName());
        try {
          professionalFieldDao.save(professionalField);
          profFieldRecordsCount++;
        } catch (ConstraintViolationException e) {
          LOGGER.info("Professional Field duplicate key - " + profFieldJson.getId());
        }

        for (SpecializationJson specializationJson : profFieldJson.getSpecializations()) {
          Specialization specialization = new Specialization(specializationJson.getId(), specializationJson.getName(), professionalField);
          try {
            specializationDao.save(specialization);
            specializationRecordsCount++;
          } catch (ConstraintViolationException e) {
            LOGGER.info("Specialization duplicate key - " + specializationJson.getId());
          }
        }
      }

      LOGGER.info("Professional Fields inserted - " + profFieldRecordsCount);
      LOGGER.info("Specializations inserted - " + specializationRecordsCount);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
