<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<s:form id="frmMain" name="frmMain" action="monitoring" method="post" validate="true">
    <s:textfield
        name="availCpu"
        size="60"
        requiredLabel="true"
        maxlength="40"
        cssClass="ui-widget ui-widget-content"
        key="availCpu"
        disabled="true"
        cssStyle="text-transform:uppercase"
        />
    <s:textfield
        name="osName"
        size="60"
        requiredLabel="true"
        maxlength="40"
        cssClass="ui-widget ui-widget-content"
        key="osName"
        disabled="true"
        cssStyle="text-transform:uppercase"
        />
    <s:textfield
        name="osVer"
        size="60"
        requiredLabel="true"
        maxlength="40"
        cssClass="ui-widget ui-widget-content"
        key="osVer"
        disabled="true"
        cssStyle="text-transform:uppercase"
        />
    <s:textfield
        name="osArch"
        size="60"
        requiredLabel="true"
        maxlength="40"
        cssClass="ui-widget ui-widget-content"
        key="osArch"
        disabled="true"
        cssStyle="text-transform:uppercase"
        />
    <sj:submit
        id="btnSave"
        formIds="frmMain"
        buttonIcon="ui-icon-gear"
        button="true"
        key="button.save"
        disabled="false"
        targets="ph-main"
        onBeforeTopics="beforeSubmit"
        />
</s:form>
<script type="text/javascript">
    tempParams = {};
    tempParams.onClose = function(availCpu, osName, osVer, osArch) {
        $("#frmMain_availCpu").val(availCpu);
        $("#frmMain_osName").val(osName);
        $("#frmMain_osVer").val(osVer);
        $("#frmMain_osArch").val(osArch);
    }
    $("#btnSave").subscribe("beforeSubmit", function(event) {
        $("#frmMain").unbind("submit");
        changeAction("frmMain", "monitoring", "_monitor");
        $("#btnSave").unsubscribe("beforeSubmit");
        $("#btnSave").click();
    }
</script>