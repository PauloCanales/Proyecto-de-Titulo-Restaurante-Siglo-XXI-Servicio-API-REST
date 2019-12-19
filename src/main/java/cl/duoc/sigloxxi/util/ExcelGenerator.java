package cl.duoc.sigloxxi.util;
 
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;

import cl.duoc.sigloxxi.repository.UsuarioRepository;
 
public class ExcelGenerator {
	
	@Autowired
	static
	UsuarioRepository usuarioRepository;	
  
  public static ByteArrayInputStream presupuestosToExcel() throws IOException {
    
    try(
        Workbook workbook = new HSSFWorkbook();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
    ){
      CreationHelper createHelper = workbook.getCreationHelper();
   
      
		List<Sheet> listaHojas = new ArrayList<>();      
		      
		Font headerFont = workbook.createFont();
		headerFont.setBold(true);
		headerFont.setColor(IndexedColors.BLUE.getIndex());
		
		CellStyle headerCellStyle = workbook.createCellStyle();
		headerCellStyle.setFont(headerFont);



		Sheet sheetGasto = workbook.createSheet("Gastos");
		
	      Row rowGastos = sheetGasto.createRow(0);
		   
	      // Header
	      String[] COLUMNsGASTO = {"Id", "Dimension", "Categoria", "Subcategoria", "Fecha", 
	    		  "Usuario", "Provision", "Item", "Tipo Viaje", "Barco", "No Barco", 
	    		  "Nombre", "Proveedor", "Precio Unitario","Cantidad", "Impuesto", 
	    		  "Forma De Pago", "Moneda", "Observacion"};
	      for (int col = 0; col < COLUMNsGASTO.length; col++) {
	        Cell cell = rowGastos.createCell(col);
	        cell.setCellValue(COLUMNsGASTO[col]);
	        cell.setCellStyle(headerCellStyle);
	      }
	   
	   
	      int rowIdxGasto = 1;
	      
		listaHojas.add(sheetGasto);
		
		
		
		Sheet sheetProvision = workbook.createSheet("Provisiones");
		
	      Row rowProvisiones = sheetProvision.createRow(0);
		   
	      // Header
	      String[] COLUMNsPROVISION = {"Id", "Nombre"};
	      for (int col = 0; col < COLUMNsPROVISION.length; col++) {
	        Cell cell = rowProvisiones.createCell(col);
	        cell.setCellValue(COLUMNsPROVISION[col]);
	        cell.setCellStyle(headerCellStyle);
	      }
	   
	   
	      int rowIdxProvision = 1;
	      
		listaHojas.add(sheetGasto);
		
		
      
      workbook.write(out);
      return new ByteArrayInputStream(out.toByteArray());
    }
  }
  
  
}