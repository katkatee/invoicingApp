package pl.kotczykod.invoicing.invoicingapp.db.file;

import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.kotczykod.invoicing.invoicingapp.utils.FileService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class IdServiceTest {
    private Path nextIdFileTemp;

    {
        try {
            nextIdFileTemp = File.createTempFile("nextId", ".txt").toPath();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SneakyThrows
    @Test
    @DisplayName("Next id starts from 1 if file was empty")
    public void test1() {
        //given, when
        IdService service = new IdService(nextIdFileTemp, new FileService());

        //then
        assertThat(Files.readAllLines(nextIdFileTemp), hasSize(1));
        assertThat(Files.readAllLines(nextIdFileTemp).get(0),is("1") );
    }

    @SneakyThrows
    @Test
    @DisplayName("Next id starts from last number if file was not empty")
    public void test2() {
        //given
        Files.writeString(nextIdFileTemp,"3");
        IdService service = new IdService(nextIdFileTemp, new FileService());

        //when
        service.getNextIdAndIncrement();

        //then
        assertThat(Files.readAllLines(nextIdFileTemp).get(0),is("4"));
    }

}