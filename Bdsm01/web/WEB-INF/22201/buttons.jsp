<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sj" uri="/struts-jquery-tags" %>

<sj:radio id="rbBS" list="{'Inquiry', 'Clear'}" name="rbBS" disabled="true" />

<script type="text/javascript">
	jQuery(document).ready(function() {
		/* $("#rbBS").html("&nbsp;"); */
		$(window).resize();
		
		rdb.setOnChange(
			function() {
				$("#frmMain_cardNo").focus();
			},
			null,
			null,
			null,      
			function(removeMsg) {
				$("#frmMain_cardNo").attr("value", null);
				
				if (removeMsg) {
					$("#actionMessage").remove();
					$("#actionError").remove();
					$("span.errorMessage").remove();
					$("label").removeClass("errorLabel");
				}
			}
		);
		
		rdb.setMenuAccess('<s:property value="%{#session.accessRight}" />', '1000');
	});
</script>