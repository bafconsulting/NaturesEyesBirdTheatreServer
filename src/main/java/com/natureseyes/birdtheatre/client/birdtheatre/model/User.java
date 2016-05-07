package com.natureseyes.birdtheatre.client.birdtheatre.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.ZonedDateTime;





@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2016-05-07T11:59:04.274-04:00")
public class User   {
  
  private Boolean activated = null;
  private String email = null;
  private String firstName = null;
  private Long id = null;
  private String langKey = null;
  private String lastName = null;
  private String login = null;
  private ZonedDateTime resetDate = null;
  private String resetKey = null;

  
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
  @JsonProperty("resetDate")
  public ZonedDateTime getResetDate() {
    return resetDate;
  }
  public void setResetDate(ZonedDateTime resetDate) {
    this.resetDate = resetDate;
  }

  
  /**
   **/
  
  @ApiModelProperty(value = "")
  @JsonProperty("resetKey")
  public String getResetKey() {
    return resetKey;
  }
  public void setResetKey(String resetKey) {
    this.resetKey = resetKey;
  }

  

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    User user = (User) o;

    return true && Objects.equals(activated, user.activated) &&
        Objects.equals(email, user.email) &&
        Objects.equals(firstName, user.firstName) &&
        Objects.equals(id, user.id) &&
        Objects.equals(langKey, user.langKey) &&
        Objects.equals(lastName, user.lastName) &&
        Objects.equals(login, user.login) &&
        Objects.equals(resetDate, user.resetDate) &&
        Objects.equals(resetKey, user.resetKey)
    ;
  }

  @Override
  public int hashCode() {
    return Objects.hash(activated, email, firstName, id, langKey, lastName, login, resetDate, resetKey);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class User {\n");
    
    sb.append("    activated: ").append(toIndentedString(activated)).append("\n");
    sb.append("    email: ").append(toIndentedString(email)).append("\n");
    sb.append("    firstName: ").append(toIndentedString(firstName)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    langKey: ").append(toIndentedString(langKey)).append("\n");
    sb.append("    lastName: ").append(toIndentedString(lastName)).append("\n");
    sb.append("    login: ").append(toIndentedString(login)).append("\n");
    sb.append("    resetDate: ").append(toIndentedString(resetDate)).append("\n");
    sb.append("    resetKey: ").append(toIndentedString(resetKey)).append("\n");
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

