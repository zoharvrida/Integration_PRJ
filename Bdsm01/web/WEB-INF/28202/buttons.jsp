<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<sj:radio
    id="rbBS"
    list="{'Inquiry','Clear'}"
    name="rbBS" disabled="true"
/>
<script type="text/javascript">
jQuery(document).ready(function() {
    $(window).resize();
    rdb.resetDisabled();
    rdb.resetReadonly();

    rdb.setDisabled("frmMain_noAcct"        , "0010");
    rdb.setDisabled("frmMain_txnAmountscr"  , "0010");
	
    rdb.setReadonly("frmMain_noAcct"        , "0000");
    rdb.setReadonly("frmMain_acctTitle"        , "1111");
    rdb.setReadonly("frmMain_balAvailscr"        , "1111");
    rdb.setReadonly("frmMain_txnAmountscr"        , "0000");
    rdb.setReadonly("frmMain_midRatescr"        , "1111");
    
    rdb.setDisableButton("btnSave"   , "0000");
	rdb.setDisableButton("btnClear"  , "0111");

    rdb.setOnChange(
        function() {
            changeAction("frmMain", "28202", "");
            $("#frmMain_noAcct").focus();
        },
        null,
        null,
        null,
        function(removeMsg) {
            $("#frmMain_noAcct").attr("value", null);
            $("#frmMain_acctTitle").attr("value", null);
            $("#frmMain_balAvailscr").attr("value",null);
			$("#ccyUd").attr("value",'');
			$("#frmMain_txnAmountscr").attr("value",null);
			$("#frmMain_midRatescr").attr("value",null);
			$("#frmMain_amtUsdscr").attr("value",null);
                    $("#frmMain_model_codProd").attr("value",null);
                    $("#frmMain_prodData_namProduct").attr("value",null);
                    $("#btnSave").hide();
                    $("#typeS-2LLD").attr("checked", false).button("refresh");
                    $("#typeS-1LLD").attr("checked", false).button("refresh");
//            $("#frmMain_periodStr").attr("value", null);
            if (removeMsg) {
                $("#actionMessage").remove();
                $("#actionError").remove();
                $("span.errorMessage").remove();
                $("label").removeClass("errorLabel");
            }
        }
    );

    $("span.ui-button-text:contains('Inquiry')").replaceWith("<span class='ui-button-text'>Request</span>");
    $("span.ui-button-text:contains('Add')").replaceWith("<span class='ui-button-text'>&nbsp;</span>");
    $("span.ui-button-text:contains('Edit')").replaceWith("<span class='ui-button-text'>&nbsp;</span>");
    $("span.ui-button-text:contains('Delete')").replaceWith("<span class='ui-button-text'>&nbsp;</span>");
    
    rdb.setMenuAccess('<s:property value="%{#session.accessRight}" />', '1000');
    
    var sessionGoAction = '<s:property value="%{#session.goAction}" />';
    
    <s:set name="goAction" value="%{}" scope="session"/>
        setTimeout(function() {
            if (sessionGoAction=="Request") {
                $("#rbBSInquiry").change().click();
                $("#rbBSInquiry").button("refresh");
            } 
        });
});
</script>