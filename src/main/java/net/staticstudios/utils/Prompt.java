package net.staticstudios.utils;

import java.util.Objects;
import java.util.function.Consumer;

public class Prompt<U, T> implements Cloneable {
    private final Prompt<U, T> nextNode;
    private final Consumer<Prompt<U, T>> consumer;
    private boolean state = false;
    public T value;
    public U user;

    public void done() {
        this.state = true;
        if (Objects.nonNull(nextNode)) nextNode.accept(user, value);
    }

    public void done(boolean runNext) {
        if (runNext) done();
        else this.state = true;
    }

    public void accept(U user, T value) {
        this.value = value;
        this.user = user;
        if (state) {
            if (nextNode != null) nextNode.accept(user, value);
        } else consumer.accept(this);
    }

    public Prompt(Consumer<Prompt<U, T>> consumer, Prompt<U, T> nextNode) {
        this.nextNode = nextNode;
        this.consumer = consumer;
    }
    private Prompt() {
        this.nextNode = null;
        this.consumer = null;
    }

    @Override
    public Prompt<U, T> clone() {
        return new Prompt<>(consumer, nextNode);
    }
}
