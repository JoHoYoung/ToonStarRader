package com.ntoon.app.Repository;

import com.ntoon.app.model.StarModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Date;
import java.util.List;

public interface StarRepository extends MongoRepository<StarModel, String> {
  @Query("{'$and':[{'createdAt':{'$gt':?0}},{'createdAt':{'$lt':?1}},{'seriesId':?2}]}")
  List<StarModel> findByUserIdBetween(Date from, Date to, String seriesId);

  List<StarModel> findByUserIdAndSeriesId(String userId,String seriesId);
}
