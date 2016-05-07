package com.natureseyes.birdtheatre.client.birdtheatre.api;

import com.natureseyes.birdtheatre.client.birdtheatre.model.BroadcastStream;

import java.util.*;
import feign.*;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2016-05-07T11:59:04.274-04:00")
public interface BroadcaststreamresourceApi {


  /**
   * searchBroadcastStreams
   * 
   * @param query query
   * @return List<BroadcastStream>
   */
  @RequestLine("GET /api/_search/broadcast-streams?query={query}")
  @Headers({
    "Content-type: application/json",
    "Accepts: application/json",
  })
  List<BroadcastStream> searchBroadcastStreamsUsingGET(@Param("query") String query);
  
  /**
   * getAllBroadcastStreams
   * 
   * @return List<BroadcastStream>
   */
  @RequestLine("GET /api/broadcast-streams")
  @Headers({
    "Content-type: application/json",
    "Accepts: application/json",
  })
  List<BroadcastStream> getAllBroadcastStreamsUsingGET();
  
  /**
   * updateBroadcastStream
   * 
   * @param broadcastStream broadcastStream
   * @return BroadcastStream
   */
  @RequestLine("PUT /api/broadcast-streams")
  @Headers({
    "Content-type: application/json",
    "Accepts: application/json",
  })
  BroadcastStream updateBroadcastStreamUsingPUT(BroadcastStream broadcastStream);
  
  /**
   * createBroadcastStream
   * 
   * @param broadcastStream broadcastStream
   * @return BroadcastStream
   */
  @RequestLine("POST /api/broadcast-streams")
  @Headers({
    "Content-type: application/json",
    "Accepts: application/json",
  })
  BroadcastStream createBroadcastStreamUsingPOST(BroadcastStream broadcastStream);
  
  /**
   * getBroadcastStream
   * 
   * @param id id
   * @return BroadcastStream
   */
  @RequestLine("GET /api/broadcast-streams/{id}")
  @Headers({
    "Content-type: application/json",
    "Accepts: application/json",
  })
  BroadcastStream getBroadcastStreamUsingGET(@Param("id") Long id);
  
  /**
   * deleteBroadcastStream
   * 
   * @param id id
   * @return void
   */
  @RequestLine("DELETE /api/broadcast-streams/{id}")
  @Headers({
    "Content-type: application/json",
    "Accepts: application/json",
  })
  void deleteBroadcastStreamUsingDELETE(@Param("id") Long id);
  
}
