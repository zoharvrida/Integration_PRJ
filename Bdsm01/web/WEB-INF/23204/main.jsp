<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>


<s:set var="requestMethod" scope="request">${pageContext.request.method}</s:set>

<s:if test="%{#request.requestMethod.equals('POST')}">
	<s:url var="ajaxUpdateTitle" action="23204_title" />
	<s:url var="ajaxUpdateButtons" action="23204_buttons" />
	<sj:a id="tempTitle" href="%{ajaxUpdateTitle}" targets="ph-title" cssClass="ui-helper-hidden" />
	<sj:a id="tempButtons" href="%{ajaxUpdateButtons}" targets="ph-buttons" cssClass="ui-helper-hidden" />

	<s:actionmessage id="actionMessage" />
	<s:actionerror name="actionError"/>
	<s:form id="frmMain" name="frmMain" action="23204" method="post" validate="true">
		<s:url id="getListType" action="json/23204_getListType" escapeAmp="false">
		</s:url>
		<s:textfield
			name="IDBATCH" 
			size="10" 
			maxlength="6" 
			cssClass="ui-widget ui-widget-content" 
			disabled="false"
			cssStyle="display:none"
			onChangeTopics="codeNoReload"
		/>
        <sj:select
            href="%{getListType}"
            id="gRPID"
            name="gRPID"
            list="grpiDList"
            listKey="GRPID"
            listValue="GRPID"
            onChangeTopics="schedImportList"
			onChange="$('#btnFind').click();"
            onSelectTopics="schedImportList"
			reloadTopics="codeNoReload"
            cssClass="ui-widget ui-widget-content"
            key="label.code.no" 
            emptyOption="true"
            headerKey="-1"
        />
        <sj:select
            href="%{getListType}"
            id="schedImport"
            name="schedImport"
            list="schedImportList"
            listKey="IDSCHEDULER"
            listValue="NAMSCHEDULER"
            reloadTopics="schedImportList"
            onChangeTopics="tempID2List"
            cssClass="ui-widget ui-widget-content"
            emptyOption="true"
            headerKey="-1"
            disabled="false"
			cssStyle="display:none"
        />
		<sj:a
			id="btnFind"
			button="true"
			disabled="false"
			cssStyle="display:none"
		>
			<s:text name="button.search" />
		</sj:a>
		<s:hidden name="modes" id="modes"/>
		<s:hidden name="idMaintainedBy" value="%{#session.idUser}" />
		<s:hidden name="idMaintainedSpv" />
		
		<s:token />
	</s:form>
	<br>

	<%-- TabbedPanel for Grid --%>
	<s:url var="urlTab1" action="23204Tab_none">
		<param name="prmBatchNo" value="" />
	</s:url>
	<s:url var="urlTab2" action="23204Tab_none">
		<param name="prmBatchNo" value="" />
	</s:url>

	<sj:tabbedpanel id="tabbedPanel" disabled="true">
		<sj:tab id="tabinquiry" href="%{urlTab1}" label="SKNNG Incoming Credit Inquiry" />
		<sj:tab id="tabapproval" href="%{urlTab2}" label="SKNNG Incoming Credit Approval All Batch" />
	</sj:tabbedpanel>

	<script type="text/javascript">
		$(function() {
			$("#tabbedPanel").hide();
			$('#tabbedPanel ul').attr('style', 'display:none');
			if ($("#actionError").length == 0 && $("#actionMessage").length == 0) {
				$("#rbBS").buttonset("destroy");
				$("#tempTitle").click();
				$("#tempButtons").click();
			}
			else {
				$("#rbBS").data("rdb").callCurrent();
				if ($("#actionError").length == 0) {
					$("#rbBS").data("rdb").clear(false);
				}
			}
			//$("#checkS").val("3");
			//$("#frmMain_IDBATCH").parent().parent().append('&nbsp;').append($("#btnFind").detach());
			
			$("#gRPID").parent().parent().append($("#btnFind").detach());
			$("#gRPID").val("-1");

			$("#btnFind").click(function() {
				var act = rdb.current;
				//alert(rdb.current);
				if ($("#gRPID").val() == '-1')
					$('#tabbedPanel').hide();
				else {
					$('#tabbedPanel').tabs({
						ajaxOptions :{
							type: 'POST',
							data: {
								'IDBATCH': $("#gRPID").val()
							}
						}						
					});
        
					if (act == "inquiry") {
						$("#tabbedPanel").tabs({selected: 0});
						$("#tabbedPanel").tabs("url", 0, "23204Tab_execute.action");
						$("#tabbedPanel").tabs("load", 0);
						//alert(0);
					}
					else if (act == "approve") {
						$("#tabbedPanel").tabs({selected: 1});
						$("#tabbedPanel").tabs("url", 1, "23204Tab_input.action");
						$("#tabbedPanel").tabs("load", 1);
						//alert(1);
					}
					$("#tabbedPanel").show();
				}
				
			});
		});
	
	</script>
</s:if>