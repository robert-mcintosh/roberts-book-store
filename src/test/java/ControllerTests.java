import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.ApplicationContext;

@Getter
public class ControllerTests {

  @Autowired @Setter private ApplicationContext applicationContext;

  @Autowired @Setter private TestRestTemplate restTemplate;

  @LocalServerPort private int port;

  @BeforeEach
  public void setup() {}
}
