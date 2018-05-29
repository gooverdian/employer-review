package ru.hh.school.employerreview.downloader;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import ru.hh.school.employerreview.area.Area;
import ru.hh.school.employerreview.area.AreaDao;
import ru.hh.school.employerreview.downloader.dto.AreaJson;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Данный класс предназначен для консистентонй загрузки в бд одного региона (со всеми родителями)
 * ID региона, который будет загружен передается как аргумент area в main()
 * Если area не передан, бертся дефолтное значение area=1
 */
public class AreaDownloader extends AbstractDownloader {
  private final static Logger LOGGER = Logger.getLogger(AreaDownloader.class.getName());
  private static final String URL_AREAS = "https://api.hh.ru/areas";
  private static final Integer DEFAULT_AREA_ID = 1;

  public static void main(String... args) throws IOException {
    init();

    AreaDao areaDao = applicationContext.getBean(AreaDao.class);

    List<Integer> areasIdsToLoad = new ArrayList<>();
    for (String arg : args) {
      if (arg.contains("area")) {
        String[] items = arg.split("=");
        if (items.length > 1) {
          try {
            Integer areaId = Integer.parseInt(items[1]);
            areasIdsToLoad.add(areaId);
            LOGGER.info("Area id added - " + areaId);
          } catch (NumberFormatException e) {
            LOGGER.warning(e.getMessage());
          }
        }
      }
    }

    if (areasIdsToLoad.size() == 0) {
      areasIdsToLoad.add(DEFAULT_AREA_ID);
    }

    int totalAreasInserted = 0;
    for (Integer areaId : areasIdsToLoad) {

      Map<Integer, Area> areasIds = new HashMap<>();
      AreaJson[] areaJsons = OBJECT_MAPPER.readValue(new URL(URL_AREAS), AreaJson[].class);
      parseNestedAreas(areaJsons, areasIds);

      LinkedList<Area> areaQueue = new LinkedList<>();
      areaQueue.addLast(areasIds.get(areaId));
      while (areasIds.get(areaId).getParentId() != 0) {
        areaId = areasIds.get(areaId).getParentId();
        areaQueue.addLast(areasIds.get(areaId));
      }

      int recordsCount = 0;
      while (!areaQueue.isEmpty()) {
        Area area = areaQueue.poll();
        LOGGER.info("Processing area - " + area.getName());
        try {
          areaDao.save(area);
          recordsCount++;
        } catch (ConstraintViolationException | DataIntegrityViolationException e) {
          LOGGER.info("Area duplicate key - " + area.getId());
        }
      }
      totalAreasInserted += recordsCount;
    }
    LOGGER.info("Areas inserted - " + totalAreasInserted);
  }

  private static void parseNestedAreas(AreaJson[] areaJsons, Map<Integer, Area> areasIds) {
    for (AreaJson areaJson : areaJsons) {
      Area area = areaJson.toArea();
      areasIds.put(area.getId(), area);
      if (areaJson.getAreas() != null) {
        parseNestedAreas(areaJson.getAreas(), areasIds);
      }
    }
  }
}
