import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.mc.bookstore.controllers.BookController;
import com.mc.bookstore.model.entities.Book;
import com.mc.bookstore.model.requests.BookRq;
import com.mc.bookstore.model.responses.BookRecord;
import com.mc.bookstore.service.BookService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(BookController.class)
public class BookControllerTests {

  @Autowired private MockMvc mockMvc;

  @MockBean private BookService bookService;

  @Test
  public void testGetAllBooks() throws Exception {
    // Arrange
    List<Book> books =
        Arrays.asList(
            new Book(1L, "Title1", "Author1", 5, 100.0, "REG"),
            new Book(2L, "Title2", "Author2", 10, 150.0, "REG"));

    List<BookRecord> bookRecords = new ArrayList<>();
    books.forEach(book -> bookRecords.add(new BookRecord().populateFromBook(book)));

    when(bookService.getAllBooks()).thenReturn(bookRecords);

    // Act & Assert
    mockMvc
        .perform(get("/api/books"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.size()").value(2))
        .andExpect(jsonPath("$[0].title").value("Title1"))
        .andExpect(jsonPath("$[0].author").value("Author1"))
        .andExpect(jsonPath("$[1].title").value("Title2"))
        .andExpect(jsonPath("$[1].author").value("Author2"));
  }

  @Test
  public void testAddBook() throws Exception {
    // Arrange
    Book book = new Book(1L, "Title1", "Author1", 5, 100.0, "REG");

    BookRq bookRq = populateBookRqFromBook(book);
    BookRecord bookRecord = new BookRecord().populateFromBook(book);

    when(bookService.addBook(bookRq)).thenReturn(bookRecord);

    // Act & Assert
    mockMvc
        .perform(
            post("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    "{\"title\":\"Title1\",\"author\":\"Author1\",\"price\":100.0,\"type\":\"REG\",\"qty\":5}"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.title").value("Title1"))
        .andExpect(jsonPath("$.author").value("Author1"));
  }

  @Test
  public void testDeleteBook() throws Exception {
    // Arrange
    doNothing().when(bookService).deleteBook(1L);

    // Act & Assert
    mockMvc.perform(delete("/api/books/1")).andExpect(status().isOk());
  }


  private static BookRq populateBookRqFromBook(Book book) {
    BookRq bookRq =
            new BookRq(
                    book.getId(),
                    book.getTitle(),
                    book.getAuthor(),
                    book.getQty(),
                    book.getPrice(),
                    book.getType());
    return bookRq;
  }

}
