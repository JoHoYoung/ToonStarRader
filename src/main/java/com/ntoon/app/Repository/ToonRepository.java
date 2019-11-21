package com.ntoon.app.Repository;

import com.ntoon.app.model.ContentsModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ToonRepository extends MongoRepository<ContentsModel, String> {


}

