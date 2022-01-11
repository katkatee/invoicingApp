package pl.kotczykod.invoicing.invoicingapp.db.memory;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.kotczykod.invoicing.invoicingapp.db.Database;
import pl.kotczykod.invoicing.invoicingapp.model.Invoice;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static pl.kotczykod.invoicing.invoicingapp.Entries.invoice1;
import static pl.kotczykod.invoicing.invoicingapp.Entries.invoice2;

class InMemoryDatabaseTest {
    Database database = new InMemoryDatabase();

    @Test
    @DisplayName("Should save invoice to repository.")
    void test1() {

        //given, when
        database.save(invoice1);

        //then
        assertThat(database.getAllInvoices().get(0), is(invoice1));
    }

    @Test
    @DisplayName("Should find invoice by given id.")
    void  test2() {
        //given, when
        database.save(invoice1);
        Optional<Invoice> result = database.findInvoiceById(invoice1.getId());

        //then
        assertThat(database.getAllInvoices(), contains(result.get()));
    }

    @Test
    @DisplayName("Should update invoice with given id.")
    void  test3() {
        //given, when
        database.save(invoice1);
        database.update(invoice1.getId(), invoice2);

        //then
        assertThat(database.getAllInvoices(), contains(invoice2));
    }

    @Test
    @DisplayName("Should throw an exception when invoice with given id does not exists.")
    void  test4() {
        //given, when
        //then
        assertThrows(IllegalArgumentException.class, () -> database.update(invoice1.getId(), invoice2));
    }

    @Test
    @DisplayName("Should return empty Optional when there is no invoice with given id.")
    void  test5() {
        //given, when
        //then
        assertEquals(Optional.empty(), database.findInvoiceById(22l));
    }

    @Test
    @DisplayName("Should delete invoice with given id.")
    void  test6() {
        //given, when
        database.save(invoice1);
        database.delete(invoice1.getId());
        //then
        assertThat(database.getAllInvoices(), CoreMatchers.not(contains(invoice1)));
    }

    @Test
    @DisplayName("Should throw an exception when invoice with given id does not exists.")
    void  test7() {
        //given, when
        //then
        assertThrows(IllegalArgumentException.class, () -> database.delete(22l));
    }

}