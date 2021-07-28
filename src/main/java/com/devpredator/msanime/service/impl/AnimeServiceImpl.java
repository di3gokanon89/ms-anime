/**
 * 
 */
package com.devpredator.msanime.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devpredator.msentity.entity.Anime;
import com.devpredator.msanime.repository.AnimeRepository;
import com.devpredator.msanime.service.AnimeService;

/**
 * Implements the business-logic methods of the interface for animes.
 * 
 * @author DevPredator
 * @since v1.0
 * @version v1.0 21/07/2021
 *
 */
@Service
public class AnimeServiceImpl implements AnimeService {

	@Autowired
	private AnimeRepository animeRepository;
	
	@Override
	public List<Anime> findAnimes() {
		return StreamSupport.stream(this.animeRepository.findAll().spliterator(), false)
							.collect(Collectors.toList());
	}

	@Override
	public Optional<Anime> findAnimeById(Long id) {
		return this.animeRepository.findById(id);
	}

	@Override
	public Anime saveAnime(Anime anime) {
		
		if (anime.getId() == null) {
			anime.setFechaCreacion(LocalDateTime.now());			
		}
		
		return this.animeRepository.save(anime);
	}

	@Override
	public void deleteAnimeById(Long id) {
		this.animeRepository.deleteById(id);
	}

}
