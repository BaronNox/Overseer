package net.noxumbrarum.sotacmarketer.data;

public class Hold<k, v> {
	private k key;
	private v value;
	
	public Hold<k, v> add(k key, v value) {
		this.key = key;
		this.value = value;
		
		return this;
	}

	public k getKey() {
		return key;
	}

	public v getValue() {
		return value;
	}
}
