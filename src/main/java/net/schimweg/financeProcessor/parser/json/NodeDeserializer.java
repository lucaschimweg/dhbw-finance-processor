package net.schimweg.financeProcessor.parser.json;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import net.schimweg.financeProcessor.ast.Node;
import net.schimweg.financeProcessor.parser.NodeTypeFactory;

import java.lang.reflect.Type;

public class NodeDeserializer<T extends Node> implements JsonDeserializer<T> {
    private final NodeTypeFactory typeInformation;

    public NodeDeserializer(NodeTypeFactory typeInformation) {
        this.typeInformation = typeInformation;
    }

    @Override
    public T deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        var obj = jsonElement.getAsJsonObject();
        var nodeType = obj.get("type").getAsString();

        var info = typeInformation.getTypeInformation(nodeType);
        if (info == null) {
            throw new JsonParseException("Unknown type: " + nodeType);
        }

        Node node;
        if (info.getConfigType() == null) {
            node = info.getFactory().apply(null);
        } else {
            var config = jsonDeserializationContext.deserialize(jsonElement, info.getConfigType());
            node = info.getFactory().apply(config);
        }

        try {
            return (T) node;
        } catch (ClassCastException e) {
            throw new JsonParseException("Unknown type: " + nodeType);
        }
    }
}
