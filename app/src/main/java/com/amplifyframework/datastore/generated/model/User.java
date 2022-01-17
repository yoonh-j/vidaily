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

/** This is an auto generated class representing the User type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Users")
public final class User implements Model {
  public static final QueryField ID = field("User", "id");
  public static final QueryField UID = field("User", "uid");
  public static final QueryField USERNAME = field("User", "username");
  public static final QueryField FOLLOWING = field("User", "following");
  public static final QueryField FOLLOWER = field("User", "follower");
  public static final QueryField VIDEO_URLS = field("User", "videoUrls");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="ID", isRequired = true) String uid;
  private final @ModelField(targetType="String", isRequired = true) String username;
  private final @ModelField(targetType="Int", isRequired = true) Integer following;
  private final @ModelField(targetType="Int", isRequired = true) Integer follower;
  private final @ModelField(targetType="String") List<String> videoUrls;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  public String getId() {
      return id;
  }
  
  public String getUid() {
      return uid;
  }
  
  public String getUsername() {
      return username;
  }
  
  public Integer getFollowing() {
      return following;
  }
  
  public Integer getFollower() {
      return follower;
  }
  
  public List<String> getVideoUrls() {
      return videoUrls;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private User(String id, String uid, String username, Integer following, Integer follower, List<String> videoUrls) {
    this.id = id;
    this.uid = uid;
    this.username = username;
    this.following = following;
    this.follower = follower;
    this.videoUrls = videoUrls;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      User user = (User) obj;
      return ObjectsCompat.equals(getId(), user.getId()) &&
              ObjectsCompat.equals(getUid(), user.getUid()) &&
              ObjectsCompat.equals(getUsername(), user.getUsername()) &&
              ObjectsCompat.equals(getFollowing(), user.getFollowing()) &&
              ObjectsCompat.equals(getFollower(), user.getFollower()) &&
              ObjectsCompat.equals(getVideoUrls(), user.getVideoUrls()) &&
              ObjectsCompat.equals(getCreatedAt(), user.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), user.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getUid())
      .append(getUsername())
      .append(getFollowing())
      .append(getFollower())
      .append(getVideoUrls())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("User {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("uid=" + String.valueOf(getUid()) + ", ")
      .append("username=" + String.valueOf(getUsername()) + ", ")
      .append("following=" + String.valueOf(getFollowing()) + ", ")
      .append("follower=" + String.valueOf(getFollower()) + ", ")
      .append("videoUrls=" + String.valueOf(getVideoUrls()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()))
      .append("}")
      .toString();
  }
  
  public static UidStep builder() {
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
  public static User justId(String id) {
    return new User(
      id,
      null,
      null,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      uid,
      username,
      following,
      follower,
      videoUrls);
  }
  public interface UidStep {
    UsernameStep uid(String uid);
  }
  

  public interface UsernameStep {
    FollowingStep username(String username);
  }
  

  public interface FollowingStep {
    FollowerStep following(Integer following);
  }
  

  public interface FollowerStep {
    BuildStep follower(Integer follower);
  }
  

  public interface BuildStep {
    User build();
    BuildStep id(String id);
    BuildStep videoUrls(List<String> videoUrls);
  }
  

  public static class Builder implements UidStep, UsernameStep, FollowingStep, FollowerStep, BuildStep {
    private String id;
    private String uid;
    private String username;
    private Integer following;
    private Integer follower;
    private List<String> videoUrls;
    @Override
     public User build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new User(
          id,
          uid,
          username,
          following,
          follower,
          videoUrls);
    }
    
    @Override
     public UsernameStep uid(String uid) {
        Objects.requireNonNull(uid);
        this.uid = uid;
        return this;
    }
    
    @Override
     public FollowingStep username(String username) {
        Objects.requireNonNull(username);
        this.username = username;
        return this;
    }
    
    @Override
     public FollowerStep following(Integer following) {
        Objects.requireNonNull(following);
        this.following = following;
        return this;
    }
    
    @Override
     public BuildStep follower(Integer follower) {
        Objects.requireNonNull(follower);
        this.follower = follower;
        return this;
    }
    
    @Override
     public BuildStep videoUrls(List<String> videoUrls) {
        this.videoUrls = videoUrls;
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
    private CopyOfBuilder(String id, String uid, String username, Integer following, Integer follower, List<String> videoUrls) {
      super.id(id);
      super.uid(uid)
        .username(username)
        .following(following)
        .follower(follower)
        .videoUrls(videoUrls);
    }
    
    @Override
     public CopyOfBuilder uid(String uid) {
      return (CopyOfBuilder) super.uid(uid);
    }
    
    @Override
     public CopyOfBuilder username(String username) {
      return (CopyOfBuilder) super.username(username);
    }
    
    @Override
     public CopyOfBuilder following(Integer following) {
      return (CopyOfBuilder) super.following(following);
    }
    
    @Override
     public CopyOfBuilder follower(Integer follower) {
      return (CopyOfBuilder) super.follower(follower);
    }
    
    @Override
     public CopyOfBuilder videoUrls(List<String> videoUrls) {
      return (CopyOfBuilder) super.videoUrls(videoUrls);
    }
  }
  
}
