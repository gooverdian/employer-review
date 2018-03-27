package ru.hh.school.employerreview.filter;

import ru.hh.errors.common.Errors;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.sql.SQLException;

@Provider
@Produces(MediaType.APPLICATION_JSON)
public class SqlExceptionMapper extends Throwable implements ExceptionMapper<SQLException> {
  @Override
  public Response toResponse(SQLException e) {
    throw new Errors(Response.Status.INTERNAL_SERVER_ERROR, "INTERNAL_ERROR", "server").toWebApplicationException();
  }
}
