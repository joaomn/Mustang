package br.com.mustang.services;

import java.io.StringWriter;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.univocity.parsers.csv.CsvWriter;
import com.univocity.parsers.csv.CsvWriterSettings;

@Service
public class CsvUtilsService {
	
	 public <T> String generateCsv(List<T> records) {
	        StringWriter writer = new StringWriter();
	        CsvWriterSettings settings = new CsvWriterSettings();

	        // Defina as configurações desejadas, como delimitador, escape de caracteres, etc.
//	        settings.setHeaders("Data do Evento","Display_ID", "Luminosidade", "sound", "temperature", 
//	        		"temperatureStatus", "soundStatus", "luminosityStatus");
	        
	        List<String> headers = getFieldNames(records.get(0).getClass());
	        settings.setHeaders(headers.toArray(new String[0]));

	        CsvWriter csvWriter = new CsvWriter(writer, settings);

	        // Escreva os cabeçalhos
	        csvWriter.writeHeaders();

	        // Escreva os registros
	        for (T record : records) {
	            Map<String, Object> recordMap = convertToMap(record, settings);
	            csvWriter.writeRow(recordMap.values());
	        }

	        csvWriter.close();

	        return writer.toString();
	    }

	    private Map<String, Object> convertToMap(Object obj, CsvWriterSettings settings) {
	    	Map<String, Object> map = new LinkedHashMap<>(); // Use LinkedHashMap para manter a ordem
	        
	        List<Field> fields = Arrays.asList(obj.getClass().getDeclaredFields());
	        
	        for (String header : settings.getHeaders()) {
	            Optional<Field> matchingField = fields.stream()
	                .filter(field -> field.getName().equals(header))
	                .findFirst();
	            
	            if (matchingField.isPresent()) {
	                Field field = matchingField.get();
	                field.setAccessible(true);
	                
	                try {
	                    Object value = field.get(obj);
	                    if (value != null && field.getType().getName().startsWith("br.com.mustang.entitys")) {
	                        Field idField = value.getClass().getDeclaredField("name"); 
	                        idField.setAccessible(true);
	                        value = idField.get(value);
	                    }
	                    map.put(header, value);
	                } catch (IllegalAccessException | NoSuchFieldException e) {
	                    throw new RuntimeException(e);
	                }
	            } else {
	                map.put(header, null); 
	            }
	        }
	        
	        return map;
	    	}
	    
	    private <T> List<String> getFieldNames(Class<T> clazz) {
	    	return Arrays.stream(clazz.getDeclaredFields())
	    	        .filter(field -> !field.getName().equals("id") && !field.getName().equals("message"))
	    	        .map(Field::getName)
	    	        .collect(Collectors.toList());
	    }

}
