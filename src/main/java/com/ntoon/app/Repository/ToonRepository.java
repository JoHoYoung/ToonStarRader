package com.ntoon.app.Repository;

import com.ntoon.app.model.ToonModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ToonRepository extends MongoRepository<ToonModel, String> {


}

