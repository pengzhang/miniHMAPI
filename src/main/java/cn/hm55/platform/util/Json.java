package cn.hm55.platform.util;

import java.io.IOException;
import java.io.StringWriter;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator.Feature;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * Helper functions to handle JsonNode values.
 * 
 * @author zhangpeng
 * 
 */
public class Json {

	private static final ObjectMapper defaultObjectMapper = new ObjectMapper();
	private static volatile ObjectMapper objectMapper = null;

	/**
	 * Get the ObjectMapper used to serialize and deserialize objects to and
	 * from JSON values.
	 * 
	 * This can be set to a custom implementation using Json.setObjectMapper.
	 * 
	 * @return the ObjectMapper currently being used
	 */
	public static ObjectMapper mapper() {
		if (objectMapper == null) {
			return defaultObjectMapper;
		} else {
			return objectMapper;
		}
	}

	private static String generateJson(Object o, boolean prettyPrint, boolean escapeNonASCII) {
		try {
			StringWriter sw = new StringWriter();
			JsonGenerator jgen = new JsonFactory(mapper()).createGenerator(sw);
			if (prettyPrint) {
				jgen.setPrettyPrinter(new DefaultPrettyPrinter());
			}
			if (escapeNonASCII) {
				jgen.enable(Feature.ESCAPE_NON_ASCII);
			}
			mapper().writeValue(jgen, o);
			sw.flush();
			return sw.toString();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Convert an object to JsonNode.
	 * 
	 * @param data
	 *            Value to convert in Json.
	 */
	public static JsonNode toJson(final Object data) {
		try {
			return mapper().valueToTree(data);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public static ObjectNode toObjectNode(final Object data) {
		try {
			return mapper().valueToTree(data);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Convert a JsonNode to a Java value
	 * 
	 * @param json
	 *            Json value to convert.
	 * @param clazz
	 *            Expected Java value type.
	 */
	public static <A> A fromJson(JsonNode json, Class<A> clazz) {
		try {
			return mapper().treeToValue(json, clazz);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Creates a new empty ObjectNode.
	 */
	public static ObjectNode newObject() {
		return mapper().createObjectNode();
	}

	/**
	 * Creates a new empty ArrayNode.
	 */
	public static ArrayNode newArray() {
		return mapper().createArrayNode();
	}

	/**
	 * Convert a JsonNode to its string representation.
	 */
	public static String stringify(JsonNode json) {
		return generateJson(json, false, false);
	}

	/**
	 * Convert a JsonNode to its string representation, escaping non-ascii
	 * characters.
	 */
	public static String asciiStringify(JsonNode json) {
		return generateJson(json, false, true);
	}

	/**
	 * Convert a JsonNode to its string representation.
	 */
	public static String prettyPrint(JsonNode json) {
		return generateJson(json, true, false);
	}

	/**
	 * Parse a String representing a json, and return it as a JsonNode.
	 */
	public static JsonNode parse(String src) {
		try {
			return mapper().readTree(src);
		} catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	/**
	 * Parse a InputStream representing a json, and return it as a JsonNode.
	 */
	public static JsonNode parse(java.io.InputStream src) {
		try {
			return mapper().readTree(src);
		} catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	/**
	 * Parse a byte array representing a json, and return it as a JsonNode.
	 */
	public static JsonNode parse(byte[] src) {
		try {
			return mapper().readTree(src);
		} catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	/**
	 * Inject the object mapper to use.
	 * 
	 * This is intended to be used when Play starts up. By default, Play will
	 * inject its own object mapper here, but this mapper can be overridden
	 * either by a custom plugin or from Global.onStart.
	 */
	public static void setObjectMapper(ObjectMapper mapper) {
		objectMapper = mapper;
	}

}
