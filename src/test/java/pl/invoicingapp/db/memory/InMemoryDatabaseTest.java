package pl.invoicingapp.db.memory;

import pl.invoicingapp.db.AbstractDatabaseTest;
import pl.invoicingapp.db.Database;

class InMemoryDatabaseTest extends AbstractDatabaseTest {

    @Override
    public Database getDatabaseInstance() {
        return new InMemoryDatabase();
    }
}