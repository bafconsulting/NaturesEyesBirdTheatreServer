package com.natureseyes.birdtheatre.client.birdtheatre.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.ZonedDateTime;
import java.util.*;





@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2016-05-07T11:59:04.274-04:00")
public class ManagedUserDTO   {
  
  private Boolean activated = null;
  private List<String> authorities = new ArrayList<String>();
  private ZonedDateTime createdDate = null;
  private String email = null;
  private String firstName = null;
  private Long id = null;
  private String langKey = null;
  private String lastModifiedBy = null;
  private ZonedDateTime lastModifiedDate = null;
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
  @JsonProperty("createdDate")
  public ZonedDateTime getCreatedDate() {
    return createdDate;
  }
  public void setCreatedDate(ZonedDateTime createdDate) {
    this.createdDate = createdDate;
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
  @JsonProperty("id")
  public Long getId() {
    return id;
  }
  public void setId(Long id) {
    this.id = id;
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
  @JsonProperty("lastModifiedBy")
  public String getLastModifiedBy() {
    return lastModifiedBy;
  }
  public void setLastModifiedBy(String lastModifiedBy) {
    this.lastModifiedBy = lastModifiedBy;
  }

  
  /**
   **/
  
  @ApiModelProperty(value = "")
  @JsonProperty("lastModifiedDate")
  public ZonedDateTime getLastModifiedDate() {
    return lastModifiedDate;
  }
  public void setLastModifiedDate(ZonedDateTime lastModifiedDate) {
    this.lastModifiedDate = lastModifiedDate;
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
    ManagedUserDTO managedUserDTO = (ManagedUserDTO) o;

    return true && Objects.equals(activated, managedUserDTO.activated) &&
        Objects.equals(authorities, managedUserDTO.authorities) &&
        Objects.equals(createdDate, managedUserDTO.createdDate) &&
        Objects.equals(email, managedUserDTO.email) &&
        Objects.equals(firstName, managedUserDTO.firstName) &&
        Objects.equals(id, managedUserDTO.id) &&
        Objects.equals(langKey, managedUserDTO.langKey) &&
        Objects.equals(lastModifiedBy, managedUserDTO.lastModifiedBy) &&
        Objects.equals(lastModifiedDate, managedUserDTO.lastModifiedDate) &&
        Objects.equals(lastName, managedUserDTO.lastName) &&
        Objects.equals(login, managedUserDTO.login) &&
        Objects.equals(password, managedUserDTO.password)
    ;
  }

  @Override
  public int hashCode() {
    return Objects.hash(activated, authorities, createdDate, email, firstName, id, langKey, lastModifiedBy, lastModifiedDate, lastName, login, password);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ManagedUserDTO {\n");
    
    sb.append("    activated: ").append(toIndentedString(activated)).append("\n");
    sb.append("    authorities: ").append(toIndentedString(authorities)).append("\n");
    sb.append("    createdDate: ").append(toIndentedString(createdDate)).append("\n");
    sb.append("    email: ").append(toIndentedString(email)).append("\n");
    sb.append("    firstName: ").append(toIndentedString(firstName)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    langKey: ").append(toIndentedString(langKey)).append("\n");
    sb.append("    lastModifiedBy: ").append(toIndentedString(lastModifiedBy)).append("\n");
    sb.append("    lastModifiedDate: ").append(toIndentedString(lastModifiedDate)).append("\n");
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

