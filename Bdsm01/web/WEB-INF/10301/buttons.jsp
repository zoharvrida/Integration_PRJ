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
    
    rdb.setDisabled("frmMain_namTemplateSearch", "0100");
    rdb.setDisabled("frmMain_idTemplate"       , "1000");
    rdb.setDisabled("frmMain_namTemplate"      , "1000");

    rdb.setReadonly("frmMain_namTemplateSearch", "0100");
    rdb.setReadonly("frmMain_idTemplate"       , "1011");
    rdb.setReadonly("frmMain_namTemplate"      , "1001");
    
    rdb.setDisableButton("btnSave", "1000");
    rdb.setDisableButton("btnFind", "0100");
    
    rdb.setOnChange(
        function() {
            changeAction("frmMain", "10301", "");
            try{
            $("#frmMain_namTemplateSearch").focus();
            }catch(err){

            }
        },
        function() {
            changeAction("frmMain", "10301", "_add");
            $("#frmMain_idTemplate").focus();
        },
        function() {
            changeAction("frmMain", "10301", "_edit");
            $("#frmMain_namTemplateSearch").focus();
        },
        function() {
            changeAction("frmMain", "10301", "_delete");
            $("#frmMain_namTemplateSearch").focus();
        },
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