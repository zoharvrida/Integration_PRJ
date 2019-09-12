<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sj" uri="/struts-jquery-tags" %>

<s:set var="requestMethod" scope="request">${pageContext.request.method}</s:set>

<s:if test="%{#request.requestMethod.equals('POST')}">
	<s:url var="ajaxUpdateTitle" action="23401_title" />
	<s:url var="ajaxUpdateButtons" action="23401_buttons" />
	<sj:a id="tempTitle" href="%{ajaxUpdateTitle}" targets="ph-title" cssClass="ui-helper-hidden" />
	<sj:a id="tempButtons" href="%{ajaxUpdateButtons}" targets="ph-buttons" cssClass="ui-helper-hidden" />
	
	<s:actionmessage id="actionMessage" />
	<s:actionerror name="actionError" />
	
	<s:form id="frmMain" 
			name="frmMain" 
			action="23401_add" 
			focusElement="frmMain_theFile" 
			method="post" 
			enctype="multipart/form-data">
		
		<s:file name="theFile" key="label.sknUpload" size="40" />
		<sj:submit 
			id="btnSave" 
			formIds="frmMain" 
			buttonIcon="ui-icon-gear"
			button="true" 
			key="button.upload" 
			disabled="false" 
			targets="ph-main"
			onBeforeTopics="beforeSubmit" 
		/>
		<s:token />
	</s:form>
	
	<script type="text/javascript">
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
			
			$("#btnSave")
				.unsubscribe("beforeSubmit")
				.subscribe("beforeSubmit", function(event) {
                event.originalEvent.options.submit = false;
                if(checkAplhanumeric($("#frmMain_theFile").val())){
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
