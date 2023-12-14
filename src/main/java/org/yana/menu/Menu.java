package org.yana.menu;

import org.yana.persistance.Item;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public interface Menu {
    default Map<Integer, Item> idToItemMap(List<Item> items) {
        return IntStream.range(1, items.size() + 1)
                .boxed()
                .collect(Collectors.toMap(Function.identity(), i -> items.get(i - 1)));

    }
}
