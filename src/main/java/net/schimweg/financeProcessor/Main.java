package net.schimweg.financeProcessor;

import com.google.gson.Gson;
import net.schimweg.financeProcessor.ast.*;
import net.schimweg.financeProcessor.execution.Executor;
import net.schimweg.financeProcessor.model.*;
import net.schimweg.financeProcessor.parser.NodeTypeFactory;
import net.schimweg.financeProcessor.parser.json.JsonParser;

import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static void main2(String[] args) {
        var data = Arrays.asList(
                new Transaction(new Amount(2500, Currency.EUR), "Income", 1, TransactionDirection.INCOMING),
                new Transaction(new Amount(3000, Currency.EUR), "Spending", 1, TransactionDirection.OUTGOING));

        var dataContext = new DataContext(() -> new ListTransactionSet(data));

        var tree = new SumNode(new UnionNode(new TransactionSetNode[] {
                new FilterNode(new AllTransactionsNode()),
                new AllTransactionsNode()
        }), true);

        System.out.println(tree.execute(dataContext));
    }

    public static void main(String[] args) {
        System.out.println("working");
        var factory = new NodeTypeFactory();
        factory.addType("sum", SumNode.SumNodeConfig.class, o -> new SumNode((SumNode.SumNodeConfig) o));
        factory.addType("union", UnionNode.UnionNodeConfig.class, o -> new UnionNode((UnionNode.UnionNodeConfig) o));
        factory.addType("filter", FilterNode.FilterNodeConfig.class, o -> new FilterNode((FilterNode.FilterNodeConfig) o));
        factory.addType("all_transactions", null, o -> new AllTransactionsNode());
        factory.addType("count", CountNode.CountNodeConfig.class, o -> new CountNode((CountNode.CountNodeConfig) o));

        JsonParser parser = new JsonParser(factory);

        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        var stream = classloader.getResourceAsStream("sample_req.json");
        if (stream == null) {
            System.out.println("null");
            return;
        }

        var root = parser.parseTree(new InputStreamReader(stream));

        var dataStream = classloader.getResourceAsStream("data.json");
        if (dataStream == null) {
            System.out.println("null");
            return;
        }

        var data = Arrays.asList(new Gson().fromJson(new InputStreamReader(dataStream), Transaction[].class));
        var dataContext = new DataContext(() -> new ListTransactionSet(data));

        var dataStream2 = classloader.getResourceAsStream("big_data.json");
        if (dataStream2 == null) {
            System.out.println("null");
            return;
        }

        var data2 = Arrays.asList(new Gson().fromJson(new InputStreamReader(dataStream2), Transaction[].class));
        var dataContext2 = new DataContext(() -> new ListTransactionSet(data2));

        Executor ex = new Executor();
        ex.addContext("data", dataContext);
        ex.addContext("bigdata", dataContext2);

        var result = ex.Execute(root);

        System.out.println(result);

    }
}
