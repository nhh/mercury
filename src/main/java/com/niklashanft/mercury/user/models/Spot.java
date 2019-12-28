package com.niklashanft.mercury.user.models;


import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import dev.morphia.annotations.Property;
import org.bson.types.ObjectId;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.UUID;

@Entity("spots")
public class Spot {

    @Id
    private String id;

    @Property
    private String name;

    @Property
    private String description;

    @Property
    private String worldId;

    @Property
    private Double x;

    @Property
    private Double y;

    @Property
    private Double z;

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public Double getZ() {
        return z;
    }

    public void setZ(Double z) {
        this.z = z;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Location getLocation() {
        World world = Bukkit.getWorld(UUID.fromString(worldId));
        return new Location(world, x, y, z);
    }

    public void setWorldId(String worldId) {
        this.worldId = worldId;
    }

    public String getWorldId() {
        return worldId;
    }
}
