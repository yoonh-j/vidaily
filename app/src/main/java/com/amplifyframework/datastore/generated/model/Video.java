package com.amplifyframework.datastore.generated.model;

import com.amplifyframework.core.model.annotations.HasOne;
import com.amplifyframework.core.model.annotations.HasMany;
import com.amplifyframework.core.model.temporal.Temporal;

import java.util.List;
import java.util.UUID;
import java.util.Objects;

import androidx.core.util.ObjectsCompat;

import com.amplifyframework.core.model.Model;
import com.amplifyframework.core.model.annotations.Index;
import com.amplifyframework.core.model.annotations.ModelConfig;
import com.amplifyframework.core.model.annotations.ModelField;
import com.amplifyframework.core.model.query.predicate.QueryField;

import static com.amplifyframework.core.model.query.predicate.QueryField.field;

/** This is an auto generated class representing the Video type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Videos")
public final class Video implements Model {
  public static final QueryField ID = field("Video", "id");
  public static final QueryField DESCRIPTION = field("Video", "description");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String") String description;
  private final @ModelField(targetType="Metadata") @HasOne(associatedWith = "id", type = Metadata.class) Metadata metadata = null;
  private final @ModelField(targetType="Comment") @HasMany(associatedWith = "id", type = Comment.class) List<Comment> comments = null;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  public String getId() {
      return id;
  }
  
  public String getDescription() {
      return description;
  }
  
  public Metadata getMetadata() {
      return metadata;
  }
  
  public List<Comment> getComments() {
      return comments;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private Video(String id, String description) {
    this.id = id;
    this.description = description;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      Video video = (Video) obj;
      return ObjectsCompat.equals(getId(), video.getId()) &&
              ObjectsCompat.equals(getDescription(), video.getDescription()) &&
              ObjectsCompat.equals(getCreatedAt(), video.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), video.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getDescription())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("Video {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("description=" + String.valueOf(getDescription()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()))
      .append("}")
      .toString();
  }
  
  public static BuildStep builder() {
      return new Builder();
  }
  
  /** 
   * WARNING: This method should not be used to build an instance of this object for a CREATE mutation.
   * This is a convenience method to return an instance of the object with only its ID populated
   * to be used in the context of a parameter in a delete mutation or referencing a foreign key
   * in a relationship.
   * @param id the id of the existing item this instance will represent
   * @return an instance of this model with only ID populated
   */
  public static Video justId(String id) {
    return new Video(
      id,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      description);
  }
  public interface BuildStep {
    Video build();
    BuildStep id(String id);
    BuildStep description(String description);
  }
  

  public static class Builder implements BuildStep {
    private String id;
    private String description;
    @Override
     public Video build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Video(
          id,
          description);
    }
    
    @Override
     public BuildStep description(String description) {
        this.description = description;
        return this;
    }
    
    /** 
     * @param id id
     * @return Current Builder instance, for fluent method chaining
     */
    public BuildStep id(String id) {
        this.id = id;
        return this;
    }
  }
  

  public final class CopyOfBuilder extends Builder {
    private CopyOfBuilder(String id, String description) {
      super.id(id);
      super.description(description);
    }
    
    @Override
     public CopyOfBuilder description(String description) {
      return (CopyOfBuilder) super.description(description);
    }
  }
  
}
