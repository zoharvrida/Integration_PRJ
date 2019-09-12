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
    
    rdb.setDisabled("frmMain_branch"       , "0000");
    rdb.setDisabled("frmMain_branchDesc"      , "0000");
    rdb.setDisabled("frmMain_currency"       , "0000");
    rdb.setDisabled("frmMain_currencyDesc"      , "0000");
    rdb.setDisabled("frmMain_statusDesc"       , "0011");
    rdb.setDisabled("datTXN"      , "0000");

    rdb.setReadonly("frmMain_branch", "0100");
    rdb.setReadonly("frmMain_branchDesc"       , "0011");
    rdb.setReadonly("frmMain_currency", "0100");
    rdb.setReadonly("frmMain_currencyDesc"       , "0011");
    rdb.setReadonly("datTXN", "1100");
    rdb.setReadonly("frmMain_statusDesc"       , "0011");
    
    rdb.setDisableButton("btnLookupBranch21204", "0111");
    rdb.setDisableButton("btnLookupCurrency21204", "0011");
    rdb.setDisableButton("btnLookupStatus21204", "0011");
    
    rdb.setOnChange(
        function() {
            changeAction("frmMain", "21204", "");
        },
        null,
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