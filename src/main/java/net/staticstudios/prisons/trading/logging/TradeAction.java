package net.staticstudios.prisons.trading.logging;

public enum TradeAction {
    REQUEST("Request Trade"),
    ACCEPT("Accept Trade"),
    BEGIN("Begin Trade"),
    EXIT("Exit Trade"),
    INV_OPENED("Inventory Opened"),
    ADD_ITEM("Add Item"),
    REMOVE_ITEM("Remove Item"),

    CONFIRM("Confirm Trade"),
    UN_CONFIRM("Un-confirm Trade"),
    CANCEL("Cancel Trade"),
    COMPLETE("Trade Completed"),
    FINISH("Finish Trade");

    private final String name;

    TradeAction(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
