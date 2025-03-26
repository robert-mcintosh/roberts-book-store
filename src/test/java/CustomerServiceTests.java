import com.mc.bookstore.entities.Customer;
import com.mc.bookstore.repository.CustomerRepository;
import com.mc.bookstore.service.CustomerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomerServiceTests {

    @Autowired
    private CustomerService customerService;

    @MockBean
    private CustomerRepository customerRepository;

    @Test
    public void testGetCustomer() {
        Customer customer = new Customer("Customer1", 0);
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

        Customer result = customerService.getCustomer(1L);
        assertEquals("Customer1", result.getName());
    }

    @Test
    public void testUpdateLoyaltyPoints() {
        Customer customer = new Customer("Customer1", 0);
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(customerRepository.save(customer)).thenReturn(customer);

        Customer result = customerService.updateLoyaltyPoints(1L, 10);
        assertEquals(10, result.getLoyaltyPoints());
    }
}
