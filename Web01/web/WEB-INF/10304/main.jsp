<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<% if(!request.getMethod().equals("GET")) {%>
<s:url var="ajaxUpdateTitle" action="10304_title" />
<s:url var="ajaxUpdateButtons" action="10304_buttons" />

<sj:a id="tempTitle" href="%{ajaxUpdateTitle}" targets="ph-title" cssClass="ui-helper-hidden"></sj:a>
<sj:a id="tempButtons" href="%{ajaxUpdateButtons}" targets="ph-buttons" cssClass="ui-helper-hidden"></sj:a>
<s:form id="tempFrmDlgTemplate" action="dlg">
    <s:hidden name="dialog" value="dlgTemplate" id="tempDelete" />
    <s:hidden name="namTemplate" />
    <s:hidden name="idTemplate"/>
    <s:token />
</s:form>
<sj:a id="tempDlgTemplate" formIds="tempFrmDlgTemplate" targets="ph-dlg" cssClass="ui-helper-hidden"></sj:a>

<s:form id="tempFrmDlgReport" action="dlg">
    <s:hidden name="dialog" value="dlgMastReport" id="repDelete"/>
    <s:hidden name="reportName" id="repPot"/>
    <s:hidden name="idTemplate" id="idTempObj" /> 
    <s:token />
</s:form>
<sj:a id="tempDlgReport" formIds="tempFrmDlgReport" targets="ph-dlg" cssClass="ui-helper-hidden"></sj:a>

<s:form id="tempFrmDlgAuth" action="dlg">
    <s:hidden name="dialog" value="dlgAuth" />
    <s:hidden name="idMenu" value="10304" />
    <s:token />
</s:form>
<sj:a id="tempDlgAuth" formIds="tempFrmDlgAuth" targets="ph-dlg" cssClass="ui-helper-hidden"></sj:a>

<s:actionmessage id="actionMessage" />
<s:actionerror name="actionError"/>
<s:form id="frmMain" name="frmMain" action="10304" focusElement="frmMain_namTemplateSearch" method="post" validate="true">
    <s:textfield
        name="namTemplateSearch"
        size="30"
        maxlength="20"
        cssClass="ui-widget ui-widget-content"
        key="label.namTemplateSearch"
        disabled="true"
    />
    <sj:a
        id="btnFindTemplate"
        button="true"
        disabled="true"
    >...</sj:a>
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
        requiredLabel="false"
        size="60"
        maxlength="40"
        cssClass="ui-widget ui-widget-content"
        key="label.namTemplate"
        disabled="true"
    />
    <s:textfield
        name="namReportSearch"
        size="30"
        maxlength="20"
        cssClass="ui-widget ui-widget-content"
        key="label.namReportSearch"
        disabled="true"
    />
    <sj:a
        id="btnFindReport"
        button="true"
        disabled="true"
    >...</sj:a>
    <s:textfield
        name="idReport"
        requiredLabel="true"
        size="15"
        maxlength="10"
        cssClass="ui-widget ui-widget-content"
        key="label.idReport"
        disabled="true"
    />
    <s:textfield
        name="reportName"
        requiredLabel="false"
        size="60"
        maxlength="40"
        cssClass="ui-widget ui-widget-content"
        key="label.reportName"
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
            if ($("#actionError").length==0) {
                $("#rbBS").data("rdb").clear(false);
            } else {
              $("#rbBS").data("rdb").callCurrent();  
            }
        }
        $("#frmMain_namTemplateSearch")
            .parent()
            .append(
                $("#btnFindTemplate").detach()
            )
//            .append(
//                $("#frmMain_idTemplate").detach()
//            )
        ;
        $("#frmMain_namReportSearch")
            .parent()
            .append(
                $("#btnFindReport").detach()
            )
//            .append(
//                $("#frmMain_idMenu").detach()
//            )
       // ;
        $("#frmMain_namTemplateSearch").die('keydown');
        $("#frmMain_namTemplateSearch").live('keydown', function(e) {
            if(e.keyCode == 9) {
                $("#btnFindTemplate").click();
            }
        });

        $("#frmMain_namReportSearch").die('keydown');
        $("#frmMain_namReportSearch").live('keydown', function(e) {
            if(e.keyCode == 9) {
                $("#btnFindReport").click();
            }
        });
        
        $("#btnFindTemplate").click(function() {
            //var s = $("#frmMain").attr('action').split("\;");
            //alert(s[0]);
            //var s = $("#frmMain").attr('action');
            if(rdb.current == "delete")
                {
                        $('#tempDelete').attr('value','dlgTemplateDelete');
                }
                else
                    {
                         $('#tempDelete').attr('value','dlgTemplate');
                    }
            if(!($("#btnFindTemplate").button('option').disabled != undefined && 
                $("#btnFindTemplate").button('option', 'disabled'))) {
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
                        $("#tempDlgTemplate").click();
                    })
                    .dialog("open");
            }    
        });
        
        $("#btnFindReport").click(function() {
         //var s = $("#frmMain").attr('action').split("\;");
         //var s = $("#frmMain").attr('action');
            if(rdb.current == "add")
                {
                     $('#repDelete').attr('value','dlgMastReport');
                     $('#idTempObj').attr('value',$("#frmMain_idTemplate").val());
                     $('#repPot').attr('value',$("#frmMain_namReportSearch").val());
                }
                else
                    {
                        $('#repDelete').attr('value','dlgReportDelete');
                        $('#idTempObj').attr('value',$("#frmMain_idTemplate").val());
                        $('#repPot').attr('value',$("#frmMain_namReportSearch").val());
                    }
            if(!($("#btnFindReport").button('option').disabled != undefined && 
                $("#btnFindReport").button('option','disabled'))) {
                dlgParams = {};
                dlgParams.idReport = "frmMain_idReport";
                dlgParams.reportName = "frmMain_reportName";
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
                        $("#tempFrmDlgReport_reportName").val($("#frmMain_namReportSearch").val());
                        //$("#tempFrmDlgreport_idTemplate").val($("#frmMain_idTemplate").val());
                        $("#tempDlgReport").click();
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
                    $("#btnSave").button("disable");
                };
                $("#ph-dlg").dialog("option", "position", "center");
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