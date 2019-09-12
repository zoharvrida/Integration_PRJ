<%@page import="java.util.UUID"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<sj:radio
    id="rbBS"
    list="{'Inquiry', 'Add', 'Edit', 'Delete', 'Clear'}"
    name="rbBS" disabled="true"
/>
<script type="text/javascript">
jQuery(document).ready(function() {
    $(window).resize();
    rdb.resetDisabled();
    rdb.resetReadonly();
    $("#frmMain_period").val('<s:date name="%{#session.dtBusiness}" format="yyyyMM" />');
    
    rdb.setDisabled("frmMain_noCif" , "0000");
    rdb.setDisabled("frmMain_namCustFull", "1000");
    rdb.setDisabled("frmMain_refTxn", "1001");
    rdb.setDisabled("dtmTxn", "1000");
    rdb.setDisabled("ccyTxn", "1000");
    rdb.setDisabled("frmMain_amtTxnScr", "1000");
    rdb.setDisabled("frmMain_ratFcyIdrScr", "1000");
    rdb.setDisabled("frmMain_amtTxnLcyScr", "1000");
    rdb.setDisabled("frmMain_ratUsdIdrScr", "1000");
    rdb.setDisabled("frmMain_amtTxnUsdScr", "1000");
    rdb.setDisabled("frmMain_txnDesc", "1000");
    rdb.setDisabled("frmMain_amtAvailUsdScr", "1000");
    rdb.setDisabled("frmMain_noUd", "1000");
    
    rdb.setReadonly("frmMain_noCif", "0000");
    rdb.setReadonly("frmMain_namCustFull", "1111");
    rdb.setReadonly("frmMain_refTxn", "1101");
    rdb.setReadonly("dtmTxn", "1111");
    rdb.setReadonly("ccyTxn", "1001");
    rdb.setReadonly("frmMain_amtTxnScr", "1001");
    rdb.setReadonly("frmMain_ratFcyIdrScr", "1001");
    rdb.setReadonly("frmMain_amtTxnLcyScr", "1111");
    rdb.setReadonly("frmMain_ratUsdIdrScr", "1111");
    rdb.setReadonly("frmMain_amtTxnUsdScr", "1111");
    rdb.setReadonly("frmMain_txnDesc", "1001");
    rdb.setReadonly("frmMain_amtAvailUsd", "1111");
    rdb.setReadonly("frmMain_noUd", "1101");

    rdb.setDisableButton("btnSave", "1100");
    rdb.setDisableButton("btnFindRefTxn", "1111");
    rdb.setDisableButton("btnFindUd", "1111");
    rdb.setDisableButton("btnClear", "0000");
    
    rdb.setOnChange(
        function() {
            changeAction("frmMain", "20402", "");

            $("#frmMain_noCif").focus();
            $("#frmMain_refTxn").attr("value", null);
        },
        function() {
            changeAction("frmMain", "20402", "_add");

            $("#frmMain_noCif").focus();
        },
        null,
        function() {
            changeAction("frmMain", "20402", "_delete");

            $("#frmMain_noCif").focus();
            $("#frmMain_refTxn").attr("value", null);
        },
        function(removeMsg) {
            $("#frmMain_noCif").attr("value", null);
            $("#frmMain_namCustFull").attr("value", null);
            $("#dtmTxn").attr("value", null);
            $("#ccyTxn").attr("value", null);
            $("#ccyTxn_combobox > input").attr("value", null);
            $("#frmMain_amtTxnScr").attr("value", null);
            $("#frmMain_ratFcyIdrScr").attr("value", null);
            $("#frmMain_amtTxnLcyScr").attr("value", null);
            $("#frmMain_ratUsdIdrScr").attr("value", null);
            $("#frmMain_amtTxnUsdScr").attr("value", null);
            $("#frmMain_txnDesc").attr("value", null);
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

    rdb.setMenuAccess('<s:property value="%{#session.accessRight}" />', '1101');
});
</script>