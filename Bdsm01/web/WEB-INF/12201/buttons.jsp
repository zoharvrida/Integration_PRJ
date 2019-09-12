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
    
    rdb.setDisabled("frmMain_namReportSearch"  , "0000");
    rdb.setDisabled("frmMain_idReport"         , "0000");
    rdb.setDisabled("frmMain_reportName"       , "0000");
 
    rdb.setReadonly("frmMain_namReportSearch"  , "0000");
    rdb.setReadonly("frmMain_idReport"         , "1111");
    rdb.setReadonly("frmMain_reportName"       , "1111");
    
    rdb.setDisableButton("btnSave"          , "0011");
    rdb.setDisableButton("btnFindReport"    , "0011");
    rdb.setDisableButton("btnClear"         , "0111");
    
    rdb.setOnChange(
        function() {
            changeAction("frmMain", "12201", "_add");
            $("#frmMain_namReportSearch").focus();
        },
        null,
        null,
        null,      
        function(removeMsg) {
            $("#frmMain_namReportSearch").attr("value", null);
            //$("#frmMain_idReport").attr("value", null);
            //$("#frmMain_reportName").attr("value", null);
            var k = $("#dummy").val();
            for(var i = 0; i< k;i++){
              $("#parameter" + i).attr("value", null);   
            }
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