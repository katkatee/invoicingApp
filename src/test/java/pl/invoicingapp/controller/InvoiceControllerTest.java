package pl.invoicingapp.controller;

import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pl.invoicingapp.model.Invoice;
import pl.invoicingapp.utils.JsonService;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static pl.invoicingapp.Entries.*;


@SpringBootTest
@AutoConfigureMockMvc
class InvoiceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JsonService jsonService;

    @BeforeEach
    public void setup() throws Exception {
        List<Invoice> list = getAllInvoices();
        if (!list.isEmpty()) {
            for (Invoice invoice : list) {
                deleteInvoice(invoice.getId());
            }
        }
    }

    @SneakyThrows
    private void deleteInvoice(Long id) {
        mockMvc.perform(delete("/invoices/api/{id}", id))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Returns empty array when no invoices were created")
    void Test1() throws Exception {
        //given, when
        String response = mockMvc.perform(MockMvcRequestBuilders.get("/invoices/api"))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        //then
        assertThat(response, is("[]"));
    }

    @Test
    @DisplayName("Correct invoice is returned when getting by id")
    void Test2() throws Exception {
        //given
        Long id = addInvoice(invoice1);

        //when
        String response = mockMvc.perform(MockMvcRequestBuilders.get("/invoices/api/1").contentType(MediaType.APPLICATION_JSON).characterEncoding(StandardCharsets.UTF_8)).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        Invoice result = jsonService.toObject(response, Invoice.class);

        //then
        assertThat(result, equalTo(invoice1));
    }

    @Test
    @DisplayName("Add invoice returns sequential id")
    void Test3() throws Exception {
        //given, when
        Long id = addInvoice(invoice1);

        //then
        assertThat(id, equalTo(getById(id).getId()));
    }

    @Test
    @DisplayName("All invoices are returned when getting all invoices")
    void Test4() throws Exception {
        //given
        Long id = addInvoice(invoice3);
        Long idSecond = addInvoice(invoice2);

        //when
        System.out.println("lolo bum");
        List<Invoice> list = getAllInvoices();

        //then
        assertThat(list, hasSize(2));
    }

    @Test
    @DisplayName("404 is returned when invoice id is not found when getting invoice by id")
    void Test5() throws Exception {
        //given
        Long id = addInvoice(invoice1);

        //when, then
        mockMvc.perform(get("/invoices/api/11")).andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("404 is returned when invoice id is not found when deleting invoice by id")
    void Test6() throws Exception {
        //given
        Long id = addInvoice(invoice1);

        //when, then
        mockMvc.perform(delete("/invoices/api/11")).andExpect(status().isNotFound());
    }

    private Long addInvoice(Invoice invoice) throws Exception {
        String invoiceAsJson = jsonService.toJson(invoice);
        return Long.valueOf(mockMvc.perform(post("/invoices/api").content(invoiceAsJson).contentType(MediaType.APPLICATION_JSON).characterEncoding(StandardCharsets.UTF_8)).andExpect(status().isOk()).andReturn().getResponse().getContentAsString());
    }

    private List<Invoice> getAllInvoices() throws Exception {
        String response = mockMvc.perform(MockMvcRequestBuilders.get("/invoices/api")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        return List.of(jsonService.toObject(response, Invoice[].class));

    }

    private Invoice getById(Long id) throws Exception {
        String invoice = mockMvc.perform(get("/invoices/api/{id}", id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

                return jsonService.toObject(invoice, Invoice.class);
    }
}