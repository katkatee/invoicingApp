package pl.invoicingapp.service;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.invoicingapp.Entries;
import pl.invoicingapp.db.Database;
import pl.invoicingapp.db.memory.InMemoryDatabase;
import pl.invoicingapp.model.Invoice;

import java.math.BigDecimal;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class InvoiceServiceIntegrationTest {
    private static InvoiceService service;

    @BeforeAll
    static void setUp() {
        Database database = new InMemoryDatabase();
        service = new InvoiceService(database);
    }

    @Test
    @DisplayName("Should throw an exception when invoice with given id does not exists.")
    void  test4() {
        //given, when
        //then
        assertThrows(IllegalArgumentException.class,
                () -> service.update(33L, Entries.invoice2));
    }

    @Test
    @DisplayName("Should save invoices to repository.")
    void test1() {

        //given, when
        Long id1 = service.save(Entries.invoice1);
        Long id2 = service.save(Entries.invoice2);
        Long id3 = service.save(Entries.invoice3);

        //then
        assertThat(service.findByInvoiceId(1L).get(), Matchers.is(Entries.invoice1));
        assertThat(service.getAllInvoices(), hasSize(3));
        MatcherAssert.assertThat(id2, Matchers.is(Entries.invoice2.getId()));
    }

    @Test
    @DisplayName("Should find invoice by given id.")
    void  test2() {
        //given, when
        service.save(Entries.invoice2);
        Optional<Invoice> result = service.findByInvoiceId(2L);
        //then
        assertThat(service.getAllInvoices(), hasItem(result.get()));
    }

    @Test
    @DisplayName("Should update invoice with given id.")
    void  test3() {
        //given, when
        service.save(Entries.invoice1);
        service.update(1L, Entries.invoice2);

        //then
        assertThat(service.getAllInvoices().get(0).getId(), equalTo(1L));
        assertThat(service.getAllInvoices().get(0).getEntries().get(0).getPrice(),
                equalTo(BigDecimal.valueOf(65.25)));
    }

    @Test
    @DisplayName("Should return empty Optional when there is no invoice with given id.")
    void  test5() {
        //given, when
        //then
        assertEquals(Optional.empty(), service.findByInvoiceId(22l));
    }

    @Test
    @DisplayName("Should delete invoice with given id.")
    void  test6() {
        //given, when
        service.save(Entries.invoice1);
        service.delete(Entries.invoice1.getId());
        //then
        MatcherAssert.assertThat(service.getAllInvoices(), CoreMatchers.not(Matchers.contains(Entries.invoice1)));
    }

    @Test
    @DisplayName("Should throw an exception when invoice with given id does not exists.")
    void  test7() {
        //given, when
        //then
        assertThrows(IllegalArgumentException.class, () -> service.delete(22l));
    }
}
