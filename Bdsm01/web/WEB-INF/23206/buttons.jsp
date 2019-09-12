<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sj" uri="/struts-jquery-tags" %>

<sj:radio 
		id="rbBS" 
		list="{}" 
		name="rbBS"
		disabled="true" 
/>

<script type="text/javascript">
	jQuery(document).ready(function() {
		$("#rbBS").html('&nbsp;');
		$(window).resize();
	});
</script>