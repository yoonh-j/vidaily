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

/** This is an auto generated class representing the Metadata type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Metadata")
public final class Metadata implements Model {
  public static final QueryField ID = field("Metadata", "id");
  public static final QueryField URL = field("Metadata", "url");
  public static final QueryField TITLE = field("Metadata", "title");
  public static final QueryField VIEWS = field("Metadata", "views");
  public static final QueryField LIKES = field("Metadata", "likes");
  public static final QueryField CREATED_AT = field("Metadata", "createdAt");
  public static final QueryField UID = field("Metadata", "uid");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String", isRequired = true) String url;
  private final @ModelField(targetType="String", isRequired = true) String title;
  private final @ModelField(targetType="Int", isRequired = true) Integer views;
  private final @ModelField(targetType="Int", isRequired = true) Integer likes;
  private final @ModelField(targetType="String", isRequired = true) String createdAt;
  private final @ModelField(targetType="ID", isRequired = true) String uid;
  private final @ModelField(targetType="User") @HasOne(associatedWith = "id", type = User.class) User user = null;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  public String getId() {
      return id;
  }
  
  public String getUrl() {
      return url;
  }
  
  public String getTitle() {
      return title;
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
  
  private Metadata(String id, String url, String title, Integer views, Integer likes, String createdAt, String uid) {
    this.id = id;
    this.url = url;
    this.title = title;
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
      Metadata metadata = (Metadata) obj;
      return ObjectsCompat.equals(getId(), metadata.getId()) &&
              ObjectsCompat.equals(getUrl(), metadata.getUrl()) &&
              ObjectsCompat.equals(getTitle(), metadata.getTitle()) &&
              ObjectsCompat.equals(getViews(), metadata.getViews()) &&
              ObjectsCompat.equals(getLikes(), metadata.getLikes()) &&
              ObjectsCompat.equals(getCreatedAt(), metadata.getCreatedAt()) &&
              ObjectsCompat.equals(getUid(), metadata.getUid()) &&
              ObjectsCompat.equals(getUpdatedAt(), metadata.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getUrl())
      .append(getTitle())
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
      .append("Metadata {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("url=" + String.valueOf(getUrl()) + ", ")
      .append("title=" + String.valueOf(getTitle()) + ", ")
      .append("views=" + String.valueOf(getViews()) + ", ")
      .append("likes=" + String.valueOf(getLikes()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("uid=" + String.valueOf(getUid()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()))
      .append("}")
      .toString();
  }
  
  public static UrlStep builder() {
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
  public static Metadata justId(String id) {
    return new Metadata(
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
      url,
      title,
      views,
      likes,
      createdAt,
      uid);
  }
  public interface UrlStep {
    TitleStep url(String url);
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
    Metadata build();
    BuildStep id(String id);
  }
  

  public static class Builder implements UrlStep, TitleStep, ViewsStep, LikesStep, CreatedAtStep, UidStep, BuildStep {
    private String id;
    private String url;
    private String title;
    private Integer views;
    private Integer likes;
    private String createdAt;
    private String uid;
    @Override
     public Metadata build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Metadata(
          id,
          url,
          title,
          views,
          likes,
          createdAt,
          uid);
    }
    
    @Override
     public TitleStep url(String url) {
        Objects.requireNonNull(url);
        this.url = url;
        return this;
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
    private CopyOfBuilder(String id, String url, String title, Integer views, Integer likes, String createdAt, String uid) {
      super.id(id);
      super.url(url)
        .title(title)
        .views(views)
        .likes(likes)
        .createdAt(createdAt)
        .uid(uid);
    }
    
    @Override
     public CopyOfBuilder url(String url) {
      return (CopyOfBuilder) super.url(url);
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
  }
  
}
