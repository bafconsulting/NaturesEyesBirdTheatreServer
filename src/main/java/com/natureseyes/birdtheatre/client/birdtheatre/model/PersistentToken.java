package com.natureseyes.birdtheatre.client.birdtheatre.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;





@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2016-05-07T11:59:04.274-04:00")
public class PersistentToken   {
  
  private String formattedTokenDate = null;
  private String ipAddress = null;
  private String series = null;
  private String userAgent = null;

  
  /**
   **/
  
  @ApiModelProperty(value = "")
  @JsonProperty("formattedTokenDate")
  public String getFormattedTokenDate() {
    return formattedTokenDate;
  }
  public void setFormattedTokenDate(String formattedTokenDate) {
    this.formattedTokenDate = formattedTokenDate;
  }

  
  /**
   **/
  
  @ApiModelProperty(value = "")
  @JsonProperty("ipAddress")
  public String getIpAddress() {
    return ipAddress;
  }
  public void setIpAddress(String ipAddress) {
    this.ipAddress = ipAddress;
  }

  
  /**
   **/
  
  @ApiModelProperty(value = "")
  @JsonProperty("series")
  public String getSeries() {
    return series;
  }
  public void setSeries(String series) {
    this.series = series;
  }

  
  /**
   **/
  
  @ApiModelProperty(value = "")
  @JsonProperty("userAgent")
  public String getUserAgent() {
    return userAgent;
  }
  public void setUserAgent(String userAgent) {
    this.userAgent = userAgent;
  }

  

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PersistentToken persistentToken = (PersistentToken) o;

    return true && Objects.equals(formattedTokenDate, persistentToken.formattedTokenDate) &&
        Objects.equals(ipAddress, persistentToken.ipAddress) &&
        Objects.equals(series, persistentToken.series) &&
        Objects.equals(userAgent, persistentToken.userAgent)
    ;
  }

  @Override
  public int hashCode() {
    return Objects.hash(formattedTokenDate, ipAddress, series, userAgent);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PersistentToken {\n");
    
    sb.append("    formattedTokenDate: ").append(toIndentedString(formattedTokenDate)).append("\n");
    sb.append("    ipAddress: ").append(toIndentedString(ipAddress)).append("\n");
    sb.append("    series: ").append(toIndentedString(series)).append("\n");
    sb.append("    userAgent: ").append(toIndentedString(userAgent)).append("\n");
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

