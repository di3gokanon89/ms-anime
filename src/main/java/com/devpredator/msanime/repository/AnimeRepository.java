/**
 * 
 */
package com.devpredator.msanime.repository;

import org.springframework.data.repository.CrudRepository;

import com.devpredator.msentity.entity.Anime;

/**
 * Defines a repository for animes.
 * 
 * @author DevPredator
 * @since v1.0
 * @version v1.0 21/07/2021
 */
public interface AnimeRepository extends CrudRepository<Anime, Long> {

}
