package com.niklashanft.mercury.user.services;

import com.mongodb.MongoClient;
import com.niklashanft.mercury.user.models.Spot;
import dev.morphia.Datastore;
import dev.morphia.Morphia;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Optional;

public class SpotService {

    private final Morphia morphia = new Morphia();

    public void save(Spot spot) {
        morphia.mapPackage("com.niklashanft.user.models");
        final Datastore datastore = morphia.createDatastore(new MongoClient(), "mercury");
        datastore.save(spot);
    }

    public List<Spot> getAllSpots() {
        final Datastore datastore = morphia.createDatastore(new MongoClient(), "mercury");
        return datastore.createQuery(Spot.class)
                .find()
                .toList();
    }

    public Optional<Spot> getOneSpot(String id) {
        ObjectId oid = new ObjectId(id);
        final Datastore datastore = morphia.createDatastore(new MongoClient(), "mercury");
        return Optional.ofNullable(datastore.find(Spot.class).field("id").equal(oid).get());
    }

}
