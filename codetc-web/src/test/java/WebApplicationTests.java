import com.codetc.WebApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by anvin on 2/4/2021.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WebApplicationTests {

    @Test
    public void test() {
        System.out.println("Hello world.");
    }
}