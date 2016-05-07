package com.natureseyes.birdtheatre.client.birdtheatre.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.ZonedDateTime;





@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2016-05-07T11:59:04.274-04:00")
public class AuditEvent   {
  
  private Object data = null;
  private String principal = null;
  private ZonedDateTime timestamp = null;
  private String type = null;

  
  /**
   **/
  
  @ApiModelProperty(value = "")
  @JsonProperty("data")
  public Object getData() {
    return data;
  }
  public void setData(Object data) {
    this.data = data;
  }

  
  /**
   **/
  
  @ApiModelProperty(value = "")
  @JsonProperty("principal")
  public String getPrincipal() {
    return principal;
  }
  public void setPrincipal(String principal) {
    this.principal = principal;
  }

  
  /**
   **/
  
  @ApiModelProperty(value = "")
  @JsonProperty("timestamp")
  public ZonedDateTime getTimestamp() {
    return timestamp;
  }
  public void setTimestamp(ZonedDateTime timestamp) {
    this.timestamp = timestamp;
  }

  
  /**
   **/
  
  @ApiModelProperty(value = "")
  @JsonProperty("type")
  public String getType() {
    return type;
  }
  public void setType(String type) {
    this.type = type;
  }

  

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AuditEvent auditEvent = (AuditEvent) o;

    return true && Objects.equals(data, auditEvent.data) &&
        Objects.equals(principal, auditEvent.principal) &&
        Objects.equals(timestamp, auditEvent.timestamp) &&
        Objects.equals(type, auditEvent.type)
    ;
  }

  @Override
  public int hashCode() {
    return Objects.hash(data, principal, timestamp, type);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AuditEvent {\n");
    
    sb.append("    data: ").append(toIndentedString(data)).append("\n");
    sb.append("    principal: ").append(toIndentedString(principal)).append("\n");
    sb.append("    timestamp: ").append(toIndentedString(timestamp)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
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

