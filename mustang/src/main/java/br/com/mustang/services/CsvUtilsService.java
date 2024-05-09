package br.com.mustang.services;

import java.io.StringWriter;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
//	        settings.setHeaders("date", "luminosity", "sound", "temperature", 
//	        		"temperatureStatus", "soundStatus", "luminosityStatus");
	        
	        List<String> headers = getFieldNames(records.get(0).getClass());
	        settings.setHeaders(headers.toArray(new String[0]));

	        CsvWriter csvWriter = new CsvWriter(writer, settings);

	        // Escreva os cabeçalhos
	        csvWriter.writeHeaders();

	        // Escreva os registros
	        for (T record : records) {
	            Map<String, Object> recordMap = convertToMap(record);
	            csvWriter.writeRow(recordMap.values());
	        }

	        csvWriter.close();

	        return writer.toString();
	    }

	    private Map<String, Object> convertToMap(Object obj) {
	    	 Map<String, Object> map = new HashMap<>();
	    	    for (Field field : obj.getClass().getDeclaredFields()) {
	    	        try {
	    	            field.setAccessible(true); // Permitir acesso a campos privados
	    	            Object value = field.get(obj);
	    	            if (value != null && field.getType().getName().startsWith("br.com.mustang.entitys")) {
	    	                // Se o valor for uma entidade, extrair o ID
	    	                Field idField = value.getClass().getDeclaredField("id"); // Supondo que o ID seja chamado "id"
	    	                idField.setAccessible(true);
	    	                value = idField.get(value);
	    	            }
	    	            map.put(field.getName(), value);
	    	        } catch (IllegalAccessException | NoSuchFieldException e) {
	    	            throw new RuntimeException(e);
	    	        }
	    	    }
	    	    return map;
	    	}
	    
	    private <T> List<String> getFieldNames(Class<T> clazz) {
	        return Arrays.stream(clazz.getDeclaredFields())
	                .map(Field::getName)
	                .collect(Collectors.toList());
	    }

}
