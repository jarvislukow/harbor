package com.icfolson.aem.harbor.core.components.content.navigation.bootstrapnavigation.mainautonavigation;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import com.icfolson.aem.harbor.api.constants.bootstrap.Bootstrap;
import com.icfolson.aem.harbor.api.content.page.navigation.NavigablePage;
import com.icfolson.aem.harbor.api.trees.Tree;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class BootstrapPageNavigableRenderableTree implements Tree<NavigablePage> {

    private final Boolean isSticky;

    private final Boolean showBrandLink;

    private final String brandLinkText;

    private final NavigablePage rootPage;

    private final Optional<String> brandLinkImage;

    private final boolean fullWidth;

    private final boolean presentMainNavigationItemInDropDown;

    public BootstrapPageNavigableRenderableTree(Boolean sticky, Boolean showBrandLink, String brandLinkText,
        Optional<String> brandLinkImage, NavigablePage rootPage, boolean fullWidth,
        boolean presentMainNavigationItemInDropDown) {
        this.isSticky = sticky;
        this.showBrandLink = showBrandLink;
        this.brandLinkText = brandLinkText;
        this.rootPage = rootPage;
        this.brandLinkImage = brandLinkImage;
        this.fullWidth = fullWidth;
        this.presentMainNavigationItemInDropDown = presentMainNavigationItemInDropDown;
    }

    public Boolean isSticky() {
        return isSticky;
    }

    public Boolean isShowBrandLink() {
        return showBrandLink;
    }

    public String getBrandLinkText() {
        if (StringUtils.isNotBlank(brandLinkText)) {
            return brandLinkText;
        } else if (rootPage != null) {
            return rootPage.getLink().getTitle();
        } else {
            return "No Brand Text Configured";
        }
    }

    public String getHref() {
        return rootPage == null ? "#" : rootPage.getHref();
    }

    public String getBrandLinkImage() {
        return brandLinkImage.or("");
    }

    public boolean isHasBrandLinkImage() {
        return brandLinkImage.isPresent();
    }

    @Override
    public NavigablePage getRoot() {
        return rootPage;
    }

    @Override
    public boolean isHasRoot() {
        return rootPage != null;
    }

    public boolean isFullWidth() {
        return fullWidth;
    }

    public String getContainerClass() {
        return isFullWidth() ? Bootstrap.CONTAINER_FULL_WIDTH_CLASS : Bootstrap.CONTAINER_CLASS;
    }

    public boolean isPresentMainNavigationItemInDropDown() {
        return presentMainNavigationItemInDropDown;
    }

    public List<String> getNavbarClasses() {
        return Lists.newArrayList("navbar", "navbar-default");
    }

    public List<String> getStickyNavbarClasses() {
        return Lists.newArrayList("navbar", "navbar-default", "navbar-fixed-top");
    }
}
