package com.natureseyes.birdtheatre.client.birdtheatre.api;

import com.natureseyes.birdtheatre.client.birdtheatre.model.AuditEvent;

import java.util.*;
import feign.*;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2016-05-07T11:59:04.274-04:00")
public interface AuditresourceApi {


  /**
   * getByDates
   * 
   * @param fromDate fromDate
   * @param toDate toDate
   * @return List<AuditEvent>
   */
  @RequestLine("GET /api/audits?fromDate={fromDate}&toDate={toDate}")
  @Headers({
    "Content-type: application/json",
    "Accepts: application/json",
  })
  List<AuditEvent> getByDatesUsingGET(@Param("fromDate") String fromDate, @Param("toDate") String toDate);
  
  /**
   * get
   * 
   * @param id id
   * @return AuditEvent
   */
  @RequestLine("GET /api/audits/{id}")
  @Headers({
    "Content-type: application/json",
    "Accepts: application/json",
  })
  AuditEvent getUsingGET(@Param("id") Long id);
  
}
