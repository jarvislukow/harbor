package com.icfolson.aem.harbor.core.lists.construction.nodesearch.predicates.nodetype;

import com.google.common.base.Optional;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

@Model(adaptables = Resource.class)
public class PageNodeTypeConstructionPredicate extends NodeTypeConstructionPredicate {

    private static final Optional<String> NODE_TYPE_OPTIONAL = Optional.of("cq:Page");

    public Optional<String> getNodeType() {
        return NODE_TYPE_OPTIONAL;
    }
}