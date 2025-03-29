package ControllerTests;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.mc.bookstore.RobertsBookStoreApplication;
import com.mc.bookstore.controllers.PurchaseController;
import com.mc.bookstore.model.entities.Customer;
import com.mc.bookstore.model.entities.Purchase;
import com.mc.bookstore.model.requests.PurchaseRq;
import com.mc.bookstore.model.responses.CustomerRecord;
import com.mc.bookstore.model.responses.PurchaseItemRecord;
import com.mc.bookstore.model.responses.PurchaseRecord;
import com.mc.bookstore.service.PurchaseService;
import java.util.Arrays;
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
        .andExpect(jsonPath("$.purchaseId").value(1))
        .andExpect(jsonPath("$.customerId").value(2))
        .andExpect(jsonPath("$.totalPrice").value(200.0))
        .andExpect(jsonPath("$.refunded").value(false))
        .andExpect(jsonPath("$.purchaseItems[0].id").value(1))
        .andExpect(jsonPath("$.purchaseItems[0].purchaseId").value(1))
        .andExpect(jsonPath("$.purchaseItems[0].price").value(100.0))
        .andExpect(jsonPath("$.purchaseItems[0].discountPrice").value(100.0))
        .andExpect(jsonPath("$.purchaseItems[1].id").value(2))
        .andExpect(jsonPath("$.purchaseItems[1].purchaseId").value(1))
        .andExpect(jsonPath("$.purchaseItems[1].price").value(100.0))
        .andExpect(jsonPath("$.purchaseItems[1].discountPrice").value(100.0))
        .andExpect(jsonPath("$.customerInfo.name").value("James"))
        .andExpect(jsonPath("$.customerInfo.loyaltyPoints").value(1));
  }

  @Test
  public void testMakeAPurchase() throws Exception {
    // Arrange
    Purchase purchase = new Purchase();
    purchase.setCustomerId(1L);
    purchase.setTotalPrice(100D);
    purchase.setRefunded(false);

    PurchaseRq purchaseRq = new PurchaseRq();
    purchaseRq.setCustomerId(purchase.getCustomerId());
    purchaseRq.setBookIds(Arrays.asList(1L, 2L));

    PurchaseRecord purchaseRecord = new PurchaseRecord().populateFromPurchase(purchase);
    purchaseRecord.getPurchaseItems().add(new PurchaseItemRecord(1L, 1L, 1L, 100, 100));
    purchaseRecord.getPurchaseItems().add(new PurchaseItemRecord(1L, 1L, 2L, 100, 100));
    purchaseRecord.setCustomerInfo(
        new CustomerRecord().populateFromCustomer(new Customer("James", 0)));

    when(purchaseService.makePurchase(1L, purchaseRq.getBookIds())).thenReturn(purchaseRecord);

    // Act & Assert
    mockMvc
        .perform(
            post("/api/purchases")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"customerId\": 1, \"bookIds\": [1, 2]}"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.customerId").value(1))
        .andExpect(jsonPath("$.totalPrice").value(100.0))
        .andExpect(jsonPath("$.refunded").value(false))
        .andExpect(jsonPath("$.purchaseItems[0].id").value(1))
        .andExpect(jsonPath("$.purchaseItems[0].price").value(100.0))
        .andExpect(jsonPath("$.purchaseItems[1].id").value(1))
        .andExpect(jsonPath("$.purchaseItems[1].price").value(100.0))
        .andExpect(jsonPath("$.customerInfo.name").value("James"));
  }
}
