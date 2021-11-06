/**
 * 
 */
package com.devpredator.msanime.service.test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.devpredator.msanime.exceptions.AnimeNullException;
import com.devpredator.msanime.repository.AnimeRepository;
import com.devpredator.msanime.service.AnimeService;
import com.devpredator.msanime.service.impl.AnimeServiceImpl;
import com.devpredator.msentity.entity.Anime;

/**
 * @author DevPredator
 * @since v1.0 29/09/2021
 * @version v1.0
 * 
 * Unit test for Animes Service Business.
 */
@SpringBootTest
@ActiveProfiles(value = {"test"})
@ExtendWith(MockitoExtension.class)
class AnimeServiceTest {

	@Mock
	private AnimeRepository animeRepository;
	
	//@Autowired
	@InjectMocks
	private AnimeServiceImpl animeServiceImpl;
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
		
		//::::: MANUAL CONFIGURATION WITH MOCKS :::::
		//animeServiceImpl = mock(AnimeService.class);
		
		//::::: CONFIGURATION WITH ANNOTATIONS ::::::
		//MockitoAnnotations.openMocks(this);
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
			
			//:::::::::::::::::: GIVEN :::::::::::::::::::::
			
			//Defines the list of animes to test a simulated data from the database.
			Iterable<Anime> animesIterable = List.of(
					new Anime(1L, "Dragon Ball Z", 1986, LocalDateTime.of(2021, 2, 20, 02, 24, 44)),
					new Anime(2L, "Hellsing", 2004, LocalDateTime.of(2021, 2, 20, 05, 24, 44))
			);
			
			// :::::::::::::::: WHEN ::::::::::::::::::::::
			
			when(animeRepository.findAll()).thenReturn(animesIterable);
			
			// :::::::::::::::: THEN ::::::::::::::::::::::
			List<Anime> animes = animeServiceImpl.findAnimes();
			
			assertNotNull(animes);
			assertTrue(animes.size() > 0);
			testReporter.publishEntry("List of animes founded: " + animes.size());
			
			verify(animeRepository).findAll();
		}

		/**
		 * Test method for {@link com.devpredator.msanime.service.AnimeService#findAnimeById(java.lang.Long)}.
		 */
		@DisplayName(value = "FIND AN ANIME BY ID")
		@Test
		void testFindAnimeById() {
			
			//::::::::::::::::: GIVEN :::::::::::::::::::::
			
			Long id = 1L;
			Anime anime = new Anime(1L, "Demon Slayer", 2019, LocalDateTime.of(2021, 10, 11, 12, 12, 12));
			
			//::::::::::::::::: WHEN ::::::::::::::::::::::
			
			when(animeRepository.findById(id)).thenReturn(Optional.of(anime));
			
			//::::::::::::::::: THEN ::::::::::::::::::::::
			Optional<Anime> animeOptional = animeServiceImpl.findAnimeById(id);
			
			assertNotNull(animeOptional.get());
			assertTrue(animeOptional.get().getNombre().equals("Demon Slayer"));
			
			verify(animeRepository).findById(id);
		}	
	}
	
	@DisplayName(value = "NESTED ANIMES TO SAVE")
	@Nested
	class SaveAnimesNested {
		
		@Disabled
		@DisplayName(value = "STORE AN ANIME IS OK")
		@Test
		void testSaveAnimeIsOk() {
			
			//::::::::::::::: GIVEN ::::::::::::::::::::::
			
			Anime anime = new Anime();
			anime.setId(10L);
			anime.setNombre("Ragnarok");
			anime.setAnio(2021);
			anime.setFechaCreacion(LocalDateTime.of(2021, 9, 10, 10, 10, 10));
			
			//::::::::::::::: WHEN ::::::::::::::::::::::::
			
			when(animeRepository.save(any(Anime.class))).thenReturn(anime);
			
			//::::::::::::::: THEN :::::::::::::::::::::::::
			Anime animeStored = animeServiceImpl.saveAnime(anime);
			
			assertNotNull(animeStored);
			assertNotNull(animeStored.getId());
			
			assertEquals(10L, animeStored.getId());
			assertEquals("Ragnarok", animeStored.getNombre());
			assertEquals(2021, animeStored.getAnio());
			
			testReporter.publishEntry("Message", "Anime saved successfully");
			
			verify(animeRepository).save(any(Anime.class));
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
				
		animeServiceImpl.deleteAnimeById(anyLong());
		
		//:::::::::::::::: WHEN :::::::::::::::::::::::::::
		
		when(animeRepository.findById(anyLong())).thenReturn(Optional.empty());
		
		//:::::::::::::::: THEN :::::::::::::::::::::::::::
		
		Optional<Anime> animeNotFound = animeServiceImpl.findAnimeById(34L);

		testReporter.publishEntry("Message", "Anime deleted successfully");

		assertTrue(animeNotFound.isEmpty());
		
		verify(animeRepository).findById(anyLong());
		verify(animeRepository).deleteById(anyLong());
		
	}
	
	@AfterEach
	void afterEach() {

	}
	
	@AfterAll
	static void afterAll() {

	}

}
