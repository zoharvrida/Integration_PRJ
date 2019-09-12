<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sj" uri="/struts-jquery-tags" %>

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
    
        //rdb.setDisabled("typeS"                         , "0000");
        rdb.setDisabled("frmMain_namschedProfileSearch" , "0001");
        rdb.setDisabled("frmMain_idschedProfile"        , "0001");
        rdb.setDisabled("frmMain_statusScheduler"       , "0001");
        rdb.setDisabled("idFixTemplate"                 , "0000");
        rdb.setDisabled("frmMain_namschedProfile"       , "0001");
        rdb.setDisabled("statusScheduler"               , "0000");
        rdb.setDisabled("frmMain_oldKey"                , "1001");
        rdb.setDisabled("frmMain_keyEncrypt"            , "1001");
        rdb.setDisabled("frmMain_keyEncryptS"           , "1001");

        //rdb.setReadonly("typeS"                         , "0101");
        rdb.setReadonly("frmMain_namschedProfileSearch" , "0001");
        rdb.setReadonly("frmMain_idschedProfile"        , "1111");
        rdb.setReadonly("frmMain_statusScheduler"       , "1001");
        rdb.setReadonly("idFixTemplate"                 , "0000");
        rdb.setReadonly("frmMain_namschedProfile"       , "1111");
        rdb.setReadonly("statusScheduler"               , "0000");
        rdb.setReadonly("frmMain_oldKey"                , "1001");
        rdb.setReadonly("frmMain_keyEncrypt"            , "1001");
        rdb.setReadonly("frmMain_keyEncryptS"           , "1001");
    
        rdb.setDisableButton("btnSave"                  , "1111");
        rdb.setDisableButton("btnFindschedProfile"      , "0101");
        rdb.setDisableButton("btnClear"                 , "0000");
    
        rdb.setOnChange(
            function() {
                changeAction("frmMain", "13301", "");
                try {
                    $("#choiceAction").buttonset("disable");
                    $("#choiceAction").button("disable");
                    $("#choicePassword").buttonset("disable");
                    $("#choicePassword").button("disable");
                    $("#schedStat").buttonset("enable");
                    $("#schedStat").button("enable");
                    $("#frmMain_namschedProfileSearch").focus();
                }
                catch(err) {}
            },
            null,
            function() {
                changeAction("frmMain", "13301", "_edit");
                $("#choiceAction").buttonset("disable");
                $("#choiceAction").button("disable");
                $("#choicePassword").buttonset("enable");
                $("#choicePassword").button("enable");
                $("#schedStat").buttonset("disable");
                $("#schedStat").button("disable");
            },
            null,
            function(removeMsg) {
                $("#typeS-1").attr("checked", false).button("refresh");
                $("#typeS-2").attr("checked", false).button("refresh");
                $("#choiceAction-1").attr("checked", false).button("refresh");
                $("#choiceAction-2").attr("checked", false).button("refresh");
                $("#schedStatActive").attr("checked", false).button("refresh");
                $("#schedStatInactive").attr("checked", false).button("refresh");
                $("#namschedProfileSearch").attr("value", null);
                $("#frmMain_namschedProfile").attr("value", null);
                $("#frmMain_idschedProfile").attr("value", null);
                $("#statusScheduler").attr("value", null);
                $("#schedStat").attr("value", null);
                $("#oldKey").attr("value", null);
                $("#keyEncrypt").attr("value", null);
                $("#keyEncryptS").attr("value", null);
                $('#status').attr('style', 'display:none');
                $('#validation').attr('style', 'display:none');
                
                $("#fsRefreshParameter").css("display", (rdb.current == 'inquiry')? "block": "none");
                
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