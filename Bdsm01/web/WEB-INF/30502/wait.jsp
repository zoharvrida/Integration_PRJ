<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>

<s:form id="frmMain" name="frmMain" action="%{#session.__background_name}.action" method="post" validate="false" theme="simple" >
    <sj:submit
        id="btnFind"
        formIds="frmMain"
        buttonIcon="ui-icon-gear"
        button="true"
        key="button.find"
    />
</s:form>
<script type="text/javascript">
$(function() {
    $("#spinner").removeClass("ui-helper-hidden");
    setTimeout(function() {
        $("#btnFind").click();
    }, 5000);
});

</script>
