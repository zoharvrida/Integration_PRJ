<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<sj:radio
    id="rbBS"
    list="{'Clear'}"
    name="rbBS" disabled="true"
    />
<script type="text/javascript">
    jQuery(document).ready(function() {
        $(window).resize();
        rdb.resetDisabled();
        rdb.resetReadonly();


        rdb.setOnChange(
                null,
                null,
                null,
                null,
                function(removeMsg) {
                    $("#frmMain_theFile").attr("value", null);
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