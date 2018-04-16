package ru.hh.school.employerreview;

import org.apache.commons.lang3.StringUtils;
import ru.hh.errors.common.Errors;

import javax.ws.rs.core.Response;

public class PaginationHelper {

  public static void checkInputParameters(String text, int page, int perPage) {
    Errors errors = new Errors(Response.Status.BAD_REQUEST);
    if (StringUtils.isEmpty(text)) {
      errors.add("BAD_REQUEST_PARAMETER", "empty test");
    }
    if (page < 0) {
      errors.add("BAD_REQUEST_PARAMETER", "page");
    }
    if (perPage <= 0) {
      errors.add("BAD_REQUEST_PARAMETER", "perPage");
    }
    if (errors.hasErrors()) {
      throw errors.toWebApplicationException();
    }
  }

  public static int calculatePagesCount(int rowCount, int perPage) {
    if (perPage == 0) {
      return -1;
    }
    return rowCount / perPage + (rowCount % perPage == 0 ? 0 : 1);
  }
}
