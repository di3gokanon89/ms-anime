/**
 * 
 */
package com.devpredator.msanime.service;

import java.util.List;
import java.util.Optional;

import com.devpredator.msentity.entity.Anime;

/**
 * Defines the business logic methods for animes.
 * 
 * @author DevPredator
 * @since v1.0
 * @version v1.0 21/07/2021
 *
 */
public interface AnimeService {
	/**
	 * Get the list of animes.
	 * @return {@link Anime} animes founded.
	 */
	List<Anime> findAnimes();
	/**
	 * Get an anime by its id.
	 * @return {@link Optional} the anime founded.
	 */
	Optional<Anime> findAnimeById(Long id);
	/**
	 * Create or update an anime in the database.
	 * @param anime {@link Anime} anime to create or update.
	 * @return {@link Anime} anime created or updated.
	 */
	Anime saveAnime(Anime anime);
	/**
	 * Delete an anime by its id.
	 * @param id {@link Long} anime's id to delete. 
	 */
	void deleteAnimeById(Long id);
}
