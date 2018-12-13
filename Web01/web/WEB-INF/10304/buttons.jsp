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
    
    rdb.setDisabled("frmMain_namTemplateSearch", "0010");
    rdb.setDisabled("frmMain_idTemplate"       , "0010");
    rdb.setDisabled("frmMain_namTemplate"      , "0010");  
    rdb.setDisabled("frmMain_namReportSearch"  , "0010");
    rdb.setDisabled("frmMain_idReport"         , "0010");
    rdb.setDisabled("frmMain_reportName"       , "0010");

    rdb.setReadonly("frmMain_namTemplateSearch", "0010");
    rdb.setReadonly("frmMain_idTemplate"       , "1100");
    rdb.setReadonly("frmMain_namTemplate"      , "1101"); 
    rdb.setReadonly("frmMain_namReportSearch"  , "0010");
    rdb.setReadonly("frmMain_idReport"         , "1111");
    rdb.setReadonly("frmMain_reportName"       , "1111");
    
    rdb.setDisableButton("btnSave"          , "1010");
    rdb.setDisableButton("btnFindTemplate"  , "0010");
    rdb.setDisableButton("btnFindReport"    , "0010");
    rdb.setDisableButton("btnClear"         , "0000");
    
    rdb.setOnChange(
        function() {
            changeAction("frmMain", "10304", "");
            try{
            $("#frmMain_namTemplateSearch").focus();
            }catch(err){

            }
        },
        function() {
            changeAction("frmMain", "10304", "_add");
            $("#frmMain_namTemplateSearch").focus();
        },
        null,
        function() {
            changeAction("frmMain", "10304", "_delete");
            $("#frmMain_namTemplateSearch").focus();
        },      
        function(removeMsg) {
            //alert("Delete");
            $("#frmMain_namTemplateSearch").attr("value", null);
            $("#frmMain_idTemplate").attr("value", null);
            $("#frmMain_namTemplate").attr("value", null);
            $("#frmMain_namReportSearch").attr("value", null);
            $("#frmMain_idReport").attr("value", null);
            $("#frmMain_reportName").attr("value", null);
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