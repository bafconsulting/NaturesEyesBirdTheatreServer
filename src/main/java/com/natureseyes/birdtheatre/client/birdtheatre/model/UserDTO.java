package com.natureseyes.birdtheatre.client.birdtheatre.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.*;





@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2016-05-07T11:59:04.274-04:00")
public class UserDTO   {
  
  private Boolean activated = null;
  private List<String> authorities = new ArrayList<String>();
  private String email = null;
  private String firstName = null;
  private String langKey = null;
  private String lastName = null;
  private String login = null;
  private String password = null;

  
  /**
   **/
  
  @ApiModelProperty(value = "")
  @JsonProperty("activated")
  public Boolean getActivated() {
    return activated;
  }
  public void setActivated(Boolean activated) {
    this.activated = activated;
  }

  
  /**
   **/
  
  @ApiModelProperty(value = "")
  @JsonProperty("authorities")
  public List<String> getAuthorities() {
    return authorities;
  }
  public void setAuthorities(List<String> authorities) {
    this.authorities = authorities;
  }

  
  /**
   **/
  
  @ApiModelProperty(value = "")
  @JsonProperty("email")
  public String getEmail() {
    return email;
  }
  public void setEmail(String email) {
    this.email = email;
  }

  
  /**
   **/
  
  @ApiModelProperty(value = "")
  @JsonProperty("firstName")
  public String getFirstName() {
    return firstName;
  }
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  
  /**
   **/
  
  @ApiModelProperty(value = "")
  @JsonProperty("langKey")
  public String getLangKey() {
    return langKey;
  }
  public void setLangKey(String langKey) {
    this.langKey = langKey;
  }

  
  /**
   **/
  
  @ApiModelProperty(value = "")
  @JsonProperty("lastName")
  public String getLastName() {
    return lastName;
  }
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  
  /**
   **/
  
  @ApiModelProperty(value = "")
  @JsonProperty("login")
  public String getLogin() {
    return login;
  }
  public void setLogin(String login) {
    this.login = login;
  }

  
  /**
   **/
  
  @ApiModelProperty(value = "")
  @JsonProperty("password")
  public String getPassword() {
    return password;
  }
  public void setPassword(String password) {
    this.password = password;
  }

  

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UserDTO userDTO = (UserDTO) o;

    return true && Objects.equals(activated, userDTO.activated) &&
        Objects.equals(authorities, userDTO.authorities) &&
        Objects.equals(email, userDTO.email) &&
        Objects.equals(firstName, userDTO.firstName) &&
        Objects.equals(langKey, userDTO.langKey) &&
        Objects.equals(lastName, userDTO.lastName) &&
        Objects.equals(login, userDTO.login) &&
        Objects.equals(password, userDTO.password)
    ;
  }

  @Override
  public int hashCode() {
    return Objects.hash(activated, authorities, email, firstName, langKey, lastName, login, password);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UserDTO {\n");
    
    sb.append("    activated: ").append(toIndentedString(activated)).append("\n");
    sb.append("    authorities: ").append(toIndentedString(authorities)).append("\n");
    sb.append("    email: ").append(toIndentedString(email)).append("\n");
    sb.append("    firstName: ").append(toIndentedString(firstName)).append("\n");
    sb.append("    langKey: ").append(toIndentedString(langKey)).append("\n");
    sb.append("    lastName: ").append(toIndentedString(lastName)).append("\n");
    sb.append("    login: ").append(toIndentedString(login)).append("\n");
    sb.append("    password: ").append(toIndentedString(password)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

