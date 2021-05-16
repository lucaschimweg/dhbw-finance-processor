package net.schimweg.financeProcessor.model;

public class DataContext {
    private final DataProvider dataProvider;

    public DataContext(DataProvider dataProvider) {
        this.dataProvider = dataProvider;
    }

    public DataProvider getDataProvider() {
        return dataProvider;
    }
}
