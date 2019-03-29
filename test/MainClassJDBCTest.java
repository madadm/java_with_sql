import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MainClassJDBCTest {
    @Test
    public void testSum(){
        int res = MainClassJDBC.sum(33,12);
        assertEquals(45, res);
    }
}
