<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sj" uri="/struts-jquery-tags" %>

<s:set var="requestMethod" scope="request">${pageContext.request.method}</s:set>

<s:if test="%{#request.requestMethod.equals('POST')}">
    <s:url var="ajaxUpdateTitle" action="23403_title" />
    <s:url var="ajaxUpdateButtons" action="23403_buttons" />
    <sj:a id="tempTitle" href="%{ajaxUpdateTitle}" targets="ph-title" cssClass="ui-helper-hidden" />
    <sj:a id="tempButtons" href="%{ajaxUpdateButtons}" targets="ph-buttons" cssClass="ui-helper-hidden" />
    <s:form id="tempValidation" action="23403_edit.action">
        <s:hidden name="acctSearch" />
        <s:token />
    </s:form>
    <sj:a id="tempDlgValidation" formIds="tempValidation" targets="ph-dlg" cssClass="ui-helper-hidden"></sj:a>
    <s:actionmessage id="actionMessage" />
    <s:actionerror name="actionError" />

    <s:form id="frmMain" 
            name="frmMain" 
            action="23403_add" 
            focusElement="frmMain_acctSearch" 
            method="post" 
            enctype="multipart/form-data">
        <s:textfield
            name="acctSearch"
            size="30"
            maxlength="20"
            cssClass="ui-widget ui-widget-content"
            key="label.sknNSMOAcct"
            disabled="false"
            />
		<td
            id="errAcctMessage"
            disabled="false"
            /></td>
		<sj:a
            id="btnFind"
            button="true"
            disabled="false"
            >
			Validate
        </sj:a>
        <s:file name="theFile" key="label.sknUpload" size="40" id="theFile" disabled="true"/>
        <sj:submit 
            id="btnSave" 
            formIds="frmMain" 
            buttonIcon="ui-icon-gear"
            button="true" 
            key="button.upload" 
            disabled="true" 
            targets="ph-main"
            onBeforeTopics="beforeSubmit" 
            />
        <s:hidden name="notification" id="notification" />  
        <s:hidden name="cdBranch" value="%{#session.cdBranch}"/>
        <s:hidden name="idMaintainedBy" value="%{#session.idUser}" />
        <s:token />
    </s:form>
    <s:hidden name="cdBranch" value="%{#session.cdBranch}"/>
    <script type="text/javascript">
	    function removesRow3(rowid){
        var row =  document.getElementById(rowid);
        //alert(rowid);
        row.parentNode.removeChild(row);
    }
       
		$(function() {
            if ($("#actionError").length == 0 && $("#actionMessage").length == 0) {
                $("#rbBS").buttonset("destroy");
                $("#tempTitle").click();
                $("#tempButtons").click();
            } else {
                $("#rbBS").data("rdb").callCurrent();
                if ($("#actionError").length == 0) {
                    $("#rbBS").data("rdb").clear(false);
                }
            }
            $("#frmMain_acctSearch")
        .parent()
        .append(
        $("#btnFind").detach())
		
		$("#btnFind")
        .parent()
        .append(
        $("#errAcctMessage").detach())
		
            $("#frmMain_acctSearch").die('keydown');
            $("#frmMain_acctSearch").live('keydown' , function (e) {
                if (e.which == 9)
                    $("#btnFind").click();
            });
            
			
            $("#btnFind").click(function() {
                if($("#frmMain_acctSearch").val() != ""){
                    $("#validatePass").val("1");
                
                    $("#tempValidation_acctSearch").val($("#frmMain_acctSearch").val());
                    //alert("nokey");
                    $("#tempDlgValidation").click(); 
                    acctParams = {};
                    acctParams.onClose = function(notification){
                        if(notification == ""){
                            //alert("test");
                            //$("#supervisor").val("");
                            if(document.getElementById("errorpas0")){                    
                                removesRow3("errorpas0");   
                            }
                            $('#errAcctMessage').before('<tr id="errorpas0"><td align="center" colspan="2"><span class="actionMessage" classname="actionMessage">'
                                + "Account is Valid" +'</span></td></tr>');
							$("#frmMain_acctSearch").attr("readonly","readonly");
							$("input#theFile").attr('disabled', false);
                            $("#btnSave").button("enable");
                            //e.preventDefault();
                        } else {
                            $("#notification").val(notification);
							
							if(document.getElementById("errorpas0")){                    
                                removesRow3("errorpas0");	
                            }
                            //getErrField(notification);
							$('#errAcctMessage').before('<tr id="errorpas0"><td align="center" colspan="2"><span class="errorMessage" classname="errorMessage">'
                                + notification +'</span></td></tr>');
                            $("#btnSave").button("disable");
                            //e.preventDefault();
                        }
                    }
                } else {
                    if(document.getElementById("errorpas0")){                    
                        removesRow3("errorpas0");   
                    }
                    $('#changePass tr#p0').before('<tr id="errorpas0"><td align="center" colspan="2"><span class="errorMessage" classname="errorMessage">'
                        +"Account No Cannot be Blank"+'</span></td></tr>');
                    $("#btnSave").button("disable");
                }
            });
            $("#btnSave")
            .unsubscribe("beforeSubmit")
            .subscribe("beforeSubmit", function(event) {
            event.originalEvent.options.submit = false;
            if(checkAplhanumeric($("input#theFile").val())){
                event.originalEvent.options.submit = true;

                $("#frmMain_theFile").attr("disabled", "true");
                $("#btnSave").attr("disabled", "true");
            } else {
                alert("No Special Character Allowed");	
                event.preventDefault();
            }
            });
        });
	
    </script>
</s:if>
