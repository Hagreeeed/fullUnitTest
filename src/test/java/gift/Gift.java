package gift;

import model.Sweet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GiftTest {

    private Gift gift;

    @BeforeEach
    void setUp() {
        gift = new Gift(1, "Birthday Gift", 0.0);
    }

    @Test
    void testAddSweet() {
        // Arrange
        Sweet sweet = new Sweet("Chocolate", 100.0, 50.0);

        // Act
        gift.addSweet(sweet);

        // Assert
        assertEquals(1, gift.getSweets().size(), "The number of sweets in the gift should be 1");
        assertEquals(sweet, gift.getSweets().get(0), "The added sweet should be present in the gift");
    }

    @Test
    void testGetTotalWeight() {
        // Arrange
        Sweet sweet1 = new Sweet("Candy", 50.0, 20.0);
        Sweet sweet2 = new Sweet("Chocolate", 100.0, 50.0);
        gift.addSweet(sweet1);
        gift.addSweet(sweet2);

        // Act
        double totalWeight = gift.getTotalWeight();

        // Assert
        assertEquals(150.0, totalWeight, "Total weight should be the sum of weights of all sweets");
    }

    @Test
    void testSortSweetsByWeight() {
        // Arrange
        Sweet sweet1 = new Sweet("Candy", 50.0, 20.0);
        Sweet sweet2 = new Sweet("Chocolate", 100.0, 50.0);
        Sweet sweet3 = new Sweet("Marshmallow", 30.0, 10.0);
        gift.addSweet(sweet1);
        gift.addSweet(sweet2);
        gift.addSweet(sweet3);

        // Act
        gift.sortSweets(Comparator.comparingDouble(Sweet::getWeight));

        // Assert
        List<Sweet> sortedSweets = gift.getSweets();
        assertEquals("Marshmallow", sortedSweets.get(0).getName(), "Sweets should be sorted by weight in ascending order");
        assertEquals("Candy", sortedSweets.get(1).getName(), "Sweets should be sorted by weight in ascending order");
        assertEquals("Chocolate", sortedSweets.get(2).getName(), "Sweets should be sorted by weight in ascending order");
    }

    @Test
    void testFindSweetsBySugarContent() {
        // Arrange
        Sweet sweet1 = new Sweet("Candy", 50.0, 20.0);
        Sweet sweet2 = new Sweet("Chocolate", 100.0, 50.0);
        Sweet sweet3 = new Sweet("Marshmallow", 30.0, 10.0);
        gift.addSweet(sweet1);
        gift.addSweet(sweet2);
        gift.addSweet(sweet3);

        // Act
        List<Sweet> filteredSweets = gift.findSweetsBySugarContent(15.0, 50.0);

        // Assert
        assertEquals(2, filteredSweets.size(), "The number of sweets with sugar content in the range should be 2");
        assertTrue(filteredSweets.stream().anyMatch(sweet -> sweet.getName().equals("Candy")), "Filtered list should contain 'Candy'");
        assertTrue(filteredSweets.stream().anyMatch(sweet -> sweet.getName().equals("Chocolate")), "Filtered list should contain 'Chocolate'");
    }

    @Test
    void testGetIdAndName() {
        // Assert
        assertEquals(1, gift.getId(), "Gift ID should match the initialized value");
        assertEquals("Birthday Gift", gift.getName(), "Gift name should match the initialized value");
    }
}
