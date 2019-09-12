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
    
    rdb.setDisabled("frmMain_namUserSearch"    , "0000");
    rdb.setDisabled("frmMain_idUser"           , "1000");
    rdb.setDisabled("frmMain_namUser"          , "1000");
    rdb.setDisabled("frmMain_cdBranch"         , "1000");
    rdb.setDisabled("frmMain_namTemplateSearch", "1001");
    rdb.setDisabled("frmMain_idTemplate"       , "1000");
    rdb.setDisabled("frmMain_namTemplate"      , "1000");
    
    rdb.setReadonly("frmMain_namUserSearch"    , "0100");
    rdb.setReadonly("frmMain_idUser"           , "1011");
    rdb.setReadonly("frmMain_namUser"          , "1111");
    rdb.setReadonly("frmMain_cdBranch"         , "1001");
    rdb.setReadonly("frmMain_namTemplateSearch", "1001");
    rdb.setReadonly("frmMain_idTemplate"       , "1111");
    rdb.setReadonly("frmMain_namTemplate"      , "1111");
    
    rdb.setDisableButton("btnCheckAD"     , "1001");
    rdb.setDisableButton("btnSave"        , "1000");
    rdb.setDisableButton("btnFindUser"    , "0100");
    rdb.setDisableButton("btnFindTemplate", "1001");
    
    rdb.setOnChange(
        function() {
            changeAction("frmMain", "10303", "");
            try{
            $("#frmMain_namUserSearch").focus();
            }catch(err){

            }
        },
        function() {
            changeAction("frmMain", "10303", "_add");
            $("#frmMain_idUser").focus();
        },
        function() {
            changeAction("frmMain", "10303", "_edit");
            $("#frmMain_namUserSearch").focus();
        },
        function() {
            changeAction("frmMain", "10303", "_delete");
            $("#frmMain_namUserSearch").focus();
        },
        function(removeMsg) {
            $("#frmMain_namUserSearch").attr("value", null);
            $("#frmMain_idUser").attr("value", null);
            $("#frmMain_namUser").attr("value", null);
            $("#frmMain_cdBranch").attr("value", null);
            $("#frmMain_namTemplateSearch").attr("value", null);
            $("#frmMain_idTemplate").attr("value", null);
            $("#frmMain_namTemplate").attr("value", null);
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