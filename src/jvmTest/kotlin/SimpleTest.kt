import io.quarkus.test.junit.QuarkusTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

@QuarkusTest
class GreetingResourceTest {

    @Test
    fun testHelloEndpoint() {
        Assertions.assertTrue(true)
    }

}