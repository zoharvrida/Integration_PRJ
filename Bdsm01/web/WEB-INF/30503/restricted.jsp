<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sj" uri="/struts-jquery-tags" %>


You don't have access right

<s:if test="%{#session.idMenu.equals('30503')}">
	<s:url var="ajaxUpdateTitle" action="30503_title_" />
	<s:url var="ajaxUpdateButtons" action="30503_blank" />
	<sj:a id="tempTitle" href="%{ajaxUpdateTitle}" targets="ph-title" cssClass="ui-helper-hidden" />
	<sj:a id="tempButtons" href="%{ajaxUpdateButtons}" targets="ph-buttons" cssClass="ui-helper-hidden" />
	
	<script type="text/javascript">
		$("#rbBS").buttonset("destroy");
		$("#tempTitle").click();
		$("#tempButtons").click();
	</script>
	
</s:if>

