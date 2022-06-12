package net.staticstudios.prisons.commands.test;

import java.util.Objects;
import java.util.function.Consumer;

public class OrderedStuff<T> {
    private final OrderedStuff<T> nextNode;
    private final Consumer<OrderedStuff<T>> callback;
    public boolean state = false;
    public T value;

    public void done() {
        this.state = true;
        if (Objects.nonNull(nextNode)) nextNode.accept(value);
    }

    public void done(boolean runNext) {
        if (runNext) done();
        else this.state = true;
    }

    public void accept(T value) {
        this.value = value;
        if (state) {
            if (nextNode != null) nextNode.accept(value);
        } else callback.accept(this);
    }

    public OrderedStuff(Consumer<OrderedStuff<T>> consumer, OrderedStuff<T> nextNode) {
        this.nextNode = nextNode;
        this.callback = consumer;
    }
}