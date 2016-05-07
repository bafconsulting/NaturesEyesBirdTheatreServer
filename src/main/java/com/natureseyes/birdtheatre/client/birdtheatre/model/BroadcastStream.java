package com.natureseyes.birdtheatre.client.birdtheatre.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.ZonedDateTime;





@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2016-05-07T11:59:04.274-04:00")
public class BroadcastStream   {
  
  private String description = null;
  private ZonedDateTime endTimestamp = null;
  private Long id = null;
  private String name = null;
  private ZonedDateTime startTimestamp = null;

  
  /**
   **/
  
  @ApiModelProperty(value = "")
  @JsonProperty("description")
  public String getDescription() {
    return description;
  }
  public void setDescription(String description) {
    this.description = description;
  }

  
  /**
   **/
  
  @ApiModelProperty(value = "")
  @JsonProperty("endTimestamp")
  public ZonedDateTime getEndTimestamp() {
    return endTimestamp;
  }
  public void setEndTimestamp(ZonedDateTime endTimestamp) {
    this.endTimestamp = endTimestamp;
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
  @JsonProperty("name")
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }

  
  /**
   **/
  
  @ApiModelProperty(value = "")
  @JsonProperty("startTimestamp")
  public ZonedDateTime getStartTimestamp() {
    return startTimestamp;
  }
  public void setStartTimestamp(ZonedDateTime startTimestamp) {
    this.startTimestamp = startTimestamp;
  }

  

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BroadcastStream broadcastStream = (BroadcastStream) o;

    return true && Objects.equals(description, broadcastStream.description) &&
        Objects.equals(endTimestamp, broadcastStream.endTimestamp) &&
        Objects.equals(id, broadcastStream.id) &&
        Objects.equals(name, broadcastStream.name) &&
        Objects.equals(startTimestamp, broadcastStream.startTimestamp)
    ;
  }

  @Override
  public int hashCode() {
    return Objects.hash(description, endTimestamp, id, name, startTimestamp);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class BroadcastStream {\n");
    
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    endTimestamp: ").append(toIndentedString(endTimestamp)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    startTimestamp: ").append(toIndentedString(startTimestamp)).append("\n");
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

