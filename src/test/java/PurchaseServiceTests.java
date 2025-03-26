import com.mc.bookstore.entities.Book;
import com.mc.bookstore.entities.Customer;
import com.mc.bookstore.entities.Purchase;
import com.mc.bookstore.repository.BookRepository;
import com.mc.bookstore.repository.PurchaseRepository;
import com.mc.bookstore.service.CustomerService;
import com.mc.bookstore.service.PurchaseService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PurchaseServiceTests {

    @Autowired
    private PurchaseService purchaseService;

    @MockBean
    private PurchaseRepository purchaseRepository;

    @MockBean
    private BookRepository bookRepository;

    @MockBean
    private CustomerService customerService;

    @Test
    public void testMakePurchase() {
        Customer customer = new Customer("Customer1", 0);
        Book book = new Book("Title1", "Author1", 100.0, "New Release");
        List<Book> books = Arrays.asList(book);

        when(customerService.getCustomer(1L)).thenReturn(customer);
        when(bookRepository.findAllById(Arrays.asList(1L))).thenReturn(books);
        when(purchaseRepository.save(any(Purchase.class))).thenReturn(new Purchase());

        Purchase result = purchaseService.makePurchase(1L, Arrays.asList(1L));
        assertEquals(1, result.getBooks().size());
        assertEquals(100.0, result.getTotalPrice(), 0.01);
    }

    @Test
    public void testCalculateTotalPrice() {
        Book book1 = new Book("Title1", "Author1", 100.0, "New Release");
        Book book2 = new Book("Title2", "Author2", 80.0, "Regular");
        List<Book> books = Arrays.asList(book1, book2);

        double result = purchaseService.calculateTotalPrice(books);
        assertEquals(180.0, result, 0.01);
    }
}
