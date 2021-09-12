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

import com.devpredator.msentity.entity.Anime;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import com.devpredator.msanime.service.AnimeService;

/**
 * Defines the endpoints of the services that we need to share information with other service.
 * 
 * @author DevPredator
 * @since v1.0
 * @version v1.0 21/07/2021
 *
 */
@Api(tags = {"ANIME'S MICROSERVICE"})
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
	
	@ApiOperation(value = "FIND ANIMES", notes = "Get the list of animes", response = List.class)
	@ApiResponses({
		@ApiResponse(code = 200, message = "Animes founded successfully!"),
		@ApiResponse(code = 401, message = "You don't have access to this endpoint!"),
		@ApiResponse(code = 403, message = "Anime's forbidden!"),
		@ApiResponse(code = 404, message = "Animes's resource not founded!")
	})
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

	@ApiOperation(value = "FIND ANIME BY ID", notes = "Get an anime by its id", response = Anime.class)
	@ApiResponses({
		@ApiResponse(code = 200, message = "Anime founded successfully!"),
		@ApiResponse(code = 401, message = "You don't have access to this endpoint!"),
		@ApiResponse(code = 403, message = "Anime's forbidden!"),
		@ApiResponse(code = 404, message = "Animes's resource not founded!")
	})
	@GetMapping("findAnimeById/{id}")
	public ResponseEntity<?> findAnimeById(
			@ApiParam(value = "The id of the anime to find", name = "id", required = true, example = "Example: 10") 
			@PathVariable("id") Long idAnime) {
		
		Optional<Anime> animeOptional = this.animeServiceImpl.findAnimeById(idAnime);
		
		if (!animeOptional.isPresent()) {
			return ResponseEntity.noContent().build();
		}
		
		return ResponseEntity.ok(animeOptional.get());
	}
	
	@ApiOperation(value = "SAVE ANIME", notes = "Store a new anime in the database", response = Anime.class)
	@ApiResponses({
		@ApiResponse(code = 200, message = "Anime request successfully!"),
		@ApiResponse(code = 201, message = "Anime created successfully!"),
		@ApiResponse(code = 401, message = "You don't have access to this endpoint!"),
		@ApiResponse(code = 403, message = "Anime's forbidden!"),
		@ApiResponse(code = 404, message = "Animes's resource not founded!")
	})
	@PostMapping("saveAnime")
	public ResponseEntity<?> saveAnime(
			@ApiParam(value = "The object of the anime to store", name = "anime", required = true) 
			@Valid @RequestBody Anime anime) {
		
		Anime animeSaved = this.animeServiceImpl.saveAnime(anime);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(animeSaved);
	}
	
	@ApiOperation(value = "UPDATE ANIME", notes = "Update an anime in the database", response = Anime.class)
	@ApiResponses({
		@ApiResponse(code = 200, message = "Anime request successfully!"),
		@ApiResponse(code = 201, message = "Anime updated successfully!"),
		@ApiResponse(code = 401, message = "You don't have access to this endpoint!"),
		@ApiResponse(code = 403, message = "Anime's forbidden!"),
		@ApiResponse(code = 404, message = "Animes's resource not founded!")
	})
	@PutMapping("updateAnime")
	public ResponseEntity<?> updateAnime(
			@ApiParam(value = "The object of the anime to update", name = "anime", required = true) 
			@Valid @RequestBody Anime anime) {
		Anime animeUpdated = this.animeServiceImpl.saveAnime(anime);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(animeUpdated);
	}
	
	@ApiOperation(value = "DELETE ANIME", notes = "Delete an anime in the database", response = ResponseEntity.class)
	@ApiResponses({
		@ApiResponse(code = 200, message = "Anime deleted successfully!"),
		@ApiResponse(code = 204, message = "No content for this anime!"),
		@ApiResponse(code = 401, message = "You don't have access to this endpoint!"),
		@ApiResponse(code = 403, message = "Anime's forbidden!"),
		@ApiResponse(code = 404, message = "Animes's resource not founded!")
	})
	@DeleteMapping("deleteAnimeById/{id}")
	public ResponseEntity<?> deleteAnimeById(
			@ApiParam(value = "The id of the anime to delete", name = "id", required = true, example = "Example: 1") 
			@PathVariable("id") Long idAnime) {
		
		this.animeServiceImpl.deleteAnimeById(idAnime);
		
		return ResponseEntity.ok().build();
	}
	
}
