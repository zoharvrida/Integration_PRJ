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
    
        rdb.setDisabled("gRPID"             , "0101");
        rdb.setDisabled("schedImport"       , "0101");
        rdb.setDisabled("requester"         , "0101");
        rdb.setDisabled("supervisor"        , "0101");
        rdb.setDisabled("monitoring"        , "0101");

        rdb.setReadonly("gRPID"             , "0101");
        rdb.setReadonly("schedImport"       , "0101");
        rdb.setReadonly("requester"         , "1101");
        rdb.setReadonly("supervisor"        , "1101");
        rdb.setReadonly("monitoring"        , "1101");
    
        rdb.setDisableButton("btnSave"  , "1000");
        rdb.setDisableButton("btnClear" , "0000");
    
        rdb.setOnChange(
        function() {
            changeAction("frmMain", "13302", "");
        },
        null,
        function() {
            changeAction("frmMain", "13302", "_edit");
            //document.getElementsById('schedImport').selectedIndex = 0;
            //schedImport.attr("value", "");
        },
        null,
        function(removeMsg) {
            $("#gRPID").attr("value", null);
            $("#schedImport").attr("value", null);
            //schedImport.attr("value", null);
            $("#requester").attr("value", null);
            $("#supervisor").attr("value", null);
            $("#monitoring").attr("value", null);
            if (removeMsg) {
                $("#actionMessage").remove();
                $("#actionError").remove();
                $("span.errorMessage").remove();
                $("label").removeClass("errorLabel");
            }
        }
    );
        rdb.setMenuAccess('<s:property value="%{#session.accessRight}" />', '1010');
    });
</script>