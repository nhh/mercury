package com.niklashanft.mercury.user.models;

import dev.morphia.annotations.*;
import org.bson.types.ObjectId;

import java.util.UUID;

@Entity("users")
@Indexes(@Index(value = "salary", fields = @Field("salary")))
public class User {

    @Id
    private String id;

    @Property
    private UUID playerId;

    @Property
    private String displayName;

    @Property
    private String password;

    @Property
    private Boolean active;

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public UUID getPlayerId() {
        return playerId;
    }

    public void setPlayerId(UUID playerId) {
        this.playerId = playerId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
