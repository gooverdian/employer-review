package ru.hh.school.employerreview.downloader;

import com.fasterxml.jackson.core.type.TypeReference;
import ru.hh.school.employerreview.downloader.json.ProfessionalFieldJson;
import ru.hh.school.employerreview.downloader.json.SpecializationJson;
import ru.hh.school.employerreview.specializations.ProfessionalField;
import ru.hh.school.employerreview.specializations.ProfessionalFieldDao;
import ru.hh.school.employerreview.specializations.Specialization;
import ru.hh.school.employerreview.specializations.SpecializationDao;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class DownloadSpecializations extends DownloadAbstract {
  private static final String URL_SPECIALIZATIONS = "https://api.hh.ru/specializations";

  public static void main(String... args) {
    init();

    ProfessionalFieldDao professionalFieldDao = applicationContext.getBean(ProfessionalFieldDao.class);
    SpecializationDao specializationDao = applicationContext.getBean(SpecializationDao.class);

    try {
      List<ProfessionalFieldJson> proffFields;
      proffFields = OBJECT_MAPPER.readValue(new URL(URL_SPECIALIZATIONS), new TypeReference<List<ProfessionalFieldJson>>() {
      });

      for (ProfessionalFieldJson proffFieldJson : proffFields) {
        ProfessionalField professionalField = new ProfessionalField(proffFieldJson.getId(), proffFieldJson.getName());
        professionalFieldDao.save(professionalField);

        for (SpecializationJson specializationJson : proffFieldJson.getSpecializations()) {
          Specialization specialization = new Specialization(specializationJson.getId(), specializationJson.getName(), professionalField);
          specializationDao.save(specialization);
        }
      }

    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
