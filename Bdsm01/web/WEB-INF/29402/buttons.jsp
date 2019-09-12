<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sj" uri="/struts-jquery-tags" %>

<sj:radio id="rbBS" 
          list="{'Add','Clear'}" 
          name="rbBS" disabled="true" />

<script type="text/javascript">
	jQuery(document).ready(function() {
		/* $("#rbBS").html("&nbsp;"); */
		$(window).resize();
		
		rdb.setOnChange(
			null,
			function() {
				changeAction("frmMain", "29402", "_add");
				$('#frmMain_refNetworkNo').focus();
				$("#btnSave").hide();
			},
			null,
			null,      
			function(removeMsg) {
				$("#frmMain_refNetworkNo").attr("value", '');
                $("#frmMain_codAccNo").attr("value",'');
                $("#frmMain_nameCurrency").attr("value",'');
                $("#frmMain_amtTxnAcy").attr("value",'');
                $("#frmMain_amtTxnTcy").attr("value",'');
                $("#frmMain_currencyCode").attr("value",'');
                $("#frmMain_sellRate").attr("value",'');
                $("#frmMain_destAmount").attr("value",'');
				$("#frmMain_codAcctTitle").attr("value",'');
				
				if (removeMsg) {
					$("#actionMessage").remove();
					$("#actionError").remove();
					$("span.errorMessage").remove();
					$("label").removeClass("errorLabel");
					$("#btnLookupDestCcyInq").button("enable");
					$("#btnCalc").button("enable");
					$("#btnSave").hide();
					$("#btnGeneratePDF").hide();
				}
			}
		);
		
		rdb.setMenuAccess('<s:property value="%{#session.accessRight}" />', '1111');
	});
</script>