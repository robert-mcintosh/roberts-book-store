import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.mc.bookstore.RobertsBookStoreApplication;
import com.mc.bookstore.controllers.DiscountController;
import com.mc.bookstore.model.entities.Discount;
import com.mc.bookstore.model.responses.DiscountRecord;
import com.mc.bookstore.service.DiscountService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(DiscountController.class)
@ContextConfiguration(classes = RobertsBookStoreApplication.class)
public class DiscountControllerTests {

  @Autowired private MockMvc mockMvc;

  @MockBean private DiscountService discountService;

  @Test
  public void testGetAllDiscounts() throws Exception {
    // Arrange
    List<Discount> discounts =
        Arrays.asList(
            new Discount("NEW", "New Release", 0, 0, 'N', null, null),
            new Discount("REG", "Regular", 0, 10, 'Y', null, null),
            new Discount("OLD", "Old Edition", 20, 5, 'Y', null, null));

    List<DiscountRecord> discountRecords = new ArrayList<>();
    discounts.forEach(
        discount -> discountRecords.add(new DiscountRecord().populateFromDiscount(discount)));

    when(discountService.getAllDiscounts()).thenReturn(discountRecords);

    // Act & Assert
    mockMvc
        .perform(get("/api/book/discount"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.size()").value(3))
        .andExpect(jsonPath("[0].description").value("New Release"))
        .andExpect(jsonPath("[1].bookType").value("REG"))
        .andExpect(jsonPath("[2].discountPercentage").value(20));
  }
}
