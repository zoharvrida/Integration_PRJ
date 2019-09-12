<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<sj:radio
    id="rbBS"
    list="{'Add', 'Clear'}" 
    name="rbBS" disabled="true"
/>
<script type="text/javascript">
jQuery(document).ready(function() {
    $(window).resize();
    rdb.resetDisabled();
    rdb.resetReadonly();

    rdb.setDisabled("frmMain_noAcct"         , "0000");
    rdb.setDisabled("frmMain_dtPost"         , "0000");
    rdb.setDisabled("frmMain_refTxn"         , "0000");
    rdb.setDisabled("frmMain_ccyTxn"         , "0000");
    rdb.setDisabled("frmMain_noCif"          , "0000");
    rdb.setDisabled("frmMain_namCustFull"    , "0000");
    rdb.setDisabled("frmMain_codAcctCustRel" , "0000");
    rdb.setDisabled("frmMain_typMsg"         , "0000");
    rdb.setDisabled("frmMain_amtTxnScr"      , "0000");
    rdb.setDisabled("frmMain_ratFcyIdrScr"   , "0000");
    rdb.setDisabled("frmMain_amtTxnLcyScr"   , "0000");
    rdb.setDisabled("frmMain_ratUsdIdrScr"   , "0000");
    rdb.setDisabled("frmMain_amtTxnUsdScr"   , "0000");
    rdb.setDisabled("frmMain_txnDesc"        , "0000");
    rdb.setDisabled("frmMain_dtmTxn"         , "0000");
    rdb.setDisabled("frmMain_dtValue"        , "0000");
    rdb.setDisabled("frmMain_idTxn"          , "0000");
    rdb.setDisabled("frmMain_txnBranch"      , "0000");
    rdb.setDisabled("frmMain_amtAvailUsdScr" , "0000");
    rdb.setDisabled("frmMain_noUd"           , "0000");

    rdb.setReadonly("frmMain_noAcct"         , "1010");
    rdb.setReadonly("frmMain_dtPost"         , "1111");
    rdb.setReadonly("frmMain_refTxn"         , "1111");
    rdb.setReadonly("frmMain_ccyTxn"         , "1111");
    rdb.setReadonly("frmMain_noCif"          , "1111");
    rdb.setReadonly("frmMain_namCustFull"    , "1111");
    rdb.setReadonly("frmMain_codAcctCustRel" , "1111");
    rdb.setReadonly("frmMain_typMsg"         , "1111");
    rdb.setReadonly("frmMain_amtTxnScr"      , "1111");
    rdb.setReadonly("frmMain_ratFcyIdrScr"   , "1111");
    rdb.setReadonly("frmMain_amtTxnLcyScr"   , "1111");
    rdb.setReadonly("frmMain_ratUsdIdrScr"   , "1111");
    rdb.setReadonly("frmMain_amtTxnUsdScr"   , "1111");
    rdb.setReadonly("frmMain_txnDesc"        , "1111");
    rdb.setReadonly("frmMain_dtmTxn"         , "1111");
    rdb.setReadonly("frmMain_dtValue"        , "1111");
    rdb.setReadonly("frmMain_idTxn"          , "1111");
    rdb.setReadonly("frmMain_txnBranch"      , "1111");
    rdb.setReadonly("frmMain_amtAvailUsdScr" , "1111");
    rdb.setReadonly("frmMain_noUd"           , "1111");

    rdb.setDisableButton("btnFindCifNo", "1111");
    rdb.setDisableButton("btnFindUd"   , "1111");
    rdb.setDisableButton("btnSave"     , "1111");

    rdb.setOnChange(
        null,
        function() {
            changeAction("frmMain", "28401", "_add");

            $("#frmMain_noAcct").focus();
        },
        null,

        null,
        function(removeMsg) {
            $("#frmMain_dtPost").val('<s:date name="%{#session.dtBusiness}" format="dd/MM/yyyy" />');
            
            $("#frmMain_period").val('<s:date name="%{#session.dtBusiness}" format="yyyyMM" />');
            $("#frmMain_noAcct").attr("value", null);
            $("#frmMain_refTxn").attr("value", null);
            $("#frmMain_ccyTxn").attr("value", null);
            $("#frmMain_amtTxnScr").attr("value", null);
            $("#frmMain_ratFcyIdrScr").attr("value", null);
            $("#frmMain_amtTxnLcyScr").attr("value", null);
            $("#frmMain_ratUsdIdrScr").attr("value", null);
            $("#frmMain_amtTxnUsdScr").attr("value", null);
            $("#frmMain_txnDesc").attr("value", null);
            $("#frmMain_dtmTxn").attr("value", null);
            $("#frmMain_dtValue").attr("value", null);
            $("#frmMain_idTxn").attr("value", null);
            $("#frmMain_typMsg").attr("value", null);
            $("#frmMain_txnBranch").attr("value", null);
            $("#frmMain_noCif").attr("value", null);
            $("#frmMain_namCustFull").attr("value", null);
            $("#frmMain_codAcctCustRel").attr("value", null);
            $("#frmMain_amtAvailUsdScr").attr("value", null);
            $("#frmMain_noUd").attr("value", null);
            if (removeMsg) {
                $("#actionMessage").remove();
                $("#actionError").remove();
                $("span.errorMessage").remove();
                $("label").removeClass("errorLabel");
            }
        }
    );

    $("span.ui-button-text:contains('Inquiry')").replaceWith("<span class='ui-button-text'>&nbsp;</span>");
    $("span.ui-button-text:contains('Add')").replaceWith("<span class='ui-button-text'>Flag</span>");
    $("span.ui-button-text:contains('Edit')").replaceWith("<span class='ui-button-text'>&nbsp;</span>");
    $("span.ui-button-text:contains('Delete')").replaceWith("<span class='ui-button-text'>&nbsp;</span>");
	//$("span.ui-button-text:contains('Delete')").replaceWith("<span class='ui-button-text'>Unflag</span>");
    rdb.setMenuAccess('<s:property value="%{#session.accessRight}" />', '0101');
    
    var sessionNoAcct = '<s:property value="%{#session.noAcct}" />';
    var sessionRefTxn = '<s:property value="%{#session.refTxn}" />';
    var sessionTypMsg = '<s:property value="%{#session.typMsg}" />';
    var sessionTypTrx = '<s:property value="%{#session.typTrx}" />';
	var sessionDate = '<s:property value="%{#session.dateTxn}" />';
	//alert("DATE :" + sessionDate);
    var sessionGoAction = '<s:property value="%{#session.goAction}" />';
    <s:set name="noAcct" value="%{}" scope="session"/>
    <s:set name="goAction" value="%{}" scope="session"/>
    if (sessionNoAcct!='' && sessionRefTxn!='' && sessionTypMsg!='') {
        setTimeout(function() {
            if (sessionGoAction=="flag") {
                $("#rbBSAdd").change().click();
                $("#rbBSAdd").button("refresh");
            } else if (sessionGoAction=="unflag") {
                $("#rbBSDelete").change().click();
                $("#rbBSDelete").button("refresh");
            }
            
            tempParams = {};
            tempParams.onClose = function(noAcct, refTxn, ccyTxn, amtTxn, ratFcyIdr, amtTxnLcy, ratUsdIdr, 
                                          amtTxnUsd, txnDesc, dtmTxn, dtValue, idTxn, typMsg, txnBranch) {
                $("#frmMain_noAcct").val(noAcct);
                $("#frmMain_refTxn").val(refTxn);
                $("#frmMain_ccyTxn").val(ccyTxn);
                $("#frmMain_amtTxn").val(amtTxn);
                $("#frmMain_amtTxnScr").val($("#frmMain_amtTxn").val());
                $("#frmMain_amtTxnScr").formatCurrency({decimalSymbol:'.', digitGroupSymbol:',', symbol:''});
                $("#frmMain_ratFcyIdr").val(ratFcyIdr);
                $("#frmMain_ratFcyIdrScr").val($("#frmMain_ratFcyIdr").val());
                $("#frmMain_ratFcyIdrScr").formatCurrency({decimalSymbol:'.', digitGroupSymbol:',', symbol:''});
                $("#frmMain_amtTxnLcy").val(amtTxnLcy);
                $("#frmMain_amtTxnLcyScr").val($("#frmMain_amtTxnLcy").val());
                $("#frmMain_amtTxnLcyScr").formatCurrency({decimalSymbol:'.', digitGroupSymbol:',', symbol:''});
                $("#frmMain_ratUsdIdr").val(ratUsdIdr);
                $("#frmMain_ratUsdIdrScr").val($("#frmMain_ratUsdIdr").val());
                $("#frmMain_ratUsdIdrScr").formatCurrency({decimalSymbol:'.', digitGroupSymbol:',', symbol:''});
                $("#frmMain_amtTxnUsd").val(amtTxnUsd);
                $("#frmMain_amtTxnUsdScr").val($("#frmMain_amtTxnUsd").val());
                $("#frmMain_amtTxnUsdScr").formatCurrency({decimalSymbol:'.', digitGroupSymbol:',', symbol:''});
                $("#frmMain_txnDesc").val(txnDesc);
                $("#frmMain_dtmTxn").val(dtmTxn);
                $("#frmMain_dtValue").val(dtValue);
                $("#frmMain_idTxn").val(idTxn);
                if(typMsg == 'O') {
                    $("#frmMain_typMsg").val('Original');
                } else if(typMsg == 'R') {
                    $("#frmMain_typMsg").val('Reversal');
                }                
                $("#frmMain_txnBranch").val(txnBranch);
                $("#btnFindCifNo").button("enable");
            };
            $("#formGetData_noAcct").val(sessionNoAcct);
            $("#formGetData_refTxn").val(sessionRefTxn);
            $("#formGetData_typMsg").val(sessionTypMsg);
            $("#formGetData_typTrx").val(sessionTypTrx);
            $("#tempGetData").click();
        }, 1000);
    }
});
</script>