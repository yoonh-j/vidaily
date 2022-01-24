package com.amplifyframework.datastore.generated.model;

import com.amplifyframework.core.model.annotations.HasOne;
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
  public static final QueryField TITLE = field("Video", "title");
  public static final QueryField DESCRIPTION = field("Video", "description");
  public static final QueryField VIEWS = field("Video", "views");
  public static final QueryField LIKES = field("Video", "likes");
  public static final QueryField CREATED_AT = field("Video", "createdAt");
  public static final QueryField UID = field("Video", "uid");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String", isRequired = true) String title;
  private final @ModelField(targetType="String") String description;
  private final @ModelField(targetType="Int", isRequired = true) Integer views;
  private final @ModelField(targetType="Int", isRequired = true) Integer likes;
  private final @ModelField(targetType="String", isRequired = true) String createdAt;
  private final @ModelField(targetType="ID", isRequired = true) String uid;
  private final @ModelField(targetType="User") @HasOne(associatedWith = "id", type = User.class) User user = null;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  public String getId() {
      return id;
  }
  
  public String getTitle() {
      return title;
  }
  
  public String getDescription() {
      return description;
  }
  
  public Integer getViews() {
      return views;
  }
  
  public Integer getLikes() {
      return likes;
  }
  
  public String getCreatedAt() {
      return createdAt;
  }
  
  public String getUid() {
      return uid;
  }
  
  public User getUser() {
      return user;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private Video(String id, String title, String description, Integer views, Integer likes, String createdAt, String uid) {
    this.id = id;
    this.title = title;
    this.description = description;
    this.views = views;
    this.likes = likes;
    this.createdAt = createdAt;
    this.uid = uid;
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
              ObjectsCompat.equals(getTitle(), video.getTitle()) &&
              ObjectsCompat.equals(getDescription(), video.getDescription()) &&
              ObjectsCompat.equals(getViews(), video.getViews()) &&
              ObjectsCompat.equals(getLikes(), video.getLikes()) &&
              ObjectsCompat.equals(getCreatedAt(), video.getCreatedAt()) &&
              ObjectsCompat.equals(getUid(), video.getUid()) &&
              ObjectsCompat.equals(getUpdatedAt(), video.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getTitle())
      .append(getDescription())
      .append(getViews())
      .append(getLikes())
      .append(getCreatedAt())
      .append(getUid())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("Video {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("title=" + String.valueOf(getTitle()) + ", ")
      .append("description=" + String.valueOf(getDescription()) + ", ")
      .append("views=" + String.valueOf(getViews()) + ", ")
      .append("likes=" + String.valueOf(getLikes()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("uid=" + String.valueOf(getUid()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()))
      .append("}")
      .toString();
  }
  
  public static TitleStep builder() {
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
      null,
      null,
      null,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      title,
      description,
      views,
      likes,
      createdAt,
      uid);
  }
  public interface TitleStep {
    ViewsStep title(String title);
  }
  

  public interface ViewsStep {
    LikesStep views(Integer views);
  }
  

  public interface LikesStep {
    CreatedAtStep likes(Integer likes);
  }
  

  public interface CreatedAtStep {
    UidStep createdAt(String createdAt);
  }
  

  public interface UidStep {
    BuildStep uid(String uid);
  }
  

  public interface BuildStep {
    Video build();
    BuildStep id(String id);
    BuildStep description(String description);
  }
  

  public static class Builder implements TitleStep, ViewsStep, LikesStep, CreatedAtStep, UidStep, BuildStep {
    private String id;
    private String title;
    private Integer views;
    private Integer likes;
    private String createdAt;
    private String uid;
    private String description;
    @Override
     public Video build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Video(
          id,
          title,
          description,
          views,
          likes,
          createdAt,
          uid);
    }
    
    @Override
     public ViewsStep title(String title) {
        Objects.requireNonNull(title);
        this.title = title;
        return this;
    }
    
    @Override
     public LikesStep views(Integer views) {
        Objects.requireNonNull(views);
        this.views = views;
        return this;
    }
    
    @Override
     public CreatedAtStep likes(Integer likes) {
        Objects.requireNonNull(likes);
        this.likes = likes;
        return this;
    }
    
    @Override
     public UidStep createdAt(String createdAt) {
        Objects.requireNonNull(createdAt);
        this.createdAt = createdAt;
        return this;
    }
    
    @Override
     public BuildStep uid(String uid) {
        Objects.requireNonNull(uid);
        this.uid = uid;
        return this;
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
    private CopyOfBuilder(String id, String title, String description, Integer views, Integer likes, String createdAt, String uid) {
      super.id(id);
      super.title(title)
        .views(views)
        .likes(likes)
        .createdAt(createdAt)
        .uid(uid)
        .description(description);
    }
    
    @Override
     public CopyOfBuilder title(String title) {
      return (CopyOfBuilder) super.title(title);
    }
    
    @Override
     public CopyOfBuilder views(Integer views) {
      return (CopyOfBuilder) super.views(views);
    }
    
    @Override
     public CopyOfBuilder likes(Integer likes) {
      return (CopyOfBuilder) super.likes(likes);
    }
    
    @Override
     public CopyOfBuilder createdAt(String createdAt) {
      return (CopyOfBuilder) super.createdAt(createdAt);
    }
    
    @Override
     public CopyOfBuilder uid(String uid) {
      return (CopyOfBuilder) super.uid(uid);
    }
    
    @Override
     public CopyOfBuilder description(String description) {
      return (CopyOfBuilder) super.description(description);
    }
  }
  
}
