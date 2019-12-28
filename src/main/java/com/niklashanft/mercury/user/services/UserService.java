package com.niklashanft.mercury.user.services;

import com.mongodb.MongoClient;
import com.niklashanft.mercury.user.models.User;
import dev.morphia.Datastore;
import dev.morphia.Morphia;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserService {

    private final Morphia morphia = new Morphia();

    public void save(User user) {
        // Todo refactor new MongoClient() into poor mans connection broker
        // This would be a singleton with a concurrent hashmap, returning an available "established" mongoclient for reusage
        // The problem would be, to free these client after a transaction is happened.
        // Maybe there is a library for that
        final Datastore datastore = morphia.createDatastore(new MongoClient(), "mercury");
        datastore.save(user);
    }

    public List<User> getAllUsers() {
        // Todo refactor new MongoClient() into poor mans connection broker
        final Datastore datastore = morphia.createDatastore(new MongoClient(), "mercury");
        return datastore.createQuery(User.class)
                .find()
                .toList();
    }

    // Todo rename "get" to "find"
    // GET is a terminology we can use in the fronted, where http is your "data" pipeline
    // QUERY is the terminology we should use in the backend, because we are querying/finding things from the database.
    // Details matter!
    public Optional<User> getOneUserById(String id) {
        ObjectId oid = new ObjectId(id);
        // Todo refactor new MongoClient() into poor mans connection broker
        final Datastore datastore = morphia.createDatastore(new MongoClient(), "mercury");
        return Optional.ofNullable(datastore.find(User.class).field("id").equal(oid).get());
    }

    public Optional<User> getOneByPlayerId(UUID id) {
        // Todo refactor new MongoClient() into poor mans connection broker
        final Datastore datastore = morphia.createDatastore(new MongoClient(), "mercury");
        User user = datastore.createQuery(User.class)
                .field("playerId").equal(id).first();
        return Optional.ofNullable(user);
    }

    public Optional<User> getOneByDisplayName(String username) {
        // Todo refactor new MongoClient() into poor mans connection broker
        final Datastore datastore = morphia.createDatastore(new MongoClient(), "mercury");
        User user = datastore.createQuery(User.class)
                .field("displayName").equal(username).first();
        return Optional.ofNullable(user);
    }

}
