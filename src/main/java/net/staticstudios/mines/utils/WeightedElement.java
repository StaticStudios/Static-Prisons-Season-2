package net.staticstudios.mines.utils;


/**
 * @author Sam (GitHub: <a href="https://github.com/Sammster10">Sam's GitHub</a>)
 * <br>
 * <br> A simple class that is used to hold an object and a weight, used primarily by the <b>WeightedElements</b> class.
 */
public record WeightedElement<T>(T element, double weight) {
}
