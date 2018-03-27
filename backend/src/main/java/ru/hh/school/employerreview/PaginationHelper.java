package ru.hh.school.employerreview;

public class PaginationHelper {

  public static int calculatePagesCount(int rowCount, int perPage) {
    if (perPage == 0) {
      return -1;
    }
    return rowCount / perPage + (rowCount % perPage == 0 ? 0 : 1);
  }
}
