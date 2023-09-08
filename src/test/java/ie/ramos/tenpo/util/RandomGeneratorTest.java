package ie.ramos.tenpo.util;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RandomGeneratorTest {

    private final RandomGenerator randomGenerator = new RandomGenerator();

    @Test
    public void testGetRandomBoolean_FullProbability_ReturnsTrue() {
        boolean retrieved = randomGenerator.getRandomBoolean(1);
        assertThat(retrieved).isTrue();
    }

    @Test
    public void testGetRandomBoolean_ZeroProbability_ReturnsFalse() {
        boolean retrieved = randomGenerator.getRandomBoolean(0);
        assertThat(retrieved).isFalse();
    }
}
