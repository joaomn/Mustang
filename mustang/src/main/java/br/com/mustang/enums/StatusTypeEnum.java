package br.com.mustang.enums;

public enum StatusTypeEnum {

	GOOD("Bom"), SATISFACTORY("Satisfatorio"), BAD("Ruim"), NULL("N/I");
	

	private final String value;

	StatusTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

	

	
    
    
}
