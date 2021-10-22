package co.edu.unbosque.tiendagenerica.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import co.edu.unbosque.tiendagenerica.model.Producto;
import co.edu.unbosque.tiendagenerica.utils.CSVHelper;
import co.edu.unbosque.tiendagenerica.utils.CSVService;
import co.edu.unbosque.tiendagenerica.utils.ResponseMessagesCSV;

@CrossOrigin("http://localhost:8080")
@Controller
@RequestMapping("/api/csv")
public class CSVController {

  @Autowired
  CSVService fileService;

  @PostMapping("/upload")
  public ResponseEntity<ResponseMessagesCSV> uploadFile(@RequestParam("file") MultipartFile file) {
	  
    String message = "";

    if (CSVHelper.hasCSVFormat(file)) {
      try {
        fileService.save(file);

        message = "El archivo se cargo correctamente!: " + file.getOriginalFilename();
        
        /*
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/csv/download/")
                .path(file.getOriginalFilename())
                .toUriString(); */

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessagesCSV(message));
      } catch (Exception e) {
        message = "No se pudo cargar el archivo: " + file.getOriginalFilename() + "!";
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessagesCSV(message));
      }
    }

    message = "Please upload a csv file!";
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessagesCSV(message));
  }
  
  // Metodo para retornar los productos en JSON

  @GetMapping("/productos")
  public ResponseEntity<List<Producto>> getAllProducts() {
    try {
      List<Producto> productos = fileService.getAllProducts();

      if (productos.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }

      return new ResponseEntity<>(productos, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
  
  /*
   * Metodo para descargar el archivo CSV.
   * 
  @GetMapping("/download/{fileName:.+}")
  public ResponseEntity<Resource> downloadFile(@PathVariable String fileName) {
    InputStreamResource file = new InputStreamResource(fileService.load());

    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName)
        .contentType(MediaType.parseMediaType("application/csv"))
        .body(file);
  }
  */
}