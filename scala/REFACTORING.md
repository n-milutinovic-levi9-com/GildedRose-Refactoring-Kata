# Refactoring journey

This is a diary of refactoring steps taken on this refactoring kata. Although full
history can be derived from Git history, it is still useful to have an overview of
intentions and steps taken.

## Initial refactoring

In this phase, the primary refactoring and simplification was done, reducing the
complexity of the control logic. This phase followed the YouTube video provided by
the author. Turns out the logic for `Sulfuras, Hand of Ragnaros` does nothing on
the state.

With this in place, as per YT video, we have changed the public API of the problem
we are working on. We should restore that before merging.

### Using Item processor

Item Processor is a component operating directly on original Items. Since Items are
semi-mutable (name is immutable), this choice makes sense.

### Using internal item classes with polymorphism

With proper encapsulation and polymorphism we can introduce immutability and have a
proper code decomposition. The drawback is that we have to convert to and from the
public API, but that seems OK.