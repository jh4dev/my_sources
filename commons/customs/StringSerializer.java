package com.lottewellfood.sfa.common.customs;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdScalarSerializer;

public class StringSerializer extends StdScalarSerializer<Object> {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public StringSerializer() {
        super(Object.class);
    }

    @Override
    public void serialize(Object value, JsonGenerator gen, SerializerProvider provider) throws IOException {
    	System.out.println(value.toString());
        gen.writeString(value.toString());
    }
}