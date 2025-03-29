package ControllerTests;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.mc.bookstore.RobertsBookStoreApplication;
import com.mc.bookstore.controllers.BookController;
import com.mc.bookstore.controllers.PurchaseController;
import com.mc.bookstore.model.entities.Book;
import com.mc.bookstore.model.entities.Customer;
import com.mc.bookstore.model.requests.BookRq;
import com.mc.bookstore.model.requests.PurchaseRq;
import com.mc.bookstore.model.responses.BookRecord;
import com.mc.bookstore.model.responses.CustomerRecord;
import com.mc.bookstore.model.responses.PurchaseItemRecord;
import com.mc.bookstore.model.responses.PurchaseRecord;
import com.mc.bookstore.service.BookService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.mc.bookstore.service.PurchaseService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(PurchaseController.class)
@ContextConfiguration(classes = RobertsBookStoreApplication.class)
public class PurchaseControllerTests {

  @Autowired private MockMvc mockMvc;

  @MockBean private PurchaseService purchaseService;

  @Test
  public void testGetPurchase() throws Exception {
    // Arrange
    Customer customer = new Customer("James", 1);

    PurchaseRecord purchaseRecord = new PurchaseRecord();
    purchaseRecord.setPurchaseId(1L);
    purchaseRecord.setCustomerId(2L);
    purchaseRecord.setRefunded(false);
    purchaseRecord.setTotalPrice(200D);
    purchaseRecord.getPurchaseItems().add(new PurchaseItemRecord(1L,1L,1L,100,100));
    purchaseRecord.getPurchaseItems().add(new PurchaseItemRecord(2L,1L,2L,100,100));
    purchaseRecord.setCustomerInfo(new CustomerRecord().populateFromCustomer(customer));

    when(purchaseService.getPurchase(1L)).thenReturn(purchaseRecord);

    // Act & Assert
    mockMvc
        .perform(get("/api/purchases").param("purchaseId", "1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath(".purchaseId").value(1))
        .andExpect(jsonPath(".customerId").value(2))
        .andExpect(jsonPath(".purchaseItems.purchaseId[0]").value(1))
        .andExpect(jsonPath(".purchaseItems.price[1]").value(100))
        .andExpect(jsonPath(".customerInfo.name").value("James"));
  }

  @Test
  public void testMakeAPurchase() throws Exception {}

  @Test
  public void testRefundPurchase() throws Exception {}


}
