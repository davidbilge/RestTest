package de.davidbilge.resttest.webmvc;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

import org.codehaus.jackson.JsonEncoding;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.Module;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.TypeFactory;
import org.codehaus.jackson.type.JavaType;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

public class CustomJacksonConverter extends AbstractHttpMessageConverter<Object> {

	ObjectMapper objectMapper = new ObjectMapper();

	public CustomJacksonConverter(List<Module> modules) {
		super(MediaType.APPLICATION_JSON, MediaType.TEXT_HTML);

		for (Module module : modules) {
			objectMapper.registerModule(module);
		}
	}

	@Override
	protected boolean supports(Class<?> clazz) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean canRead(Class<?> clazz, MediaType mediaType) {
		JavaType javaType = getJavaType(clazz);
		return objectMapper.canDeserialize(javaType) && canRead(mediaType);
	}

	@Override
	public boolean canWrite(Class<?> clazz, MediaType mediaType) {
		return objectMapper.canSerialize(clazz) && canWrite(mediaType);
	}

	protected JavaType getJavaType(Class<?> clazz) {
		return TypeFactory.defaultInstance().constructType(clazz);
	}

	@Override
	protected Object readInternal(Class<? extends Object> clazz, HttpInputMessage inputMessage) throws IOException,
			HttpMessageNotReadableException {

		JavaType javaType = getJavaType(clazz);
		try {
			return objectMapper.readValue(inputMessage.getBody(), javaType);
		} catch (JsonParseException e) {
			throw new HttpMessageNotReadableException("Unable to deserialize JSON", e);
		}
	}

	@Override
	protected void writeInternal(Object t, HttpOutputMessage outputMessage) throws IOException,
			HttpMessageNotWritableException {

		JsonEncoding encoding = getEncoding(outputMessage.getHeaders().getContentType());
		JsonGenerator jsonGenerator = objectMapper.getJsonFactory().createJsonGenerator(outputMessage.getBody(),
				encoding);
		try {
			jsonGenerator.useDefaultPrettyPrinter();
			objectMapper.writeValue(jsonGenerator, t);
		} catch (JsonGenerationException ex) {
			throw new HttpMessageNotWritableException("Unable to serialize to JSON", ex);
		}
	}

	private JsonEncoding getEncoding(MediaType contentType) {
		if (contentType != null && contentType.getCharSet() != null) {
			Charset charset = contentType.getCharSet();

			for (JsonEncoding encoding : JsonEncoding.values()) {
				if (charset.name().equals(encoding.getJavaName())) {
					return encoding;
				}
			}
		}

		return JsonEncoding.UTF8;
	}

}
