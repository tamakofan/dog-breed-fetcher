package dogapi;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class DogApiBreedFetcherTest {

    @Test
    void testValidBreedReturnsSubBreeds() throws BreedFetcher.BreedNotFoundException {
        BreedFetcher fetcher = new BreedFetcherForLocalTesting();
        List<String> subBreeds = fetcher.getSubBreeds("hound");
        Set<String> expected = new HashSet<>(List.of("afghan", "basset", "blood", "english", "ibizan", "plott", "walker"));
        assertEquals(expected, new HashSet<>(subBreeds));
    }

    @Test
    void testInvalidBreedThrowsException() {
        BreedFetcher fetcher = new BreedFetcherForLocalTesting();
        assertThrows(BreedFetcher.BreedNotFoundException.class, () -> fetcher.getSubBreeds("cat"));
    }
}