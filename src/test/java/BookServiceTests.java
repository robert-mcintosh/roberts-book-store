import com.mc.bookstore.model.entities.Book;
import com.mc.bookstore.repository.BookRepository;
import com.mc.bookstore.service.BookService;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BookServiceTests {

  @Autowired private BookService bookService;

  @MockBean private BookRepository bookRepository;

  @Test
  public void testGetAllBooks() {
    //  public Book(String title, String author, double price, String type) {
    List<Book> books = Arrays.asList(new Book("Title1", "Author1", 100.0,"REG", 5));
//    when(bookRepository.findAll()).thenReturn(books);
//
//    List<Book> result = bookService.getAllBooks();
//    assertEquals(1, result.size());
//    assertEquals("Title1", result.get(0).getTitle());
  }

  @Test
  public void testAddBook() {
    Book book = new Book("Title1", "Author1", 100.0,"REG", 5);
//    when(bookRepository.save(book)).thenReturn(book);
//
//    Book result = bookService.addBook(book);
//    assertEquals("Title1", result.getTitle());
  }

  @Test
  public void testDeleteBook() {
    Long bookId = 1L;
//    doNothing().when(bookRepository).deleteById(bookId);
//
//    bookService.deleteBook(bookId);
//    verify(bookRepository, times(1)).deleteById(bookId);
  }
}
