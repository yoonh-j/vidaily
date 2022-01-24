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

/** This is an auto generated class representing the PushToken type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "PushTokens")
public final class PushToken implements Model {
  public static final QueryField ID = field("PushToken", "id");
  public static final QueryField TOKEN = field("PushToken", "token");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String", isRequired = true) String token;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  public String getId() {
      return id;
  }
  
  public String getToken() {
      return token;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private PushToken(String id, String token) {
    this.id = id;
    this.token = token;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      PushToken pushToken = (PushToken) obj;
      return ObjectsCompat.equals(getId(), pushToken.getId()) &&
              ObjectsCompat.equals(getToken(), pushToken.getToken()) &&
              ObjectsCompat.equals(getCreatedAt(), pushToken.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), pushToken.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getToken())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("PushToken {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("token=" + String.valueOf(getToken()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()))
      .append("}")
      .toString();
  }
  
  public static TokenStep builder() {
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
  public static PushToken justId(String id) {
    return new PushToken(
      id,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      token);
  }
  public interface TokenStep {
    BuildStep token(String token);
  }
  

  public interface BuildStep {
    PushToken build();
    BuildStep id(String id);
  }
  

  public static class Builder implements TokenStep, BuildStep {
    private String id;
    private String token;
    @Override
     public PushToken build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new PushToken(
          id,
          token);
    }
    
    @Override
     public BuildStep token(String token) {
        Objects.requireNonNull(token);
        this.token = token;
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
    private CopyOfBuilder(String id, String token) {
      super.id(id);
      super.token(token);
    }
    
    @Override
     public CopyOfBuilder token(String token) {
      return (CopyOfBuilder) super.token(token);
    }
  }
  
}
