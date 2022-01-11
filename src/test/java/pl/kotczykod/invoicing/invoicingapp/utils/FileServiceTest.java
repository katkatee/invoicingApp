package pl.kotczykod.invoicing.invoicingapp.utils;

import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static pl.kotczykod.invoicing.invoicingapp.Entries.invoice1;
import static pl.kotczykod.invoicing.invoicingapp.Entries.invoice2;

class FileServiceTest {
    private FileService service = new FileService();
    private Path path;

    {
        try {
            path = File.createTempFile("lines", ".txt").toPath();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SneakyThrows
    @Test
    @DisplayName("Line is correctly append to file")
    void test1() {
        //given, when
        service.appendLineToFile(path, invoice1.toString());
        service.appendLineToFile(path, invoice2.toString());

        //then
        assertThat(Files.readAllLines(path), hasItem(invoice1.toString()));
        assertThat(Files.readAllLines(path), hasItem(invoice2.toString()));
        assertThat(Files.readAllLines(path), hasSize(2));
    }

    @SneakyThrows
    @Test
    @DisplayName("Line is correctly written to file")
    void test3() {
        //given, when
        service.writeToFile(path, invoice1.toString());
        service.writeToFile(path, invoice2.toString());

        //then
        assertThat(Files.readAllLines(path), hasItem(invoice2.toString()));
        assertThat(Files.readAllLines(path), not(hasItem(invoice1.toString())));
        assertThat(Files.readAllLines(path), hasSize(1));
    }

    @SneakyThrows
    @Test
    @DisplayName("List of line is correctly written to file")
    void test4() {
        //given, when
        service.writeLinesToFile(path, List.of(invoice1.toString(), invoice2.toString()));

        //then
        assertThat(Files.readAllLines(path), hasItem(invoice1.toString()));
        assertThat(Files.readAllLines(path), hasItem(invoice2.toString()));
        assertThat(Files.readAllLines(path), hasSize(2));
    }

    @SneakyThrows
    @Test
    @DisplayName("Line is correctly read from file")
    void test5() {
        //given, when
        service.writeToFile(path, invoice1.toString());

        //then
        assertThat(Files.readAllLines(path), hasItem(invoice1.toString()));
    }

    @SneakyThrows
    @Test
    @DisplayName("Empty file returns empty collection")
    void test6() {
        //given, when
        //then
        assertThat(Files.readAllLines(path), hasSize(0));
    }
}
