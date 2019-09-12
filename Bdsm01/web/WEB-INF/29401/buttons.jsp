<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sj" uri="/struts-jquery-tags" %>

<sj:radio id="rbBS" list="{'Inquiry','Clear'}" name="rbBS" disabled="true" />

<script type="text/javascript">
	jQuery(document).ready(function() {
		/* $("#rbBS").html("&nbsp;"); */
		$(window).resize();
		rdb.resetDisabled();
        rdb.resetReadonly();
		
		rdb.setOnChange(
			function() {
				$("#frmMain_cCodeRate").focus();
			},
			null,
			null,
			null,      
			function(removeMsg) {
				$("#frmMain_namCurrency").attr("value", null),
                $("#frmMain_namCurrencyDesc").attr("value", null),
                $("#frmMain_oriAmount").attr("value", null),
                $("#frmMain_usdEquival").attr("value", null),
                $("#frmMain_currencyCode").attr("value", null),
                $("#frmMain_FlagCode").attr("value", null),
                $("#frmMain_usdDestinationRate").attr("value", null),
                $("#frmMain_destAmount").attr("value", null);
				$('#usdDestCcyRate').html("USD/");
				
				if (removeMsg) {
					$("#actionMessage").remove();
					$("#actionError").remove();
					$("span.errorMessage").remove();
					$("label").removeClass("errorLabel");
					$("#btnCalculate").hide();
				}
			}
		);
		
		rdb.setMenuAccess('<s:property value="%{#session.accessRight}" />', '11111');
	});
</script>