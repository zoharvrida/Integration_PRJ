<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<sj:radio
    id="rbBS"
    list="{'Inquiry', 'Add', 'Clear'}"
    name="rbBS" disabled="true"
/>
<script type="text/javascript">
jQuery(document).ready(function() {
    $(window).resize();
    rdb.resetDisabled();
    rdb.resetReadonly();

    rdb.setDisabled("frmMain_noCif"        , "0010");
    rdb.setDisabled("frmMain_namCustFull"  , "1111");
    rdb.setDisabled("frmMain_periodStr"    , "0010");
    rdb.setDisabled("frmMain_cdBranch"     , "1010");
    rdb.setDisabled("frmMain_slCreatorUser", "1010");

    rdb.setReadonly("frmMain_noCif"        , "0000");
    rdb.setReadonly("frmMain_namCustFull"  , "1111");
    rdb.setReadonly("frmMain_periodStr"    , "0011");
    rdb.setReadonly("frmMain_cdBranch"     , "1111");
    rdb.setReadonly("frmMain_slCreatorUser", "1111");

    rdb.setDisableButton("btnSave"   , "1010");

    rdb.setOnChange(
        function() {
            changeAction("frmMain", "20301", "");

            $("#frmMain_noCif").focus();
            $("#frmMain_periodStr").val('<s:date name="%{#session.dtBusiness}" format="MM/yyyy" />');
            $("#frmMain_cdBranch").attr("value", null);
            $("#frmMain_slCreatorUser").attr("value", null);
        },
        function() {
            changeAction("frmMain", "20301", "_add");

            $("#frmMain_noCif").focus();
            $("#frmMain_periodStr").val('<s:date name="%{#session.dtBusiness}" format="MM/yyyy" />');
            $("#frmMain_cdBranch").val('<s:property value="%{#session.cdBranch}" />');
            $("#frmMain_slCreatorUser").val('<s:property value="%{#session.idUser}" />');
        },
        null,
        null,
        function(removeMsg) {
            $("#frmMain_noCif").attr("value", null);
            $("#frmMain_namCustFull").attr("value", null);
//            $("#frmMain_periodStr").attr("value", null);
            if (removeMsg) {
                $("#actionMessage").remove();
                $("#actionError").remove();
                $("span.errorMessage").remove();
                $("label").removeClass("errorLabel");
            }
        }
    );

    rdb.setMenuAccess('<s:property value="%{#session.accessRight}" />', '1100');
    
    var sessionNoCif = '<s:property value="%{#session.noCif}" />';
    var sessionGoAction = '<s:property value="%{#session.goAction}" />';
    <s:set name="noCif" value="%{}" scope="session"/>
    <s:set name="goAction" value="%{}" scope="session"/>
    if (sessionNoCif!='') {
        setTimeout(function() {
            if (sessionGoAction=="add") {
                $("#rbBSAdd").change().click();
                $("#rbBSAdd").button("refresh");
                $("#frmMain_noCif").val(sessionNoCif);
                $("#frmMain_noCif").trigger($.Event("keydown", { keyCode: 9 }));
                $("#frmMain_periodStr").focus();
            } else if (sessionGoAction=="inquiry") {
                $("#frmMain_noCif").val(sessionNoCif);
                $("#frmMain_noCif").trigger($.Event("keydown", { keyCode: 9 }));
                $("#frmMain_periodStr").focus();
            }
        }, 1000);
    }
});
</script>