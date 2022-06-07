package net.staticstudios.utils;

import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class WeightedElements<T> {

    @NotNull
    public static <T> T getRandom(List<WeightedElement<T>> weightedElements) {
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

    private final List<WeightedElement<T>> elements = new ArrayList<WeightedElement<T>>();

    public List<WeightedElement<T>> getElements() {
        return elements;
    }
    public WeightedElement<T> getElement(int index) {
        return elements.get(index);
    }
    public double getChance(int index) {
        return (elements.get(index).getWeight() / getTotalWeight()) * 100;
    }
    public double getChance(Object o) {
        for (WeightedElement<T> element : elements)
            if (element.element.equals(o)) return (element.getWeight() / getTotalWeight()) * 100;
        return 0;
    }
    public double getTotalWeight() {
        double totalWeight = 0;
        for (WeightedElement<T> element : elements) totalWeight += element.weight;
        return totalWeight;
    }
    public List<T> getInternalList() {
        List<T> elements = new ArrayList<>();
        for (WeightedElement<T> weightedElement : this.elements) elements.add(weightedElement.element);
        return elements;
    }

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
        return getRandom(elements);
    }
}
