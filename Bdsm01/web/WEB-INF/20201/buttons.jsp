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
    
    rdb.setDisabled("frmMain_noAccount", "0111");
    rdb.setReadonly("frmMain_noAccount", "0111");

    rdb.setOnChange(
        function() {
            changeAction("frmMain", "20201", "");

            $("#frmMain_noAccount").focus();
            $("#tabbedpanel").tabs("option", "disabled", [0,1,2]);
        },
        null,
        null,
        null,
        function(removeMsg) {
            $("#frmMain_noAccount").attr("value", null);
            $("#frmMain_noAccount").attr("readonly", null);
            $("#tabbedpanel").tabs("option", "selected", 0);
                $("#btnFind").button("enable");
            $("#frmMain_period").val('<s:date name="%{#session.dtBusiness}" format="yyyyMM" />');
            if (removeMsg) {
                $('#tabbedpanel').tabs('url', 0, '20201T0_none');
                $('#tabbedpanel').tabs('load', 0);
                $("#actionMessage").remove();
                $("#actionError").remove();
                $("span.errorMessage").remove();
                $("label").removeClass("errorLabel");
            }
        }
    );

    rdb.setMenuAccess('<s:property value="%{#session.accessRight}" />', '1000');
});
</script>