package com.github.georgi4511.victbot.repository;

import com.github.georgi4511.victbot.model.VictGuild;
import java.util.List;
import org.springframework.data.repository.ListCrudRepository;

public interface VictGuildRepository extends ListCrudRepository<VictGuild, String> {
  <T> List<T> findBy(Class<T> clazz);

  <T> T findById(Class<T> clazz, String id);

}
