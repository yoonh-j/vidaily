package com.amplifyframework.datastore.generated.model;

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
  public static final QueryField KEY = field("Metadata", "key");
  public static final QueryField UID = field("Metadata", "uid");
  public static final QueryField URL = field("Metadata", "url");
  public static final QueryField TITLE = field("Metadata", "title");
  public static final QueryField DESCRIPTION = field("Metadata", "description");
  public static final QueryField TIME_IN_MILLIS = field("Metadata", "timeInMillis");
  public static final QueryField VIEWS = field("Metadata", "views");
  public static final QueryField LIKES = field("Metadata", "likes");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String", isRequired = true) String key;
  private final @ModelField(targetType="ID", isRequired = true) String uid;
  private final @ModelField(targetType="String", isRequired = true) String url;
  private final @ModelField(targetType="String", isRequired = true) String title;
  private final @ModelField(targetType="String") String description;
  private final @ModelField(targetType="String", isRequired = true) String timeInMillis;
  private final @ModelField(targetType="Int", isRequired = true) Integer views;
  private final @ModelField(targetType="Int", isRequired = true) Integer likes;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  public String getId() {
      return id;
  }
  
  public String getKey() {
      return key;
  }
  
  public String getUid() {
      return uid;
  }
  
  public String getUrl() {
      return url;
  }
  
  public String getTitle() {
      return title;
  }
  
  public String getDescription() {
      return description;
  }
  
  public String getTimeInMillis() {
      return timeInMillis;
  }
  
  public Integer getViews() {
      return views;
  }
  
  public Integer getLikes() {
      return likes;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private Metadata(String id, String key, String uid, String url, String title, String description, String timeInMillis, Integer views, Integer likes) {
    this.id = id;
    this.key = key;
    this.uid = uid;
    this.url = url;
    this.title = title;
    this.description = description;
    this.timeInMillis = timeInMillis;
    this.views = views;
    this.likes = likes;
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
              ObjectsCompat.equals(getKey(), metadata.getKey()) &&
              ObjectsCompat.equals(getUid(), metadata.getUid()) &&
              ObjectsCompat.equals(getUrl(), metadata.getUrl()) &&
              ObjectsCompat.equals(getTitle(), metadata.getTitle()) &&
              ObjectsCompat.equals(getDescription(), metadata.getDescription()) &&
              ObjectsCompat.equals(getTimeInMillis(), metadata.getTimeInMillis()) &&
              ObjectsCompat.equals(getViews(), metadata.getViews()) &&
              ObjectsCompat.equals(getLikes(), metadata.getLikes()) &&
              ObjectsCompat.equals(getCreatedAt(), metadata.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), metadata.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getKey())
      .append(getUid())
      .append(getUrl())
      .append(getTitle())
      .append(getDescription())
      .append(getTimeInMillis())
      .append(getViews())
      .append(getLikes())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("Metadata {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("key=" + String.valueOf(getKey()) + ", ")
      .append("uid=" + String.valueOf(getUid()) + ", ")
      .append("url=" + String.valueOf(getUrl()) + ", ")
      .append("title=" + String.valueOf(getTitle()) + ", ")
      .append("description=" + String.valueOf(getDescription()) + ", ")
      .append("timeInMillis=" + String.valueOf(getTimeInMillis()) + ", ")
      .append("views=" + String.valueOf(getViews()) + ", ")
      .append("likes=" + String.valueOf(getLikes()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()))
      .append("}")
      .toString();
  }
  
  public static KeyStep builder() {
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
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      key,
      uid,
      url,
      title,
      description,
      timeInMillis,
      views,
      likes);
  }
  public interface KeyStep {
    UidStep key(String key);
  }
  

  public interface UidStep {
    UrlStep uid(String uid);
  }
  

  public interface UrlStep {
    TitleStep url(String url);
  }
  

  public interface TitleStep {
    TimeInMillisStep title(String title);
  }
  

  public interface TimeInMillisStep {
    ViewsStep timeInMillis(String timeInMillis);
  }
  

  public interface ViewsStep {
    LikesStep views(Integer views);
  }
  

  public interface LikesStep {
    BuildStep likes(Integer likes);
  }
  

  public interface BuildStep {
    Metadata build();
    BuildStep id(String id);
    BuildStep description(String description);
  }
  

  public static class Builder implements KeyStep, UidStep, UrlStep, TitleStep, TimeInMillisStep, ViewsStep, LikesStep, BuildStep {
    private String id;
    private String key;
    private String uid;
    private String url;
    private String title;
    private String timeInMillis;
    private Integer views;
    private Integer likes;
    private String description;
    @Override
     public Metadata build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Metadata(
          id,
          key,
          uid,
          url,
          title,
          description,
          timeInMillis,
          views,
          likes);
    }
    
    @Override
     public UidStep key(String key) {
        Objects.requireNonNull(key);
        this.key = key;
        return this;
    }
    
    @Override
     public UrlStep uid(String uid) {
        Objects.requireNonNull(uid);
        this.uid = uid;
        return this;
    }
    
    @Override
     public TitleStep url(String url) {
        Objects.requireNonNull(url);
        this.url = url;
        return this;
    }
    
    @Override
     public TimeInMillisStep title(String title) {
        Objects.requireNonNull(title);
        this.title = title;
        return this;
    }
    
    @Override
     public ViewsStep timeInMillis(String timeInMillis) {
        Objects.requireNonNull(timeInMillis);
        this.timeInMillis = timeInMillis;
        return this;
    }
    
    @Override
     public LikesStep views(Integer views) {
        Objects.requireNonNull(views);
        this.views = views;
        return this;
    }
    
    @Override
     public BuildStep likes(Integer likes) {
        Objects.requireNonNull(likes);
        this.likes = likes;
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
    private CopyOfBuilder(String id, String key, String uid, String url, String title, String description, String timeInMillis, Integer views, Integer likes) {
      super.id(id);
      super.key(key)
        .uid(uid)
        .url(url)
        .title(title)
        .timeInMillis(timeInMillis)
        .views(views)
        .likes(likes)
        .description(description);
    }
    
    @Override
     public CopyOfBuilder key(String key) {
      return (CopyOfBuilder) super.key(key);
    }
    
    @Override
     public CopyOfBuilder uid(String uid) {
      return (CopyOfBuilder) super.uid(uid);
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
     public CopyOfBuilder timeInMillis(String timeInMillis) {
      return (CopyOfBuilder) super.timeInMillis(timeInMillis);
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
     public CopyOfBuilder description(String description) {
      return (CopyOfBuilder) super.description(description);
    }
  }
  
}
