<%@include file="/libs/harbor/components/global.jsp" %>

<div <c:choose><c:when test="${bootstrapMainAutoNavigation.tree.isSticky && !isEditMode}">class="navbar navbar-default navbar-fixed-top"</c:when><c:otherwise>class="navbar navbar-default"</c:otherwise></c:choose> role="navigation">
    <c:choose>
        <c:when test="${bootstrapMainAutoNavigation.hasRoot && !bootstrapMainAutoNavigation.onStructuralPage}">
            <div class="${bootstrapMainAutoNavigation.tree.containerClass}">
                <cq:include script="navbarheader.jsp" />
                <cq:include script="navbarcontent.jsp" />
            </div>
        </c:when>
        <c:otherwise>
            <div class="${bootstrapMainAutoNavigation.tree.containerClass}">
                <div class="navbar-header">
                    <a class="navbar-brand authoring-placeholder" href="#">Warning - Unrooted Navigation</a>
                </div>
            </div>
        </c:otherwise>
    </c:choose>
</div>