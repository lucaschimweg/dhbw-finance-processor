package net.schimweg.financeProcessor.plugins.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.schimweg.financeProcessor.ast.*;
import net.schimweg.financeProcessor.parser.Parser;
import net.schimweg.financeProcessor.parser.NodeTypeFactory;

import java.io.Reader;

public class JsonParser implements Parser {
    NodeTypeFactory typeInformation;
    Gson gson;

    public JsonParser(NodeTypeFactory typeInformation) {
        this.typeInformation = typeInformation;
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(TransactionNode.class, new NodeDeserializer<>(typeInformation));
        builder.registerTypeAdapter(TransactionSetNode.class, new NodeDeserializer<>(typeInformation));
        builder.registerTypeAdapter(AmountNode.class, new NodeDeserializer<>(typeInformation));
        builder.registerTypeAdapter(Node.class, new NodeDeserializer<>(typeInformation));

        gson = builder.create();
    }

    @Override
    public AstRoot parseTree(Reader inputStream) {
        return gson.fromJson(inputStream, AstRoot.class);
    }
}
