package com.lottewellfood.sfa.common.customs;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class StringDeserializer extends JsonDeserializer<String> {
	@Override
	public String deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String value = p.getValueAsString();
        if (value != null) {
            value = value.trim();
        }
        return value;
    }
}