package net.schimweg.financeProcessor.http;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import net.schimweg.financeProcessor.ast.AstRoot;
import net.schimweg.financeProcessor.execution.*;
import net.schimweg.financeProcessor.model.*;
import net.schimweg.financeProcessor.parser.Parser;
import net.schimweg.financeProcessor.plugin.Encoder;
import net.schimweg.financeProcessor.plugin.PluginManager;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;

public class Server implements HttpHandler {
    private final HttpServer server;
    private final PluginManager pluginManager;

    public Server(PluginManager manager) throws IOException {
        this.pluginManager = manager;
        server = HttpServer.create(new InetSocketAddress("localhost", 8080), 256);
        server.createContext("/", this);
    }

    public void start() {
        server.start();
    }

    @Override
    public void handle(HttpExchange exchange) {
        try {
            String contentType = exchange.getRequestHeaders().getFirst("Content-Type");
            if (contentType == null) {
                sendError("No content type specified", 400, exchange);
                return;
            }

            Parser parser = pluginManager.getParserFor(contentType);
            if (parser == null) {
                sendError("Unsupported content type", 400, exchange);
                return;
            }

            AstRoot root;
            try {
                root = parser.parseTree(exchange.getRequestBody());
            } catch (Exception e) {
                sendError("Error parsing AST", 400, exchange);
                return;
            }

            Encoder encoder;
            String accept = exchange.getRequestHeaders().getFirst("Accept");
            if (accept == null) {
                encoder = pluginManager.getDefaultEncoder();
            } else {
                encoder = pluginManager.getEncoderFor(accept);
            }

            if (encoder == null) {
                sendError("Cold not find matching encoder", 400, exchange);
                return;
            }

            // add data sources

            Result res;
            try {

                var data = Arrays.asList(
                        new Transaction(new Amount(2500, Currency.EUR), "Income", 1, TransactionDirection.INCOMING),
                        new Transaction(new Amount(3000, Currency.EUR), "Spending", 1, TransactionDirection.OUTGOING));

                HashMap<String, DataProvider> providers = new HashMap<>();
                providers.put("data", () -> new ListTransactionSet(data));

                var dataContext = new DataContext(providers);
                Executor ex = new Executor(dataContext);

                res = ex.execute(root);
            } catch (EvaluationException e) {
                e.printStackTrace();
                sendError("Error during execution: " + e.getMessage(), 500, exchange);
                return;
            }

            if (res == null) {
                sendError("Execution yielded no result", 500, exchange);
                return;
            }

            MaterializedResult materializedResult;
            try {
                Materializer m = new Materializer();
                materializedResult = m.materialize(res);
            } catch (EvaluationException e) {
                e.printStackTrace();
                sendError("Error during result materialization: " + e.getMessage(), 500, exchange);
                return;
            }

            ByteArrayOutputStream memoryStream = new ByteArrayOutputStream();
            String encodedContentType;
            try {
                encodedContentType = encoder.encode(materializedResult, memoryStream);
            } catch (Exception e) {
                sendError("Error during response encoding", 500, exchange);
                return;
            }

            exchange.getResponseHeaders().set("Content-Type", encodedContentType);
            try {
                exchange.sendResponseHeaders(200, 0);
                exchange.getResponseBody().write(memoryStream.toByteArray());
                exchange.getResponseBody().close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendError(String error, int responseCode, HttpExchange exchange) {
        System.out.println("Handling error " + error);
        try {
            exchange.getResponseHeaders().set("Content-Type", "text/plain");
            exchange.sendResponseHeaders(responseCode, 0);
            exchange.getResponseBody().write(error.getBytes(StandardCharsets.UTF_8));
            exchange.getResponseBody().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
