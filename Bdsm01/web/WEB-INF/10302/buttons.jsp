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

    rdb.setDisabled("frmMain_namTemplateSearch", "0101");
    rdb.setDisabled("frmMain_idTemplate"       , "1101");
    rdb.setDisabled("frmMain_namTemplate"      , "1101");
    rdb.setDisabled("frmMain_namMenuSearch"    , "0101");
    rdb.setDisabled("frmMain_idMenu"           , "1101");
    rdb.setDisabled("frmMain_namMenu"          , "1101");

    rdb.setReadonly("frmMain_namTemplateSearch", "0101");
    rdb.setReadonly("frmMain_idTemplate"       , "1111");
    rdb.setReadonly("frmMain_namTemplate"      , "1111");
    rdb.setReadonly("frmMain_namMenuSearch"    , "0101");
    rdb.setReadonly("frmMain_idMenu"           , "1111");
    rdb.setReadonly("frmMain_namMenu"          , "1111");

    rdb.setDisableButton("btnSave"        , "1101");
    rdb.setDisableButton("btnFindTemplate", "0101");
    rdb.setDisableButton("btnFindMenu"    , "0101");
    
    rdb.setOnChange(
        function() {
            changeAction("frmMain", "10302", "");
            try{
            $("#frmMain_namTemplateSearch").focus();
            }catch(err){

            }
        },
        null,
        function() {
            changeAction("frmMain", "10302", "_edit");
            $("#frmMain_namTemplateSearch").focus();
        },
        null,
        function(removeMsg) {
            $("#frmMain_namTemplateSearch").attr("value", null);
            $("#frmMain_idTemplate").attr("value", null);
            $("#frmMain_namTemplate").attr("value", null);
            $("#frmMain_namMenuSearch").attr("value", null);
            $("#frmMain_idMenu").attr("value", null);
            $("#frmMain_namMenu").attr("value", null);
            $("#accessCode").buttonset("disable");
            $("#accessCode-1").attr("checked", false).button("refresh");
            $("#accessCode-2").attr("checked", false).button("refresh");
            $("#accessCode-3").attr("checked", false).button("refresh");
            $("#accessCode-4").attr("checked", false).button("refresh");
            $("#accessCode-5").attr("checked", false).button("refresh");
            $("#accessCode span.ui-button-text:contains('-- --')").replaceWith("<span class='ui-button-text'>Inquiry</span>");
            $("#accessCode span.ui-button-text:contains('Flag')").replaceWith("<span class='ui-button-text'>Add</span>");
            $("#accessCode span.ui-button-text:contains('-----')").replaceWith("<span class='ui-button-text'>Edit</span>");
            $("#accessCode span.ui-button-text:contains('Unflag')").replaceWith("<span class='ui-button-text'>Delete</span>");
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