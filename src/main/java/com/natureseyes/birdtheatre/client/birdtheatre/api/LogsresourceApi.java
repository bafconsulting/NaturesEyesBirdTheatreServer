package com.natureseyes.birdtheatre.client.birdtheatre.api;

import com.natureseyes.birdtheatre.client.birdtheatre.model.LoggerDTO;

import java.util.*;
import feign.*;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2016-05-07T11:59:04.274-04:00")
public interface LogsresourceApi {


  /**
   * getList
   * 
   * @return List<LoggerDTO>
   */
  @RequestLine("GET /api/logs")
  @Headers({
    "Content-type: application/json",
    "Accepts: application/json",
  })
  List<LoggerDTO> getListUsingGET();
  
  /**
   * changeLevel
   * 
   * @param jsonLogger jsonLogger
   * @return void
   */
  @RequestLine("PUT /api/logs")
  @Headers({
    "Content-type: application/json",
    "Accepts: */*",
  })
  void changeLevelUsingPUT(LoggerDTO jsonLogger);
  
}
