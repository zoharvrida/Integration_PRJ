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
    
    rdb.setDisabled("dtmRequest", "0100");
    rdb.setDisabled("frmMain_branch"       , "1000");
    rdb.setDisabled("frmMain_branchDesc"      , "1000");
    rdb.setDisabled("frmMain_currency"       , "1000");
    rdb.setDisabled("frmMain_currencyDesc"      , "1000");
    rdb.setDisabled("frmMain_txnId"       , "1000");
    rdb.setDisabled("frmMain_statusDesc"      , "1000");
    rdb.setDisabled("frmMain_vendor"       , "1000");
    rdb.setDisabled("frmMain_customer"      , "1000");
    rdb.setDisabled("frmMain_totalAmtReq"       , "1000");

    rdb.setReadonly("frmMain_branch", "0100");
    rdb.setReadonly("frmMain_branchDesc"       , "1011");
    rdb.setReadonly("frmMain_currency", "0100");
    rdb.setReadonly("frmMain_currencyDesc"       , "1011");
    rdb.setReadonly("frmMain_txnId", "0100");
    rdb.setReadonly("frmMain_statusDesc"       , "1011");
    rdb.setReadonly("frmMain_vendor", "0100");
    rdb.setReadonly("frmMain_customer"       , "1011");
    rdb.setDisabled("frmMain_totalAmtReq"       , "1000");
    
    rdb.setDisableButton("btnLookupBranch", "1111");
    rdb.setDisableButton("btnLookupCurrency", "1011");
    rdb.setDisableButton("btnLookupCustomer", "1011");

    rdb.setDisableButton("btnRequest", "1000");
    rdb.setDisableButton("btnDenomSearch", "1011");
    
    rdb.setOnChange(
        function() {
            changeAction("frmMain", "21506", "");
            try{
            $("#frmMain_namTemplateSearch").focus();
            }catch(err){

            }
        },
        function() {
            changeAction("frmMain", "21506", "_add");
            $("#frmMain_idTemplate").focus();
        },
        null,
        null,
        function(removeMsg) {
            $("#frmMain_idTemplate").attr("value", null);
            $("#frmMain_namTemplate").attr("value", null);
            $("#frmMain_namTemplateSearch").attr("value", null);
            if (removeMsg) {
                $("#actionMessage").remove();
                $("#actionError").remove();
                $("span.errorMessage").remove();
                $("label").removeClass("errorLabel");
            }
        }
    );

    rdb.setMenuAccess('<s:property value="%{#session.accessRight}" />', '1111');
});
</script>