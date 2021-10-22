package co.edu.unbosque.tiendagenerica.utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import co.edu.unbosque.tiendagenerica.dao.ProductoDAO;
import co.edu.unbosque.tiendagenerica.model.Producto;

@Service
public class CSVService {
  @Autowired
  ProductoDAO repository;

  // Metodo para hacer upload del documento a traves de @Service
  public void save(MultipartFile file) {
    try {
      List<Producto> products = CSVHelper.csvToProducts(file.getInputStream());
      repository.saveAll(products);
    } catch (IOException e) {
      throw new RuntimeException("fail to store csv data: " + e.getMessage());
    }
  }
  
	//Metodo para retornar lista en Archivo JSON
	 public List<Producto> getAllProducts() {
	   return repository.findAll();
	 }

  // Metodo para convertir los datos de MySQL en un archivo CSV..
  /*
  public ByteArrayInputStream load() {
    List<Producto> tutorials = repository.findAll();

    ByteArrayInputStream in = CSVHelper.productsToCSV(tutorials);
    return in;
  }
  */
}
