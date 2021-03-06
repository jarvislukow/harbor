package com.icfolson.aem.harbor.core.components.content.navigation.bootstrapnavigation.secondarynavigation;

import com.citytechinc.cq.component.annotations.Component;
import com.icfolson.aem.library.core.constants.ComponentConstants;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

@Component(
    value = "Baked-In Bootstrap Main Manual Navigation",
    group = ComponentConstants.GROUP_HIDDEN,
    path = "content/navigation",
    resourceSuperType = BootstrapSecondaryNavigation.RESOURCE_TYPE,
    disableTargeting = true,
    actions = { "text: Secondary Navigation", "-", "edit" })
@Model(adaptables = Resource.class)
public class BakedinBootstrapSecondaryNavigation extends BootstrapSecondaryNavigation {

    public static final String RESOURCE_TYPE = "harbor/components/content/navigation/bakedinbootstrapsecondarynavigation";

}
