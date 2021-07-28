/**
 * 
 */
package com.devpredator.msanime.controllers;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devpredator.MsAnimeApplication;
import com.devpredator.msentity.entity.Anime;
import com.devpredator.msanime.service.AnimeService;

/**
 * Defines the endpoints of the services that we need to share information with other service.
 * 
 * @author DevPredator
 * @since v1.0
 * @version v1.0 21/07/2021
 *
 */
@RestController
@RequestMapping("/anime")
public class AnimeController {
	/**
	 * Service bean with the businness logic of animes.
	 */
	@Autowired
	private AnimeService animeServiceImpl;	
	/**
	 * Property to get the app environment (DEV, PROD, TEST, ETC.).
	 */
	@Value("${app.environment}")
	String appEnvironment;
	
	/**
	 *  CONSTANT TO GET THE LOGS.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(AnimeController.class); 
	
	@GetMapping("findAnimes")
	public ResponseEntity<?> findAnimes() {
		LOGGER.info("Run findAnimes service.");
		
		System.out.println(this.appEnvironment);
		
		List<Anime> animes = this.animeServiceImpl.findAnimes();
		
		LOGGER.info("ANIMES FOUNDED: " + animes.size());
		
		if (animes.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		
		LOGGER.info("FindAnimes Service Finished");
		
		return ResponseEntity.ok(animes);
	}
	
	@GetMapping("findAnimeById/{id}")
	public ResponseEntity<?> findAnimeById(@PathVariable("id") Long idAnime) {
		
		Optional<Anime> animeOptional = this.animeServiceImpl.findAnimeById(idAnime);
		
		if (!animeOptional.isPresent()) {
			return ResponseEntity.noContent().build();
		}
		
		return ResponseEntity.ok(animeOptional.get());
	}
	
	@PostMapping("saveAnime")
	public ResponseEntity<?> saveAnime(@Valid @RequestBody Anime anime) {
		
		Anime animeSaved = this.animeServiceImpl.saveAnime(anime);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(animeSaved);
	}
	
	@PutMapping("updateAnime")
	public ResponseEntity<?> updateAnime(@RequestBody Anime anime) {
		Anime animeUpdated = this.animeServiceImpl.saveAnime(anime);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(animeUpdated);
	}
	
	@DeleteMapping("deleteAnimeById/{id}")
	public ResponseEntity<?> deleteAnimeById(@PathVariable("id") Long idAnime) {
		
		this.animeServiceImpl.deleteAnimeById(idAnime);
		
		return ResponseEntity.ok().build();
	}
	
}
