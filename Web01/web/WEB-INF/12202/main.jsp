<%@page import="com.opensymphony.xwork2.ActionSupport"%>
<%@page import="com.opensymphony.xwork2.ActionContext"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<% if (!request.getMethod().equals("GET")) {%>
<s:url var="ajaxUpdateTitle" action="12202_title" />
<s:url var="ajaxUpdateButtons" action="12202_buttons" />
<sj:a id="tempTitle" href="%{ajaxUpdateTitle}" targets="ph-title" cssClass="ui-helper-hidden"></sj:a>
<sj:a id="tempButtons" href="%{ajaxUpdateButtons}" targets="ph-buttons" cssClass="ui-helper-hidden"></sj:a>

    <s:url var="urlTab0" action="12202_gridDownload" escapeAmp="false">
    </s:url> 
	<s:url var="urlTab1" action="12202_gridDownloaded" escapeAmp="false">
    </s:url> 
    <sj:tabbedpanel id="tabbedpanel" disabled="true" >
        <sj:tab id="t0" href="%{urlTab0}" label="Download"/>
		<sj:tab id="t1" href="%{urlTab1}" label="Download"/>
    </sj:tabbedpanel>

<s:actionmessage id="actionMessage" />
<s:actionerror name="actionError"/>
<s:form id="frmMain" name="frmMain" action="12202" method="post" theme="simple">
    <s:hidden name="idMaintainedBy" id = "idMaintainedBy" value="%{#session.idUser}" />
    <s:hidden name="idMaintainedSpv" id = "idMaintainedSpv"/>
    <s:hidden name="user" id="frmMain_user" value="%{#session.idUser}" />
    <s:hidden name="usertemp" id="usertemp" value="%{#session.idUser}"/>
    <s:hidden name="idReport" id="idReport"/>
    <s:hidden name="filePath" id="filePath"/>
    <s:hidden name="dtmRequest" id="dtmRequest"/>
    <s:hidden name="status" id="status"/>
    <s:hidden name="flag" id="frmMain_flag" value="1" />
    <s:token />
</s:form>

<script type="text/javascript">
    $("#btnSave").attr("style","display:none");
    
    $(function() {
        $('#tabbedpanel ul').attr('style', 'display:none');
        if ($("#actionError").length == 0 && $("#actionMessage").length == 0) {
            $("#rbBS").buttonset("destroy");
            $("#tempTitle").click();
                    var data = "";
            $('#tabbedpanel').tabs({
                                      select: function(event, ui) {
                    data = {user : $("#user").val(), flag : $("#flag").val()};
                                        },
                                      ajaxOptions: {
                                                type: 'POST',
                    data: {
                        'user': $("#frmMain_user").val(),
                        'flag': $("#frmMain_flag").val()
                    }
                                       }
                    });
                    $('#tabbedpanel').tabs('load', 0);
                    $("#tabbedpanel").tabs("option", "disabled", []);
            $("#tempButtons").click();
        } else {
            $("#rbBS").data("rdb").callCurrent();
            if ($("#actionError").length == 0) {
                $("#rbBS").data("rdb").clear(false);
            }
        }
        
		$("#btnSave").subscribe("beforeSubmit", function(event) {
            $("#frmMain").unbind("submit");
                        event.originalEvent.options.submit = true;
                        $("#btnSave").unsubscribe("beforeSubmit");
                        $("#btnSave").click();
                        frmDownload.submit();
                        <%((ActionSupport)ActionContext.getContext().getActionInvocation().getAction()).clearErrorsAndMessages();%>
        });
    });
</script>
<%  
    }%>
