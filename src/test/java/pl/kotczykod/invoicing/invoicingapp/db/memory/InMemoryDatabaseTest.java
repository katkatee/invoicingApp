package pl.kotczykod.invoicing.invoicingapp.db.memory;

import pl.kotczykod.invoicing.invoicingapp.db.AbstractDatabaseTest;
import pl.kotczykod.invoicing.invoicingapp.db.Database;

class InMemoryDatabaseTest extends AbstractDatabaseTest {

    @Override
    public Database getDatabaseInstance() {
        return new InMemoryDatabase();
    }
}