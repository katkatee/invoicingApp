package pl.kotczykod.invoicing.invoicingapp.service;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.kotczykod.invoicing.invoicingapp.db.Database;
import pl.kotczykod.invoicing.invoicingapp.model.Invoice;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static pl.kotczykod.invoicing.invoicingapp.Entries.invoice1;
import static pl.kotczykod.invoicing.invoicingapp.Entries.invoice2;

class InvoiceServiceTest {
    Database database = mock(Database.class);
    InvoiceService service = new InvoiceService(database);

    @Test
    @DisplayName("Calling save() should delegate to database method.")
    void Test1() {
        //given, when
        service.save(invoice1);

        //then
        verify(database, times(1)).save(invoice1);
    }

    @Test
    @DisplayName("Calling update() should delegate to database method.")
    void Test2() {
        //given, when
        service.update(1L, invoice2);

        //then
        verify(database, times(1)).update(1L,invoice2);
    }

    @Test
    @DisplayName("Calling findByInvoiceId() should delegate to database method.")
    void Test3() {
        //given, when
        Optional<Invoice> result = service.findByInvoiceId(1L);

        //then
        verify(database, times(1)).findInvoiceById(1L);
    }

    @Test
    @DisplayName("Calling getAllInvoices() should delegate to database method.")
    void Test5() {
        //given, when
        List<Invoice> invoices = service.getAllInvoices();

        //then
        verify(database, times(1)).getAllInvoices();
    }

    @Test
    @DisplayName("Calling getAllInvoices() should delegate to database method.")
    void Test6() {
        //given, when
        service.delete(1L);

        //then
        verify(database, times(1)).delete(1L);
    }


    @Test
    void  test22() throws JSONException {

    String msg = " [400 : \"{\"code\":\"WL-109\",\"message\":\"Pole 'numer konta' ma nieprawidłową długość. Wymagane 26 znaków.\"}\"]";
    msg = msg.replaceFirst("400 : \"", "");
    msg = msg.substring(0,msg.length()-2) + "]";

    //StringBuilder builder = msg.replaceFirst("400 : \"", "").msg.substring(0,msg.length()-2) + "]".build();


        JSONArray array = new JSONArray(msg);

            JSONObject object = array.getJSONObject(0);
            System.out.println(object.getString("code"));
            System.out.println(object.getString("message"));



    }


}