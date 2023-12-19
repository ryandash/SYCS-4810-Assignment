import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Tests the getType method from the PermType class
 *
 * @author Ryan Dash
 */
class PermTypeTest {


    @Test
    public void testGetType() {
        Assertions.assertNull(PermType.getType("Thing"));
        Assertions.assertEquals(PermType.getType("View"), PermType.VIEW);
        Assertions.assertEquals(PermType.getType("Modify"), PermType.MODIFY);
        Assertions.assertEquals(PermType.getType("Validate"), PermType.VALIDATE);
    }
}