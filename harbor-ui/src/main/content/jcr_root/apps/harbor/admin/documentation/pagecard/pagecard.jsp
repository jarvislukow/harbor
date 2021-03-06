<%--
  ADOBE CONFIDENTIAL

  Copyright 2014 Adobe Systems Incorporated
  All Rights Reserved.

  NOTICE:  All information contained herein is, and remains
  the property of Adobe Systems Incorporated and its suppliers,
  if any.  The intellectual and technical concepts contained
  herein are proprietary to Adobe Systems Incorporated and its
  suppliers and may be covered by U.S. and Foreign Patents,
  patents in process, and are protected by trade secret or copyright law.
  Dissemination of this information or reproduction of this material
  is strictly forbidden unless prior written permission is obtained
  from Adobe Systems Incorporated.
--%><%
%><%@page session="false"%><%
%><%@page import="com.day.cq.i18n.I18n,
                  com.day.cq.wcm.api.Page,
                  com.adobe.granite.ui.components.AttrBuilder,
                  com.adobe.granite.ui.components.ComponentHelper,
                  com.adobe.granite.ui.components.Tag,
                  org.apache.jackrabbit.util.Text,
                  org.apache.sling.api.resource.Resource,
                  org.apache.sling.api.resource.ResourceUtil,
                  org.apache.sling.api.resource.ValueMap,
                  javax.jcr.Node,
                  javax.jcr.Session,
                  java.util.Calendar" %><%
%><%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><%
%><%@taglib prefix="cq" uri="http://www.day.com/taglibs/cq/1.0" %><%
%><cq:defineObjects /><%

    ComponentHelper cmp = new ComponentHelper(pageContext);
    I18n i18n = cmp.getI18n();

    String parentPath = slingRequest.getRequestPathInfo().getSuffix();
    if (parentPath == null) {
        parentPath = "/content";
    }

    Page cqPage = resource.adaptTo(Page.class);
    int sortWeight = 0;

    String title;
    if (cqPage != null) {
        title = cqPage.getTitle();

        if (cqPage.isHideInNav()) {
            sortWeight += -5;
        }
    } else {
        // if it is not a page it is a folder
        title = getFolderTitle(resource);
    }

    Tag tag = cmp.consumeTag();
    AttrBuilder attrs = tag.getAttrs();

    attrs.add("itemscope", "itemscope");
    attrs.add("data-path", resource.getPath()); // for compatibility
    attrs.add("data-resourcetype", resource.getResourceType());
    attrs.add("data-timeline", true);
    attrs.add("data-item-title", title);
    attrs.add("data-gridlayout-sortkey", sortWeight);
    attrs.add("data-livecopy", true);

    if (cqPage != null) {
        attrs.addClass("card-page");
        attrs.addClass(cqPage.listChildren().hasNext() ? "stack" : null);
        attrs.add("data-item-type", "page");
        pageContext.setAttribute("movable", true);
    } else {
        attrs.addClass("card-directory");
        attrs.add("data-item-type", "directory");
        // check if this node's parent supports orderable children
        Session session = resourceResolver.adaptTo(Session.class);
        if (parentPath != null && session.nodeExists(parentPath)) {
            Node node = session.getNode(parentPath);
            pageContext.setAttribute("movable", node.getPrimaryNodeType().hasOrderableChildNodes());
        }
    }

%><article <%= attrs.build() %>>
    <a href="<%= xssAPI.getValidHref(resource.getPath() + ".html") %>"  onclick="window.location.href='<%= xssAPI.getValidHref(resource.getPath() + ".html") %>'" x-cq-linkchecker="skip">
        <span class="image"><img itemprop="thumbnail" width="192" src="<%= xssAPI.getValidHref(request.getContextPath() + getThumbnailUrl(cqPage)) %>" alt=""></span>
        <div class="label">
            <div class="main"><h4 class="foundation-collection-item-title" itemprop="title"><%= xssAPI.encodeForHTML(title) %></h4>
            </div>
            <div class="info">
                <p class="modified"><b><%= cqPage.getProperties().get("jcr:subtitle")==null?"":cqPage.getProperties().get("jcr:subtitle") %> </b></p>
                <br/>
                <p class="modified"> <%= cqPage.getProperties().get("jcr:description")==null?"":cqPage.getProperties().get("jcr:description") %> </p>
            </div>
        </div>
    </a>
</article><%!


    private String getThumbnailUrl(Page page) {
        String ck = "";

        ValueMap metadata = page.getProperties("image/file/jcr:content");
        if (metadata != null) {
            Calendar cal = metadata.get("jcr:lastModified", Calendar.class);
            if (cal != null) {
                ck = "" + (cal.getTimeInMillis() / 1000);
            }
        }

        return Text.escapePath(page.getPath()) + ".thumb.319.319.png?ck=" + ck;
    }

    private String getFolderTitle(Resource folder) {
        ValueMap vm = ResourceUtil.getValueMap(folder);
        return vm.get("jcr:content/jcr:title", vm.get("jcr:title", folder.getName()));
    }

%>
