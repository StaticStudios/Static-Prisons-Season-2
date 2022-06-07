package net.staticstudios.utils;

public class WeightedElement<T> {
    T element;
    double weight;
    public WeightedElement(T element, double weight) {
        this.weight = weight;
        this.element = element;
    }

    public T getElement() {
        return element;
    }
    public double getWeight() {
        return weight;
    }
}
