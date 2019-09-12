<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<sj:radio
    id="rbBS"
    list="{}" 
    name="rbBS" disabled="true"
/>
<script type="text/javascript">
jQuery(document).ready(function() {
    $(window).resize();
    rdb.resetDisabled();
    rdb.resetReadonly();
    $("#rbBS").html("&nbsp;");
    rdb.setDisableButton("btnSave"          , "0011");

    rdb.setOnChange( 
        function () {
            changeAction("frmMain", "12202", "_gridDownload");
        },
        null,
        null,
        null,      
        function(removeMsg) {
            if (removeMsg) {
                $("#actionMessage").remove();
                $("#actionError").remove();
                $("span.errorMessage").remove();
                $("label").removeClass("errorLabel");
            }
        }
    );   
    rdb.setMenuAccess('<s:property value="%{#session.accessRight}" />', '1000');
    
    var sessionGoAction = '<s:property value="%{#session.goAction}" />';
    
    <s:set name="goAction" value="%{}" scope="session"/>
        setTimeout(function() {
            if (sessionGoAction=="Refresh") {
                $("#rbBSInquiry").change().click();
                $("#rbBSInquiry").button("refresh");
            } 
        });
});
</script>