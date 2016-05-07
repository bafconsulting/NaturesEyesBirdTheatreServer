package com.natureseyes.birdtheatre.client.birdtheatre.api;

import com.natureseyes.birdtheatre.client.birdtheatre.model.User;
import com.natureseyes.birdtheatre.client.birdtheatre.model.ManagedUserDTO;
import com.natureseyes.birdtheatre.client.birdtheatre.model.InlineResponse200;

import java.util.*;
import feign.*;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2016-05-07T11:59:04.274-04:00")
public interface UserresourceApi {


  /**
   * search
   * 
   * @param query query
   * @return List<User>
   */
  @RequestLine("GET /api/_search/users/{query}")
  @Headers({
    "Content-type: application/json",
    "Accepts: application/json",
  })
  List<User> searchUsingGET(@Param("query") String query);
  
  /**
   * getAllUsers
   * 
   * @return List<ManagedUserDTO>
   */
  @RequestLine("GET /api/users")
  @Headers({
    "Content-type: application/json",
    "Accepts: application/json",
  })
  List<ManagedUserDTO> getAllUsersUsingGET();
  
  /**
   * updateUser
   * 
   * @param managedUserDTO managedUserDTO
   * @return ManagedUserDTO
   */
  @RequestLine("PUT /api/users")
  @Headers({
    "Content-type: application/json",
    "Accepts: application/json",
  })
  ManagedUserDTO updateUserUsingPUT(ManagedUserDTO managedUserDTO);
  
  /**
   * createUser
   * 
   * @param managedUserDTO managedUserDTO
   * @return InlineResponse200
   */
  @RequestLine("POST /api/users")
  @Headers({
    "Content-type: application/json",
    "Accepts: application/json",
  })
  InlineResponse200 createUserUsingPOST(ManagedUserDTO managedUserDTO);
  
  /**
   * getUser
   * 
   * @param login login
   * @return ManagedUserDTO
   */
  @RequestLine("GET /api/users/{login}")
  @Headers({
    "Content-type: application/json",
    "Accepts: application/json",
  })
  ManagedUserDTO getUserUsingGET(@Param("login") String login);
  
  /**
   * deleteUser
   * 
   * @param login login
   * @return void
   */
  @RequestLine("DELETE /api/users/{login}")
  @Headers({
    "Content-type: application/json",
    "Accepts: application/json",
  })
  void deleteUserUsingDELETE(@Param("login") String login);
  
}
