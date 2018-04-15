package ru.hh.school.employerreview.filter;

import org.hibernate.HibernateException;
import ru.hh.errors.common.Errors;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
@Produces(MediaType.APPLICATION_JSON)
public class HibernateExceptionMapper extends Throwable implements ExceptionMapper<HibernateException> {
  @Override
  public Response toResponse(HibernateException e) {
    throw new Errors(Response.Status.INTERNAL_SERVER_ERROR, "INTERNAL_ERROR", "server").toWebApplicationException();
  }
}
