package com.natureseyes.birdtheatre.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A BroadcastStream.
 */
@Entity
@Table(name = "broadcast_stream")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "broadcaststream")
public class BroadcastStream implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min = 5, max = 20)
    @Column(name = "name", length = 20, nullable = false)
    private String name;

    @NotNull
    @Size(min = 20, max = 200)
    @Column(name = "description", length = 200, nullable = false)
    private String description;

    @NotNull
    @Column(name = "start_timestamp", nullable = false)
    private ZonedDateTime startTimestamp;

    @NotNull
    @Column(name = "end_timestamp", nullable = false)
    private ZonedDateTime endTimestamp;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ZonedDateTime getStartTimestamp() {
        return startTimestamp;
    }

    public void setStartTimestamp(ZonedDateTime startTimestamp) {
        this.startTimestamp = startTimestamp;
    }

    public ZonedDateTime getEndTimestamp() {
        return endTimestamp;
    }

    public void setEndTimestamp(ZonedDateTime endTimestamp) {
        this.endTimestamp = endTimestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BroadcastStream broadcastStream = (BroadcastStream) o;
        if(broadcastStream.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, broadcastStream.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "BroadcastStream{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", description='" + description + "'" +
            ", startTimestamp='" + startTimestamp + "'" +
            ", endTimestamp='" + endTimestamp + "'" +
            '}';
    }
}
