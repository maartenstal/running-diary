
package com.la.runners.parser;

import java.io.InputStream;
import java.util.Iterator;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

import android.content.ContentValues;
import android.text.TextUtils;

import com.la.runners.R;
import com.la.runners.exception.ParserException;

/**
 * Abstract class for all the json parsers that need to iterate through a list.
 * 
 * @author luigi.agosti
 */
public abstract class AbstractJsonParserIterator implements JsonParserIterator {

    private static final String COMMA = ",";

    private Iterator<JsonNode> nodes;

    private JsonNode rootNode;

    private JsonNode currentNode;
    
    private int position = 0;

    public AbstractJsonParserIterator(InputStream stream, String root) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            rootNode = mapper.readValue(stream, JsonNode.class);
            JsonNode array = null;
            if(rootNode == null) {
            	array = rootNode.get(root);
            	nodes = array.getElements();
            } else {
            	nodes = rootNode.getElements();
            }
        } catch (Throwable e) {
            throw new ParserException(R.string.error_3, e.getMessage());
        }
    }

    public AbstractJsonParserIterator(String source, String root) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            rootNode = mapper.readValue(source, JsonNode.class);
            JsonNode array = rootNode.get(root);
            nodes = array.getElements();
        } catch (Throwable e) {
            throw new ParserException(R.string.error_3, e.getMessage());
        }
    }

    @Override
    public boolean hasNext() {
        return nodes.hasNext();
    }

    @Override
    public JsonNode getCurrentNode() {
        return currentNode;
    }
    
    @Override
    public int getPosition() {
        return position;
    }
    
    protected JsonNode nextNode() {
        currentNode = nodes.next();
        position++;
        return currentNode;
    }

    public static final void setStringValueForColumns(String columns, String jsonAttribute,
            JsonNode node, ContentValues cv) {
        JsonNode property = node.get(jsonAttribute);
        if (property != null) {
            cv.put(columns, property.getTextValue());
        }
    }

    public static final void setLongValueForColumns(String columns, String jsonAttribute,
            JsonNode node, ContentValues cv) {
        JsonNode property = node.get(jsonAttribute);
        if (property != null) {
            cv.put(columns, property.getLongValue());
        }
    }

    public static final void setIntValueForColumns(String columns, String jsonAttribute,
            JsonNode node, ContentValues cv) {
        JsonNode property = node.get(jsonAttribute);
        if (property != null) {
            cv.put(columns, property.getIntValue());
        }
    }
    
    public static final void setStringValueAsCommaSeparatedForColumn(String column,
            String jsonAttribute, JsonNode node, ContentValues cv) {
        // TODO change all this just first thing that came to my mind
        String size = "";
        JsonNode property = node.get(jsonAttribute);
        if (property != null) {
            Iterator<JsonNode> nodes = property.getElements();
            JsonNode sizeNode;
            while (nodes.hasNext()) {
                sizeNode = nodes.next();
                size = size + sizeNode.getValueAsText() + COMMA;
            }
            if(!TextUtils.isEmpty(size)) {
                size = size.substring(0, size.length()-1);
            }
        }
        if (!TextUtils.isEmpty(size)) {
            cv.put(column, size);
        }
    }
    
    public static final void setStringValueWithSeparatorFromArrayObject(String column,
            String jsonAttribute, String jsonArrayAttribute, JsonNode node, ContentValues cv, String separator) {
        String size = "";
        JsonNode attribute = node.get(jsonAttribute);
        if (attribute != null) {
            Iterator<JsonNode> nodes = attribute.getElements();
            JsonNode subNode;
            while (nodes.hasNext()) {
                subNode = nodes.next();
                JsonNode subAttribute = subNode.get(jsonArrayAttribute);
                if(subAttribute != null) {
                    size = size + subAttribute.getValueAsText() + separator;
                }
            }
            if(!TextUtils.isEmpty(size)) {
                size = size.substring(0, size.length()-separator.length());
            }
        }
        if (!TextUtils.isEmpty(size)) {
            cv.put(column, size);
        }
    }

}
