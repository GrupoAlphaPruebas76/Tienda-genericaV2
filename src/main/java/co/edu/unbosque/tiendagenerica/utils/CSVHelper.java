package co.edu.unbosque.tiendagenerica.utils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.csv.QuoteMode;
import org.springframework.web.multipart.MultipartFile;

import co.edu.unbosque.tiendagenerica.model.Producto;


public class CSVHelper {
	
  public static String TYPE = "text/csv";
  static String[] HEADERs = { "codigoProducto", "ivacompra", "nitproveedor", "nombre_producto", "precio_compra", "precio_venta" };

  public static boolean hasCSVFormat(MultipartFile file) {
    if (TYPE.equals(file.getContentType())
    		|| file.getContentType().equals("application/vnd.ms-excel")) {
      return true;
    }

    return false;
  }
  
  // Metodo para enviar datos desde CSV file a la base de datos // cambie csvToTutorials for products (InputStream)

  public static List<Producto> csvToProducts(InputStream is) {
    try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
        CSVParser csvParser = new CSVParser(fileReader,
            CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

      List<Producto> productoList = new ArrayList<>();

      Iterable<CSVRecord> csvRecords = csvParser.getRecords();

      for (CSVRecord csvRecord : csvRecords) {
    	  
    	  Producto producto = new Producto(
              Long.parseLong(csvRecord.get("codigoProducto")),
              Double.parseDouble(csvRecord.get("ivacompra")),
              null, Long.parseLong(csvRecord.get("nitproveedor")),
              csvRecord.get("nombre_producto"),
              Double.parseDouble(csvRecord.get("precio_compra")),
              Double.parseDouble(csvRecord.get("precio_venta"))
            );

    	  productoList.add(producto);
      }

      return productoList;
    } catch (IOException e) {
      throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
    }
  }
  
  // Metodo para traer los datos desde la base de datos y convertirlos a CSV file con el metodo CSVPrinter
  
  /*
  public static ByteArrayInputStream productsToCSV(List<Producto> productoList) {
    final CSVFormat format = CSVFormat.DEFAULT.withQuoteMode(QuoteMode.MINIMAL);

    try (ByteArrayOutputStream out = new ByteArrayOutputStream();
        CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format);) {
      for (Producto producto : productoList) {
        List<Object> data = Arrays.asList(
              String.valueOf(producto.getCodigoProducto()),
              producto.getIvaCompra(),
              String.valueOf(producto.getNitProveedor()),
              producto.getNombreProducto(),
              String.valueOf(producto.getPrecioCompra()),
              String.valueOf(producto.getPrecioVenta())
            );

        csvPrinter.printRecord(data);
      }

      csvPrinter.flush();
      return new ByteArrayInputStream(out.toByteArray());
    } catch (IOException e) {
      throw new RuntimeException("fail to import data to CSV file: " + e.getMessage());
    }
  }
  */
  
}
