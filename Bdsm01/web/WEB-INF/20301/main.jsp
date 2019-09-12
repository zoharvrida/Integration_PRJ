<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<% if(!request.getMethod().equals("GET")) { %>
<s:url var="ajaxUpdateTitle" action="20301_title" />
<s:url var="ajaxUpdateButtons" action="20301_buttons" />
<sj:a id="tempTitle" href="%{ajaxUpdateTitle}" targets="ph-title" cssClass="ui-helper-hidden"></sj:a>
<sj:a id="tempButtons" href="%{ajaxUpdateButtons}" targets="ph-buttons" cssClass="ui-helper-hidden"></sj:a>
<s:form id="formCifInfo" action="20301_inquiryCIF.action">
    <s:hidden name="noCif" />
    <s:token />
</s:form>
<sj:a id="tempCifInfo" formIds="formCifInfo" targets="ph-temp" cssClass="ui-helper-hidden"></sj:a>
<s:form id="formSlInfo" action="20301_inquiry.action">
    <s:hidden name="noCif" />
    <s:hidden name="periodStr" />
    <s:token />
</s:form>
<sj:a id="tempSlInfo" formIds="formSlInfo" targets="ph-temp" cssClass="ui-helper-hidden"></sj:a>
<s:form id="tempFrmDlgAuth" action="dlg">
    <s:hidden name="dialog" value="dlgAuth" />
    <s:hidden name="idMenu" value="20301" />
    <s:token />
</s:form>
<sj:a id="tempDlgAuth" formIds="tempFrmDlgAuth" targets="ph-dlg" cssClass="ui-helper-hidden"></sj:a>
<s:actionmessage id="actionMessage"/>
<s:actionerror name="actionError" />
<s:form id="frmMain" name="frmMain" action="20301" focusElement="frmMain_noCif" validate="true">
    <s:textfield
        name="periodStr"
        requiredLabel="true"
        size="7"
        maxlength="7"
        cssClass="ui-widget ui-widget-content"
        key="label.period"
        disabled="true"
    />
    <s:textfield
        name="noCif"
        requiredLabel="true"
        size="10"
        maxlength="10"
        cssClass="ui-widget ui-widget-content"
        key="label.noCif"
        disabled="true"
    />
    <s:textfield
        name="namCustFull"
        size="50"
        maxlength="50"
        cssClass="ui-widget ui-widget-content"
        label=""
        labelSeparator=""
        disabled="true"
    />
    <s:textfield
        name="cdBranch"
        size="5"
        maxlength="5"
        cssClass="ui-widget ui-widget-content"
        key="label.cdBranch"
        disabled="true"
    />
    <s:textfield
        name="slCreatorUser"
        size="10"
        maxlength="10"
        cssClass="ui-widget ui-widget-content"
        key="label.idUser"
        disabled="true"
    />
    <s:hidden name="idMaintainedBy" value="%{#session.idUser}" />
    <s:hidden name="idMaintainedSpv" />
    <sj:submit
        id="btnSave"
        formIds="frmMain"
        buttonIcon="ui-icon-gear"
        button="true"
        key="button.save"
        disabled="true"
        targets="ph-main"
        onBeforeTopics="beforeSubmit"
    />
    <s:token />
</s:form>
<script type="text/javascript">
    $(function() {
        if ($("#actionError").length==0 && $("#actionMessage").length==0) {
            $("#rbBS").buttonset("destroy");
            $("#tempTitle").click();
            $("#tempButtons").click();
        } else {
            $("#rbBS").data("rdb").callCurrent();
            if ($("#actionError").length==0) {
                $("#rbBS").data("rdb").clear(false);
            }
        }
        $("#frmMain_noCif")
            .parent()
            .append(
                $("#frmMain_namCustFull").detach()
            )
        ;

        $("#frmMain_noCif").die('keydown');
        $("#frmMain_noCif").live('keydown', function(e) {
            if(e.keyCode == 9) {
                var noCif = $.trim($("#frmMain_noCif").val());
                if (noCif=="" || isNaN(noCif) || Number(noCif)<0) {
                    if(isNaN(noCif)) alert('CIF No only accept numbers.');
                    e.preventDefault();
                    return;
                } 

                if($("#rbBSAdd").attr("checked")) {
                if ($("#frmMain_namCustFull").val()=="") {
                        tmpParams = {};
                        tmpParams.onClose = function(namCustFull) {
                        if(namCustFull == "") {
                            alert("CIF: '" + $("#frmMain_noCif").val() + "' not found");
                            $("#frmMain_namCustFull").val("");
                            $("#frmMain_noCif").focus();
                            e.preventDefault();
                        } else {
                            $("#frmMain_namCustFull").val(namCustFull);
                            $("#frmMain_noCif").attr("readonly", "true");
                                $("#frmMain_periodStr").val('<s:date name="%{#session.dtBusiness}" format="MM/yyyy" />');
                                $("#frmMain_periodStr").focus();
                            }
                        }
                        
                        $("#formCifInfo_noCif").val($("#frmMain_noCif").val());
                        $("#tempCifInfo").click();
                    }
                } else {
                    tempParams = {};
                    tempParams.onClose = function(namCustFull, cdBranch, slCreatorUser) {
                        if(namCustFull == "") {
                            alert("CIF: '" + $("#frmMain_noCif").val() + "' not found");
                            $("#frmMain_namCustFull").val("");
                            $("#frmMain_noCif").focus();
                            e.preventDefault();
                        } else {
                            $("#frmMain_namCustFull").val(namCustFull);
                            $("#frmMain_noCif").attr("readonly", "true");

                            if(cdBranch != "" && slCreatorUser != "") {
                                $("#frmMain_cdBranch").val(cdBranch);
                                $("#frmMain_slCreatorUser").val(slCreatorUser);
                                $("#frmMain_periodStr").attr("readonly", "true");
                            } else {
                                alert("SL for CIF: '" + $("#frmMain_noCif").val() + "' and Period: '" +  $("#frmMain_periodStr").val() + "' not found");
                                $("#frmMain_periodStr").focus();
                            }
                            }
                        }

                    $("#formSlInfo_noCif").val($("#frmMain_noCif").val());
                    $("#formSlInfo_periodStr").val($("#frmMain_periodStr").val());
                    $("#tempSlInfo").click();
                }
            }
        });
        
        $("#btnSave").subscribe("beforeSubmit", function(event) {
            $("#frmMain").unbind("submit");
            event.originalEvent.options.submit = false;
            if (validateForm_frmMain()) {
                dlgParams = {};
                dlgParams.idMaintainedBy = "frmMain_idMaintainedBy";
                dlgParams.idMaintainedSpv = "frmMain_idMaintainedSpv";
                dlgParams.event = event;
                dlgParams.onSubmit = function() {
                    dlgParams.event.originalEvent.options.submit = true;
                    $("#btnSave").unsubscribe("beforeSubmit");
                    $("#btnSave").click();
                };
                $("#ph-dlg").dialog("option", "position", "center");
                //$("#ph-dlg").dialog("option", "width", $("body").width()*2/4);
                //$("#ph-dlg").dialog("option", "height", $("body").height()*2/4);
                $("#ph-dlg").dialog("option", "width", "320");
                $("#ph-dlg").dialog("option", "height", "180");
                $("#ph-dlg")
                    .html("Please wait...")
                    .unbind("dialogopen")
                    .bind("dialogopen", function() {
                        $("#tempDlgAuth").click();
                    })
                    .dialog("open");
            }
        });
    });
</script>
<% } %>