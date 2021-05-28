package net.schimweg.financeProcessor.plugins.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.schimweg.financeProcessor.ast.*;
import net.schimweg.financeProcessor.plugin.NodeTypeFactory;
import net.schimweg.financeProcessor.plugin.Parser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class JsonParser implements Parser {
    NodeTypeFactory typeInformation;
    Gson gson;

    public JsonParser(NodeTypeFactory typeInformation) {
        this.typeInformation = typeInformation;
        try {
            GsonBuilder builder = new GsonBuilder();
            builder.registerTypeAdapter(TransactionNode.class, new NodeDeserializer<>(typeInformation));
            builder.registerTypeAdapter(TransactionSetNode.class, new NodeDeserializer<>(typeInformation));
            builder.registerTypeAdapter(AmountNode.class, new NodeDeserializer<>(typeInformation));
            builder.registerTypeAdapter(Node.class, new NodeDeserializer<>(typeInformation));

            gson = builder.create();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public AstRoot parseTree(InputStream inputStream) throws IOException {
        try (Reader reader = new InputStreamReader(inputStream)) {
            return gson.fromJson(reader, AstRoot.class);
        }
    }
}
