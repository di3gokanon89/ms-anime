/**
 * 
 */
package com.devpredator.msanime.service.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestReporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.devpredator.msanime.exceptions.AnimeNullException;
import com.devpredator.msanime.service.AnimeService;
import com.devpredator.msentity.entity.Anime;

/**
 * @author DevPredator
 * @since v1.0 29/09/2021
 * @version v1.0
 * 
 * Unit test for Animes Service Business.
 */
@SpringBootTest
class AnimeServiceTest {

	@Autowired
	private AnimeService animeServiceImpl;
	/**
	 * Log the details of a unit test.
	 */
	private TestReporter testReporter;
	/**
	 * Info of each unit case.
	 */
	private TestInfo testInfo;
	
	
	@BeforeAll
	static void beforeAll() {
		System.out.println("Before All");
	}
	
	@BeforeEach
	void beforeEach(TestReporter testReporter, TestInfo testInfo) {
		this.testReporter = testReporter;
		this.testInfo = testInfo;
		
		this.testReporter.publishEntry("DISPLAY NAME: " + this.testInfo.getDisplayName());
		this.testReporter.publishEntry("TEST METHOD: " + this.testInfo.getTestMethod());
	}
	
	@DisplayName(value = "NESTED ANIMES TO FIND")
	@Nested
	class FindAnimesNested {
		/**
		 * Test method for {@link com.devpredator.msanime.service.AnimeService#findAnimes()}.
		 */
		@DisplayName(value = "FIND ALL ANIMES")
		@Test
		void testFindAnimes() {
			List<Anime> animes = animeServiceImpl.findAnimes();
			
			assertNotNull(animes);
			assertTrue(animes.size() > 0);
			testReporter.publishEntry("List of animes founded: " + animes.size());
		}

		/**
		 * Test method for {@link com.devpredator.msanime.service.AnimeService#findAnimeById(java.lang.Long)}.
		 */
		@DisplayName(value = "FIND AN ANIME BY ID")
		@Test
		void testFindAnimeById() {
			
			Optional<Anime> anime = animeServiceImpl.findAnimeById(1L);
			
			assertNotNull(anime.get());
			assertTrue(anime.get().getNombre().equals("Shingeky no Kyojin"));
		}	
	}
	
	@DisplayName(value = "NESTED ANIMES TO SAVE")
	@Nested
	class SaveAnimesNested {
		
		@Disabled
		@DisplayName(value = "STORE AN ANIME IS OK")
		@Test
		void testSaveAnimeIsOk() {
			Anime anime = new Anime();
			anime.setNombre("Ragnarok");
			anime.setAnio(2021);
			
			Anime animeStored = animeServiceImpl.saveAnime(anime);
			
			assertNotNull(animeStored);
			assertNotNull(animeStored.getId());
			
			assertEquals("Ragnarok", animeStored.getNombre());
			assertEquals(2021, animeStored.getAnio());
			
			testReporter.publishEntry("Message", "Anime saved successfully");
		}

		/**
		 * Test method for {@link com.devpredator.msanime.service.AnimeService#saveAnime(com.devpredator.msentity.entity.Anime)}.
		 */
		@DisplayName(value = "STORE AN ANIME IS NULL")
		@Test
		void testSaveAnimeIsNull() {
			
			AnimeNullException exception = assertThrows(AnimeNullException.class, () -> animeServiceImpl.saveAnime(null));
			
			assertEquals("The anime is null", exception.getMessage());
		}	
	}

	/**
	 * Test method for {@link com.devpredator.msanime.service.AnimeService#deleteAnimeById(java.lang.Long)}.
	 */
	@DisplayName(value = "DELETE AN ANIME")
	@Test
	void testDeleteAnimeById() {
		animeServiceImpl.deleteAnimeById(34L);
		
		Optional<Anime> animeNotFound = animeServiceImpl.findAnimeById(34L);

		testReporter.publishEntry("Message", "Anime deleted successfully");

		assertTrue(animeNotFound.isEmpty());
		
	}
	
	@AfterEach
	void afterEach() {

	}
	
	@AfterAll
	static void afterAll() {

	}

}
