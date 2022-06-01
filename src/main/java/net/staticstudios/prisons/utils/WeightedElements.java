package net.staticstudios.prisons.utils;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class WeightedElements<T> {

    @SafeVarargs
    @NotNull
    public static <T> T getRandom(WeightedElement<T>... weightedElements) {
        List<T> elements = new ArrayList<>();
        List<Double> weights = new ArrayList<>();
        for (WeightedElement<T> weightedElement : weightedElements) elements.add(weightedElement.element);
        for (WeightedElement<T> weightedElement : weightedElements) weights.add(weightedElement.weight);
        double totalWeight = 0;
        for (double weight : weights) totalWeight += weight;
        double random = Math.random() * totalWeight;
        double currentWeight = 0;
        for (int i = 0; i < elements.size(); i++) {
            currentWeight += weights.get(i);
            if (random < currentWeight) return elements.get(i);
        }
        return elements.get(0);
    }

    public static WeightedElement of(Object o, double weight) {
        return new WeightedElement(o, weight);
    }

    private final List<WeightedElement<T>> elements = new ArrayList<>();

    public WeightedElements<T> add(WeightedElement<T> e) {
        elements.add(e);
        return this;
    }

    public WeightedElements<T> add(T o, double weight) {
        return add(of(o, weight));
    }

    public WeightedElements<T> remove(WeightedElement e) {
        elements.remove(e);
        return this;
    }

    public WeightedElements<T> remove(T o, double weight) {
        return remove(of(o, weight));
    }

    public WeightedElements<T> set(WeightedElement<T>... e) {
        elements.clear();
        elements.addAll(List.of(e));
        return this;
    }

    public WeightedElements<T> clear() {
        elements.clear();
        return this;
    }

    public T getRandom() {
        return getRandom(elements.toArray(new WeightedElement[0]));
    }
}
