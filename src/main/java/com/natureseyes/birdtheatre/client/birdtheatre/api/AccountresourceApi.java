package com.natureseyes.birdtheatre.client.birdtheatre.api;

import com.natureseyes.birdtheatre.client.birdtheatre.model.UserDTO;
import com.natureseyes.birdtheatre.client.birdtheatre.model.InlineResponse200;
import com.natureseyes.birdtheatre.client.birdtheatre.model.KeyAndPasswordDTO;
import com.natureseyes.birdtheatre.client.birdtheatre.model.PersistentToken;

import java.util.*;
import feign.*;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2016-05-07T11:59:04.274-04:00")
public interface AccountresourceApi {


  /**
   * getAccount
   * 
   * @return UserDTO
   */
  @RequestLine("GET /api/account")
  @Headers({
    "Content-type: application/json",
    "Accepts: application/json",
  })
  UserDTO getAccountUsingGET();
  
  /**
   * saveAccount
   * 
   * @param userDTO userDTO
   * @return String
   */
  @RequestLine("POST /api/account")
  @Headers({
    "Content-type: application/json",
    "Accepts: application/json",
  })
  String saveAccountUsingPOST(UserDTO userDTO);
  
  /**
   * changePassword
   * 
   * @param password password
   * @return InlineResponse200
   */
  @RequestLine("POST /api/account/change_password")
  @Headers({
    "Content-type: application/json",
    "Accepts: text/plain",
  })
  InlineResponse200 changePasswordUsingPOST(String password);
  
  /**
   * finishPasswordReset
   * 
   * @param keyAndPassword keyAndPassword
   * @return String
   */
  @RequestLine("POST /api/account/reset_password/finish")
  @Headers({
    "Content-type: application/json",
    "Accepts: text/plain",
  })
  String finishPasswordResetUsingPOST(KeyAndPasswordDTO keyAndPassword);
  
  /**
   * requestPasswordReset
   * 
   * @param mail mail
   * @return InlineResponse200
   */
  @RequestLine("POST /api/account/reset_password/init")
  @Headers({
    "Content-type: application/json",
    "Accepts: text/plain",
  })
  InlineResponse200 requestPasswordResetUsingPOST(String mail);
  
  /**
   * getCurrentSessions
   * 
   * @return List<PersistentToken>
   */
  @RequestLine("GET /api/account/sessions")
  @Headers({
    "Content-type: application/json",
    "Accepts: application/json",
  })
  List<PersistentToken> getCurrentSessionsUsingGET();
  
  /**
   * invalidateSession
   * 
   * @param series series
   * @return void
   */
  @RequestLine("DELETE /api/account/sessions/{series}")
  @Headers({
    "Content-type: application/json",
    "Accepts: */*",
  })
  void invalidateSessionUsingDELETE(@Param("series") String series);
  
  /**
   * activateAccount
   * 
   * @param key key
   * @return String
   */
  @RequestLine("GET /api/activate?key={key}")
  @Headers({
    "Content-type: application/json",
    "Accepts: application/json",
  })
  String activateAccountUsingGET(@Param("key") String key);
  
  /**
   * isAuthenticated
   * 
   * @return String
   */
  @RequestLine("GET /api/authenticate")
  @Headers({
    "Content-type: application/json",
    "Accepts: application/json",
  })
  String isAuthenticatedUsingGET();
  
  /**
   * registerAccount
   * 
   * @param userDTO userDTO
   * @return InlineResponse200
   */
  @RequestLine("POST /api/register")
  @Headers({
    "Content-type: application/json",
    "Accepts: application/json",
  })
  InlineResponse200 registerAccountUsingPOST(UserDTO userDTO);
  
}
