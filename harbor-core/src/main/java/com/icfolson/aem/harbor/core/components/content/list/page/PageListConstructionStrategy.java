package com.icfolson.aem.harbor.core.components.content.list.page;

import com.citytechinc.cq.component.annotations.DialogField;
import com.citytechinc.cq.component.annotations.widgets.DialogFieldSet;
import com.day.cq.search.result.Hit;
import com.google.common.collect.Lists;
import com.icfolson.aem.harbor.api.lists.construction.search.ConstructionPredicate;
import com.icfolson.aem.harbor.core.lists.construction.nodesearch.AbstractNodeSearchConstructionStrategy;
import com.icfolson.aem.harbor.core.lists.construction.nodesearch.predicates.nodetype.PageNodeTypeConstructionPredicate;
import com.icfolson.aem.harbor.core.lists.construction.nodesearch.predicates.path.PathConstructionPredicate;
import com.icfolson.aem.harbor.core.lists.construction.nodesearch.predicates.queryparameters.QueryParameters;
import com.icfolson.aem.harbor.core.lists.construction.nodesearch.predicates.tags.PageTagsConstructionPredicate;
import com.icfolson.aem.library.api.page.PageDecorator;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import javax.jcr.RepositoryException;
import java.util.List;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class PageListConstructionStrategy extends AbstractNodeSearchConstructionStrategy<PageDecorator> {

    private static final Logger LOG = LoggerFactory.getLogger(PageListConstructionStrategy.class);

    @DialogField
    @DialogFieldSet(title = "Path", namePrefix = "./pathpredicate/", collapsible = true, collapsed = true)
    @Inject
    @Named("pathpredicate")
    private PathConstructionPredicate pathConstructionPredicate;

    @DialogField
    @DialogFieldSet(title = "Tags", namePrefix = "./tagspredicate/", collapsible = true, collapsed = true)
    @Inject
    @Named("tagspredicate")
    private PageTagsConstructionPredicate tagsConstructionPredicate;

    @DialogField
    @DialogFieldSet(title = "Query Parameters", namePrefix = "./queryparameters/", collapsible = true, collapsed = true)
    @Inject
    @Named("queryparameters")
    private QueryParameters queryParameters;

    private List<ConstructionPredicate> constructionPredicates;

    protected PathConstructionPredicate getPathConstructionPredicate() {
        return pathConstructionPredicate;
    }

    protected PageTagsConstructionPredicate getTagsConstructionPredicate() {
        return tagsConstructionPredicate;
    }

    protected PageNodeTypeConstructionPredicate getNodeTypeConstructionPredicate() {
        return getResource().adaptTo(PageNodeTypeConstructionPredicate.class);
    }

    @Override
    protected QueryParameters getQueryParameters() {
        return queryParameters;
    }

    @Override
    protected PageDecorator transformHit(Hit hit) {
        PageDecorator pageDecorator = null;

        try {
            pageDecorator = hit.getResource().adaptTo(PageDecorator.class);
        } catch (RepositoryException e) {
            LOG.error("Failed at creating PageDecorator from a hit.", e);
        }

        return pageDecorator;
    }

    @Override
    protected List<ConstructionPredicate> getConstructionPredicates() {
        if (constructionPredicates == null) {
            constructionPredicates = Lists.newArrayList();

            constructionPredicates.add(getNodeTypeConstructionPredicate());

            if (getPathConstructionPredicate() != null) {
                constructionPredicates.add(getPathConstructionPredicate());
            }

            if (getTagsConstructionPredicate() != null) {
                constructionPredicates.add(getTagsConstructionPredicate());
            }
        }

        return constructionPredicates;
    }

    @Override
    protected boolean isReadyToQuery() {
        return getPathConstructionPredicate() != null && getPathConstructionPredicate().getSearchPath().isPresent();
    }
}
