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
  public static final QueryField USERNAME = field("User", "username");
  public static final QueryField PROFILE_URL = field("User", "profileUrl");
  public static final QueryField FOLLOWING = field("User", "following");
  public static final QueryField FOLLOWER = field("User", "follower");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String", isRequired = true) String username;
  private final @ModelField(targetType="String", isRequired = true) String profileUrl;
  private final @ModelField(targetType="Int", isRequired = true) Integer following;
  private final @ModelField(targetType="Int", isRequired = true) Integer follower;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  public String getId() {
      return id;
  }
  
  public String getUsername() {
      return username;
  }
  
  public String getProfileUrl() {
      return profileUrl;
  }
  
  public Integer getFollowing() {
      return following;
  }
  
  public Integer getFollower() {
      return follower;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private User(String id, String username, String profileUrl, Integer following, Integer follower) {
    this.id = id;
    this.username = username;
    this.profileUrl = profileUrl;
    this.following = following;
    this.follower = follower;
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
              ObjectsCompat.equals(getUsername(), user.getUsername()) &&
              ObjectsCompat.equals(getProfileUrl(), user.getProfileUrl()) &&
              ObjectsCompat.equals(getFollowing(), user.getFollowing()) &&
              ObjectsCompat.equals(getFollower(), user.getFollower()) &&
              ObjectsCompat.equals(getCreatedAt(), user.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), user.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getUsername())
      .append(getProfileUrl())
      .append(getFollowing())
      .append(getFollower())
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
      .append("username=" + String.valueOf(getUsername()) + ", ")
      .append("profileUrl=" + String.valueOf(getProfileUrl()) + ", ")
      .append("following=" + String.valueOf(getFollowing()) + ", ")
      .append("follower=" + String.valueOf(getFollower()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()))
      .append("}")
      .toString();
  }
  
  public static UsernameStep builder() {
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
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      username,
      profileUrl,
      following,
      follower);
  }
  public interface UsernameStep {
    ProfileUrlStep username(String username);
  }
  

  public interface ProfileUrlStep {
    FollowingStep profileUrl(String profileUrl);
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
  }
  

  public static class Builder implements UsernameStep, ProfileUrlStep, FollowingStep, FollowerStep, BuildStep {
    private String id;
    private String username;
    private String profileUrl;
    private Integer following;
    private Integer follower;
    @Override
     public User build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new User(
          id,
          username,
          profileUrl,
          following,
          follower);
    }
    
    @Override
     public ProfileUrlStep username(String username) {
        Objects.requireNonNull(username);
        this.username = username;
        return this;
    }
    
    @Override
     public FollowingStep profileUrl(String profileUrl) {
        Objects.requireNonNull(profileUrl);
        this.profileUrl = profileUrl;
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
    private CopyOfBuilder(String id, String username, String profileUrl, Integer following, Integer follower) {
      super.id(id);
      super.username(username)
        .profileUrl(profileUrl)
        .following(following)
        .follower(follower);
    }
    
    @Override
     public CopyOfBuilder username(String username) {
      return (CopyOfBuilder) super.username(username);
    }
    
    @Override
     public CopyOfBuilder profileUrl(String profileUrl) {
      return (CopyOfBuilder) super.profileUrl(profileUrl);
    }
    
    @Override
     public CopyOfBuilder following(Integer following) {
      return (CopyOfBuilder) super.following(following);
    }
    
    @Override
     public CopyOfBuilder follower(Integer follower) {
      return (CopyOfBuilder) super.follower(follower);
    }
  }
  
}
