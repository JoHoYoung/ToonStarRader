package com.ntoon.app.Repository;

import com.ntoon.app.model.SeriesModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SeriesRepository extends MongoRepository<SeriesModel, String> {

}
