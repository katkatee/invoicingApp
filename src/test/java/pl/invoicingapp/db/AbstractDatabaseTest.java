package pl.invoicingapp.db;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.invoicingapp.Entries;
import pl.invoicingapp.model.Invoice;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public abstract class AbstractDatabaseTest {

    Database database = getDatabaseInstance();

    public abstract Database getDatabaseInstance();

    @Test
    @DisplayName("Should save invoice to repository.")
    void test1() {

        //given, when
        var id = database.save(Entries.invoice1);

        //then
        MatcherAssert.assertThat(id, Matchers.is(Entries.invoice1.getId()));
    }

    @Test
    @DisplayName("Should find invoice by given id.")
    void test2() {
        //given
        Long id = database.save(Entries.invoice1);
        Long id2 = database.save(Entries.invoice2);

        //when
        Optional<Invoice> invoice = database.findInvoiceById(id);


        //then
        Assertions.assertEquals(invoice.get(), Entries.invoice1);
    }

    @Test
    @DisplayName("Should update invoice with given id.")
    void test3() {
        //given, when
        database.save(Entries.invoice1);
        database.update(Entries.invoice1.getId(), Entries.invoice2);

        //then
        MatcherAssert.assertThat(database.getAllInvoices(), Matchers.contains(Entries.invoice2));
    }

    @Test
    @DisplayName("Should throw an exception when invoice with given id does not exists.")
    void test4() {
        //given, when
        //then
        assertThrows(IllegalArgumentException.class,
                () -> database.update(Entries.invoice1.getId(), Entries.invoice2));
    }

    @Test
    @DisplayName("Should return empty Optional when there is no invoice with given id.")
    void test5() {
        //given, when
        //then
        assertEquals(Optional.empty(), database.findInvoiceById(22l));
    }

    @Test
    @DisplayName("Should delete invoice with given id.")
    void test6() {
        //given, when
        database.save(Entries.invoice1);
        database.delete(Entries.invoice1.getId());
        //then
        MatcherAssert.assertThat(database.getAllInvoices(), CoreMatchers.not(Matchers.contains(Entries.invoice1)));
    }

    @Test
    @DisplayName("Should throw an exception when invoice with given id does not exists.")
    void test7() {
        //given, when
        //then
        assertThrows(RuntimeException.class, () -> database.delete(202l));
    }
}
