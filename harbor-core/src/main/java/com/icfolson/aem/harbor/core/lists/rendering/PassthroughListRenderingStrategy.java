package com.icfolson.aem.harbor.core.lists.rendering;

import com.icfolson.aem.harbor.api.lists.rendering.ListRenderingStrategy;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * Produces a list consisting of the same items as were provided.
 *
 * @param <T>
 */
public class PassthroughListRenderingStrategy <T> implements ListRenderingStrategy<T, List<T>> {

    @Override
    public List<T> toRenderableList(Iterable<T> itemIterable) {
        return Lists.newArrayList(itemIterable);
    }

}
