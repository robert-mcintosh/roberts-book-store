import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.mc.bookstore.controllers.BookController;
import com.mc.bookstore.model.entities.Book;
import com.mc.bookstore.service.BookService;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(BookController.class)
public class BookControllerTests {

  @Autowired private MockMvc mockMvc;

  @MockBean private BookService bookService;

  @Test
  public void testGetAllBooks() throws Exception {
    List<Book> books = Arrays.asList(new Book("Title1", "Author1", 100.0,"REG", 5));
    when(bookService.getAllBooks()).thenReturn(books);

    //        mockMvc.perform(get("/api/books"))
    //                .andExpect(status().isOk())
    //                .andExpect(jsonPath("$[0].title").value("Title1"));
  }

  @Test
  public void testAddBook() throws Exception {
    Book book = new Book("Title1", "Author1", 100.0,"REG", 5);
    when(bookService.addBook(any(Book.class))).thenReturn(book);

    //        mockMvc.perform(post("/api/books")
    //                        .contentType(MediaType.APPLICATION_JSON)
    //
    // .content("{\"title\":\"Title1\",\"author\":\"Author1\",\"price\":100.0,\"type\":\"New
    // Release\"}"))
    //                .andExpect(status().isOk())
    //                .andExpect(jsonPath("$.title").value("Title1"));
  }

  @Test
  public void testDeleteBook() throws Exception {
    doNothing().when(bookService).deleteBook(1L);

    //        mockMvc.perform(delete("/api/books/1"))
    //                .andExpect(status().isOk());
  }
}
