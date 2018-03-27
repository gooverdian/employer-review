package ru.hh.school.employerreview.filter;

import org.glassfish.jersey.server.ParamException;
import ru.hh.errors.common.Errors;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
@Produces(MediaType.APPLICATION_JSON)
public class QueryParamExceptionMapper extends Throwable implements ExceptionMapper<ParamException.QueryParamException> {
  @Override
  public Response toResponse(ParamException.QueryParamException e) {
    throw new Errors(Response.Status.BAD_REQUEST, "BAD_REQUEST_PARAMETER", "parser").toWebApplicationException();
  }
}
