<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sj" uri="/struts-jquery-tags" %>


<s:set var="requestMethod" scope="request">${pageContext.request.method}</s:set>

<s:if test="%{#request.requestMethod.equals('POST')}">
	<s:url var="ajaxUpdateTitle" action="23201_title" />
	<s:url var="ajaxUpdateButtons" action="23201_buttons" />
	<sj:a id="tempTitle" href="%{ajaxUpdateTitle}" targets="ph-title" cssClass="ui-helper-hidden" />
	<sj:a id="tempButtons" href="%{ajaxUpdateButtons}" targets="ph-buttons" cssClass="ui-helper-hidden" />
	
	<s:actionmessage id="actionMessage" />
	<s:actionerror name="actionError" />
	
	<s:form id="frmMain" name="frmMain" action="23201" focusElement="frmMain_namTemplateSearch" method="post" validate="true">
		<s:url var="codeNoURL" action="23201_list.action?action=select&page=1&rows=100" namespace="/json" />
		<sj:select
			id="frmMain_mode"
			name="mode"
			href="%{codeNoURL}"
			list="modeList"
			value="1"
			onChangeTopics="codeNoReload"
			cssStyle="display:none"
		/>
		<s:textfield 
			name="txtCodeNo" 
			size="10" 
			maxlength="6" 
			cssClass="ui-widget ui-widget-content" 
			key="label.code.no" 
			disabled="false" 
		/>
		<sj:select
			id="frmMain_codeNo"
			name="codeNo"
			href="%{codeNoURL}"
			list="gridTemplate"
			headerKey="-1"
			headerValue=" "
			reloadTopics="codeNoReload"
			onchange="$('#btnFind').click();"
		/>
		<sj:a 
				id="btnFind" 
				button="true" 
				disabled="false"
				cssStyle="display:none"
		>
			<s:text name="button.search" />
		</sj:a>
		<s:token />
	</s:form>
	<br>
	
	<%-- TabbedPanel for Grid --%>
	<s:url var="urlTab1" action="23201Tab_none">
		<param name="prmBatchNo" value="" />
	</s:url>
	<s:url var="urlTab2" action="23201Tab_none">
		<param name="prmBatchNo" value="" />
	</s:url>
	
	<sj:tabbedpanel id="tabbedPanel" disabled="true" cssStyle="max-width:950px">
		<sj:tab id="tabinquiry" href="%{urlTab1}" label="SKNNG Inward Debit Inquiry" />
		<sj:tab id="tabapproval" href="%{urlTab2}" label="SKNNG Inward Debit Approval" />
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
			
			$("#frmMain_txtCodeNo").parent().parent().append($("#frmMain_codeNo").parent().detach());
			$("#frmMain_txtCodeNo").parent().remove();
			$("#frmMain_mode").val("-1");
			$("#frmMain_codeNo").val("-1");
	
	
			$("#btnFind").click(function() {
				var act = rdb.current;
				
				if ($("#frmMain_codeNo").val() == '-1')
					$('#tabbedPanel').hide();
				else {
					$('#tabbedPanel').tabs({
						ajaxOptions :{
							type: 'POST',
							data: {
								'codeNo': $("#frmMain_codeNo").val()
							}
						}						
					});
					
					if (act == "inquiry") {
						$("#tabbedPanel").tabs({selected : 0});
						$("#tabbedPanel").tabs("url", 0, "23201Tab_execute.action");
						$("#tabbedPanel").tabs("load", 0);
					}
					else if (act == "approve") {
						$("#tabbedPanel").tabs({selected : 1});
						$("#tabbedPanel").tabs("url", 1, "23201Tab_input.action");
						$("#tabbedPanel").tabs("load", 1);
					}
					
					$("#tabbedPanel").show();
				}
			});
		});
	
	</script>
</s:if>

