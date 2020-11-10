package org.coding.tools;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.coding.pojo.Route;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class RouteDataReader {
    public static List<Route> readFile(File csvFile) {
        CsvMapper mapper = new CsvMapper();
        CsvSchema schema = CsvSchema.builder()
                .addColumn("id")
                .addColumn("fromSeq", CsvSchema.ColumnType.NUMBER)
                .addColumn("toSeq", CsvSchema.ColumnType.NUMBER)
                .addColumn("fromPort")
                .addColumn("toPort")
                .addColumn("legDuration", CsvSchema.ColumnType.NUMBER)
                .addColumn("count", CsvSchema.ColumnType.NUMBER)
                .addColumn("routeGeometry")
                .build()
                .withHeader()
                .withColumnSeparator(',')
                .withQuoteChar('"');

        try {
            MappingIterator<Route> routeIterator = mapper.readerWithTypedSchemaFor(Route.class)
                    .with(schema)
                    .readValues(csvFile);
            return routeIterator.readAll();
        } catch (IOException ex) {
            throw new RuntimeException("Exception during parsing CSV file", ex);
        }
    }

}

