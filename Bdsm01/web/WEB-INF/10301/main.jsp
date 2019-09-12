<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<% if(!request.getMethod().equals("GET")) { %>
<s:url var="ajaxUpdateTitle" action="10301_title" />
<s:url var="ajaxUpdateButtons" action="10301_buttons" />
<sj:a id="tempTitle" href="%{ajaxUpdateTitle}" targets="ph-title" cssClass="ui-helper-hidden"></sj:a>
<sj:a id="tempButtons" href="%{ajaxUpdateButtons}" targets="ph-buttons" cssClass="ui-helper-hidden"></sj:a>
<s:form id="tempFrmDlgTemplate" action="dlg">
    <s:hidden name="dialog" value="dlgTemplate" />
    <s:hidden name="namTemplate" />
    <s:token />
</s:form>
<sj:a id="tempDlg" formIds="tempFrmDlgTemplate" targets="ph-dlg" cssClass="ui-helper-hidden"></sj:a>
<s:form id="tempFrmDlgAuth" action="dlg">
    <s:hidden name="dialog" value="dlgAuth" />
    <s:hidden name="idMenu" value="10301" />
    <s:token />
</s:form>
<sj:a id="tempDlgAuth" formIds="tempFrmDlgAuth" targets="ph-dlg" cssClass="ui-helper-hidden"></sj:a>
<s:actionmessage id="actionMessage" />
<s:actionerror name="actionError"/>
<s:form id="frmMain" name="frmMain" action="10301" focusElement="frmMain_namTemplateSearch" method="post" validate="true">
    <s:textfield
        name="namTemplateSearch"
        size="30"
        maxlength="20"
        cssClass="ui-widget ui-widget-content"
        key="label.namTemplateSearch"
        disabled="true"
    />
    <s:textfield
        name="idTemplate"
        requiredLabel="true"
        size="15"
        maxlength="10"
        cssClass="ui-widget ui-widget-content"
        key="label.idTemplate"
        disabled="true"
    />
    <s:textfield
        name="namTemplate"
        requiredLabel="true"
        size="60"
        maxlength="40"
        cssClass="ui-widget ui-widget-content"
        key="label.namTemplate"
        disabled="true"
    />
    <s:hidden name="idMaintainedBy" value="%{#session.idUser}" />
    <s:hidden name="idMaintainedSpv" />
    <sj:a
        id="btnFind"
        button="true"
        disabled="true"
    >...</sj:a>
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
        $("#frmMain_namTemplateSearch")
            .parent()
            .append(
                $("#btnFind").detach()
            )
//            .append(
//                $("#frmMain_idTemplate").detach()
//            )
        ;
        
        $("#frmMain_namTemplateSearch").die('keydown');
        $("#frmMain_namTemplateSearch").live('keydown', function(e) {
            if(e.keyCode == 9) {
                $("#btnFind").click();
            }
        });
        
        $("#btnFind").click(function() {
            if(!($("#btnFind").button('option').disabled != undefined && 
                $("#btnFind").button('option', 'disabled'))) {
                dlgParams = {};
                dlgParams.idTemplate = "frmMain_idTemplate";
                dlgParams.namTemplate = "frmMain_namTemplate";
                dlgParams.onClose = function() {
                    //alert("test onclose");
                };
                $("#ph-dlg").dialog("option", "position", "center");
                $("#ph-dlg").dialog("option", "width", $("body").width()*3/4);
                //$("#ph-dlg").dialog("option", "height", $("body").height()*3/4);
                $("#ph-dlg").dialog("option", "height", "450");
                $("#ph-dlg")
                    .html("Please wait...")
                    .unbind("dialogopen")
                    .bind("dialogopen", function() {
                        $("#tempFrmDlgTemplate_namTemplate").val($("#frmMain_namTemplateSearch").val());
                        $("#tempDlg").click();
                    })
                    .dialog("open");
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
